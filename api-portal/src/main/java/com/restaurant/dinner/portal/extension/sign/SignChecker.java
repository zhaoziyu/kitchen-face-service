package com.restaurant.dinner.portal.extension.sign;

import com.alibaba.fastjson.JSON;
import com.kitchen.common.api.constant.CommonReturnCode;
import com.kitchen.common.api.pojo.ro.BaseRequestObject;
import com.kitchen.common.api.pojo.ro.BaseTokenRequestObject;
import com.kitchen.market.common.security.encryption.single.KitEncryptionMD5;
import com.restaurant.dinner.portal.extension.sign.exception.SignException;
import com.restaurant.dinner.portal.extension.sign.rsa.KitRSACheck;
import com.restaurant.dinner.portal.extension.sign.util.SignToBeStringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 接口请求的签名验证
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/16
 */
@Aspect
@Component
public class SignChecker {
    private static final Logger logger = LoggerFactory.getLogger(SignChecker.class);

    @Autowired
    private AuthTokenManager authTokenManager;
    @Autowired
    private SecretKeyManager secretKeyManager;

    /**
     * 指定验签接口范围
     *
     * 方式一：指定特定的需要验签的接口
     * @Pointcut("@annotation(com.kitchen.common.api.annotation.SignVerify)")
     *
     * 方式二：指定所有接口进行验签，但忽略个别不需要验签的接口
     * private static final String pointcut = "@annotation(org.springframework.web.bind.annotation.RequestMapping) && !@annotation(com.kitchen.common.api.annotation.IgnoreSignVerify)";
     * @Pointcut(pointcut)
     * 或
     * private static final String pointcut = "execution(public * com.restaurant.dinner.portal.controller..*.*(..)) && !@annotation(com.kitchen.common.api.annotation.IgnoreSignVerify)";
     * @Pointcut(pointcut)
     */
    @Pointcut("@annotation(com.kitchen.common.api.annotation.SignVerify)")
    private void controller(){}

    @Before("controller()")
    public void signInterceptor(JoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 是否开启接口请求验签功能
        if (!SignConstant.ACCESS_CONTROL_OPEN_SIGN_VERIFY) {
            return;
        }

        // 获取Controller方法参数
        Object[] args = joinPoint.getArgs();

        BaseTokenRequestObject baseTokenRequestObject = null;
        BaseRequestObject baseRequestObject = null;

        // 确认该请求的参数是否符约定（是基于BaseRequestObject）
        for(Object obj : args) {
            if (obj instanceof BaseRequestObject) {
                baseRequestObject = (BaseRequestObject) obj;

                if (obj instanceof BaseTokenRequestObject) {
                    baseTokenRequestObject = (BaseTokenRequestObject) obj;
                }
            }
        }

        if (baseRequestObject == null) {
            // 签名验证失败（缺少签名参数）
            throw new SignException(CommonReturnCode.GATEWAY_SIGN_PARAMS_ABSENT);
        }

        String appId = baseRequestObject.getAppId();
        String signType = baseRequestObject.getSignType();
        String sign = baseRequestObject.getSign();
        String timestamp = baseRequestObject.getTimestamp();
        // 验证请求参数是否正确（包含验签所需参数）
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(signType)
                || StringUtils.isEmpty(sign) || StringUtils.isEmpty(timestamp)) {
            // 签名验证失败（缺少签名参数）
            throw new SignException(CommonReturnCode.GATEWAY_SIGN_PARAMS_ABSENT);
        }

        // 计算待签名字符串
        String json = JSON.toJSONString(baseRequestObject);
        logger.debug("签名验证 请求参数：{}", json);
        String toBeSignedString = SignToBeStringUtil.getToBeSignedString(json);
        logger.debug("签名验证 待签名字符串：{}", toBeSignedString);

        String authTokenKey = null;
        String authToken = null;
        if (baseTokenRequestObject != null) {
            String userId = baseTokenRequestObject.getUserId();
            String deviceId = baseTokenRequestObject.getDeviceId();
            if (StringUtils.isEmpty(userId)) {
                // 签名验证失败（缺少签名参数）
                throw new SignException(CommonReturnCode.GATEWAY_SIGN_PARAMS_ABSENT);
            }
            authTokenKey = AuthTokenManager.generateTokenKey(userId, deviceId);
            authToken = authTokenManager.getToken(authTokenKey);
            // 判断是否存在Token
            if (StringUtils.isEmpty(authToken)) {
                // 签名验证失败（token不存在或已过期）
                throw new SignException(CommonReturnCode.GATEWAY_TOKEN_ABSENT);
            }
            // 计算TokenRequest待签名字符串
            toBeSignedString += authToken;

            logger.debug("签名验证 待签名字符串（含Token）：{}", toBeSignedString);
        }

