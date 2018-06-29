package com.restaurant.dinner.portal.controller.demo.sign;

import com.kitchen.common.api.annotation.SignVerify;
import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.kitchen.market.common.security.encryption.aes.KitEncryptionTextByAES;
import com.kitchen.common.api.constant.CommonReturnCode;
import com.restaurant.dinner.portal.constant.ProjectReturnCode;
import com.restaurant.dinner.portal.controller.demo.sign.model.DemoFlatBizInfo;
import com.restaurant.dinner.portal.controller.demo.sign.model.DemoFlatUserLoginInfo;
import com.restaurant.dinner.portal.extension.sign.AuthTokenManager;
import com.restaurant.dinner.portal.extension.sign.SecretKeyManager;
import com.restaurant.dinner.portal.extension.sign.SignConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/24
 */
@RestController
@RequestMapping(value = "/demo/sign/flat")
public class DemoSignFlatParamsController {
    private static final String TRUE_PASSWORD = "zhaozy";

    @SignVerify
    @RequestMapping(value = "/login")
    @ResponseBody
    public JsonObjectVo<Object> testFlatParamsLogin(DemoFlatUserLoginInfo params) throws Exception {
        JsonObjectVo<Object> result = new JsonObjectVo<>();
        String username = params.getUserName();
        String deviceId = params.getDeviceId();
        String encryptPassword = params.getEncryptPassword();

        String appSecretKey = SecretKeyManager.getAppSecretKey(params.getAppId());

        // 使用应用密钥解密password
        String password = KitEncryptionTextByAES.decryptByBase64(encryptPassword, appSecretKey);

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
            encryptToken = KitEncryptionTextByAES.encryptToBase64(token, appSecretKey);
        }

        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);
        result.setData(encryptToken);

        return result;
    }

    @SignVerify
    @RequestMapping(value = "/biz")
    @ResponseBody
    public JsonObjectVo<Object> testFlatParamsBiz(DemoFlatBizInfo params) {
        JsonObjectVo<Object> result = new JsonObjectVo<>();

        System.out.println(params.getField1() + params.getField2());

        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);

        return result;
    }
}
