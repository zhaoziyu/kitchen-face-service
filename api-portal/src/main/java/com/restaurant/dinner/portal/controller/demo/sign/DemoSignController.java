package com.restaurant.dinner.portal.controller.demo.sign;

import com.kitchen.common.api.annotation.SignVerify;
import com.kitchen.common.api.pojo.ro.SignRequestObject;
import com.kitchen.common.api.pojo.ro.TokenRequestObject;
import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.kitchen.market.common.security.encryption.rsa.KitEncryptionTextByRSA;
import com.restaurant.dinner.portal.constant.CommonReturnCode;
import com.restaurant.dinner.portal.constant.ProjectReturnCode;
import com.restaurant.dinner.portal.extension.sign.SignConstant;
import com.restaurant.dinner.portal.controller.demo.sign.model.DemoUserLoginInfo;
import com.restaurant.dinner.portal.extension.sign.AuthTokenManager;
import com.restaurant.dinner.portal.extension.sign.SecretKeyManager;
import org.springframework.web.bind.annotation.*;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/19
 */
@RestController
@RequestMapping(value = "/demo/sign")
public class DemoSignController {

    private static final String TRUE_PASSWORD = "zhaozy";

    /**
     * 登录获取Token
     *
     * @param params
     * @return
     * @throws Exception
     */
    @SignVerify
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectVo<Object> testLogin(@RequestBody SignRequestObject<DemoUserLoginInfo> params) throws Exception {
        JsonObjectVo<Object> result = new JsonObjectVo<>();
        String username = params.getContent().getUserName();
        String deviceId = params.getContent().getDeviceId();
        String encryptPassword = params.getContent().getEncryptPassword();

        // 使用平台私钥解密password
        String password = KitEncryptionTextByRSA.decryptByPrivateKey(encryptPassword, SecretKeyManager.SERVER_PRIVATE_KEY_PKCS8);
        // 处理认证业务逻辑
        if (!password.equals(TRUE_PASSWORD)) {
            // 认证失败
            result.setSuccess(false);
            result.setCodeCompose(ProjectReturnCode.USER_AUTH_ERROR);
            return result;
        }

        String encryptToken = null;
        if (SignConstant.ACCESS_CONTROL_OPEN_TOKEN_VERIFY) {
            /**
             * 当前采用Token复用的方式，允许同一个用户在不同终端中同时登录操作；
             * 若只允许一个用户同时在一个终端中登录，则可将Token的存储Key限制为Profix_userId，并调整此处流程，在每次登录成功后，都生成新的Token
             */
            String tokenKey = AuthTokenManager.generateTokenKey(username, deviceId);
            String token = AuthTokenManager.getToken(tokenKey);
            // token不存在或已过期
            if (token == null || token.isEmpty()) {
                // 生成新的Token
                token = AuthTokenManager.generateNewToken(username);
                // 存储Key
                if (SignConstant.ACCESS_CONTROL_TOKEN_VERIFY_LIMIT > 0) {
                    AuthTokenManager.storageToken(tokenKey, token, SignConstant.ACCESS_CONTROL_TOKEN_VERIFY_LIMIT);
                } else {
                    AuthTokenManager.storageToken(tokenKey, token);
                }
            }

            // 使用应用公钥加密Token
            String appPublicKey = SecretKeyManager.getAppPublicKey(params.getAppId());
            encryptToken = KitEncryptionTextByRSA.encryptByPublicKey(token, appPublicKey);
        }

        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);
        result.setData(encryptToken);

        return result;
    }

    /**
     * 需要Token验证的业务接口
     *
     * @param params
     * @return
     */
    @SignVerify
    @RequestMapping(value = "/biz", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectVo<Object> testBiz(@RequestBody TokenRequestObject<Object> params) {
        JsonObjectVo<Object> result = new JsonObjectVo<>();

        System.out.println(params.getContent());

        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);

        return result;
    }
}