        // 根据签名类型选择处理方式
        if (signType.equals(SignConstant.SIGN_TYPE_RSA) || signType.equals(SignConstant.SIGN_TYPE_RSA2)) {
            // 非对称签名方式 使用应用公钥，验证签名
            this.verifyRSASign(appId, signType, sign, toBeSignedString);
        } else if (signType.equals(SignConstant.SIGN_TYPE_MD5)) {
            // MD5加密签名方式
            this.verifyMD5Sign(sign, toBeSignedString);
        } else {
            // 签名验证失败（未找到符合条件的签名类型）
            throw new SignException(CommonReturnCode.GATEWAY_SIGN_TYPE_FAIL);
        }

        // 是否开启时间戳验证
        if (SignConstant.ACCESS_CONTROL_OPEN_TIMESTAMP_VERIFY) {
            String dateTime = baseRequestObject.getTimestamp();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
            Long timeLong;
            try {
                timeLong = simpleDateFormat.parse(dateTime).getTime();
            } catch (ParseException e) {
                throw new SignException(CommonReturnCode.GATEWAY_SIGN_FORMAT_FAIL);
            }
            this.verifyTimestamp(timeLong);
        }

        // 重置或延长Token的过期时间
        if (SignConstant.ACCESS_CONTROL_TOKEN_HEART_BEAT && authTokenKey != null && authToken != null) {
            if (SignConstant.ACCESS_CONTROL_TOKEN_VERIFY_LIMIT > 0) {
                authTokenManager.resetTokenLimit(authTokenKey, authToken, SignConstant.ACCESS_CONTROL_TOKEN_VERIFY_LIMIT * 1000);
            } else {
                authTokenManager.resetTokenLimit(authTokenKey, authToken);
            }
        }

        long endTime = System.currentTimeMillis();
        logger.debug("请求参数验签耗时：" + (endTime - startTime) + "毫秒");
    }

    /**
     * 非对称方式验签
     * 使用应用公钥验证接口参数签名
     *
     * @param appId
     * @param signType
     * @param sign
     * @param toBeSignedString 待签名字符串
     * @throws Exception
     */
    private void verifyRSASign(String appId, String signType, String sign, String toBeSignedString) throws Exception {
        // 从缓存中获取发起请求方的应用公钥
        String appPublicKey = secretKeyManager.getAppPublicKey(appId);
        if (StringUtils.isEmpty(appPublicKey)) {
            // 签名验证失败（未找到匹配公钥）
            throw new SignException(CommonReturnCode.GATEWAY_SIGN_PUBLIC_KEY_ABSENT);
        }

        // 验证签名
        boolean pass = KitRSACheck.rsaCheck(toBeSignedString, sign, appPublicKey, SignConstant.SIGN_CHARSET_UTF8, signType);

        // 验证签名后的请求参数是否与传入的token相同
        if (!pass) {
            // 签名验证失败（签名不同）
            throw new SignException(CommonReturnCode.GATEWAY_CHECK_SIGN_FAIL);
        }
    }

    /**
     * 对称加密方式验签
     * 使用应用密钥验证接口参数签名
     * @param requestSign
     * @param toBeSignedString
     */
    private void verifyMD5Sign(String requestSign, String toBeSignedString) {
        // 计算签名
        String serverSign = KitEncryptionMD5.encrypt(toBeSignedString);
        logger.debug("签名验证 服务端签名：{}", serverSign);

        requestSign = requestSign.toUpperCase();
        if (!serverSign.equals(requestSign)) {
            // 签名验证失败（签名不同）
            throw new SignException(CommonReturnCode.GATEWAY_CHECK_SIGN_FAIL);
        }

    }

    /**
     * 验证时间戳
     *
     * @param timestamp
     * @throws ParseException
     */
    public static void verifyTimestamp(Long timestamp) throws SignException {
        if (StringUtils.isEmpty(timestamp)) {
            // 签名验证失败（缺少签名参数）
            throw new SignException(CommonReturnCode.GATEWAY_SIGN_PARAMS_ABSENT);
        }
        long serverTime = System.currentTimeMillis();

        // 临界时间
        long limitTime = timestamp + (SignConstant.ACCESS_CONTROL_TIMESTAMP_VERIFY_LIMIT * 1000);

        if (limitTime < serverTime) {
            // 签名验证失败（超时）
            throw new SignException(CommonReturnCode.GATEWAY_CHECK_TIMESTAMP_FAIL);
        }
    }
}
