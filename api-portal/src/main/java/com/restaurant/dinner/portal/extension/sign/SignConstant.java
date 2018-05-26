package com.restaurant.dinner.portal.extension.sign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 访问控制，签名相关属性
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/16
 */
@Component
public class SignConstant {
    // 签名涉及的JSON字段名称
    public static final String FIELD_SIGN_ID = "sign_id";
    public static final String FIELD_SIGN_TYPE = "sign_type";
    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_TIMESTAMP = "timestamp";

    // 加密方式
    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_TYPE_RSA2 = "RSA2";
    public static final String SIGN_TYPE_MD5 = "MD5";

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
    // UTF-8字符集
    public static final String SIGN_CHARSET_UTF8 = "UTF-8";
    // GBK字符集
    public static final String SIGN_CHARSET_GBK = "GBK";

    //--------------------------------------------------------------------------
    /**
     * 是否开启接口的签名验证
     */
    public static Boolean ACCESS_CONTROL_OPEN_SIGN;
    /**
     * 是否开启接口的返回参数签名
     */
    public static Boolean ACCESS_CONTROL_OPEN_RETURN_SIGN;
    /**
     * 是否开启接口的时间戳验证
     */
    public static Boolean ACCESS_CONTROL_OPEN_TIMESTAMP_VERIFY;
    /**
     * 时间戳有效时间范围，即请求时间戳参数超过多少秒后无效（单位：秒）
     */
    public static Integer ACCESS_CONTROL_TIMESTAMP_VERIFY_LIMIT;
    /**
     * 是否开启接口的用户token验证
     */
    public static Boolean ACCESS_CONTROL_OPEN_TOKEN_VERIFY;
    /**
     * 用户token过期时间，0为永不过期（单位：秒）
     * Token的有效期和你的项目要求有关。普通APP可以考虑不过期，常规的Web应用可能考虑1个月，或者采用心跳的方式，每次调用重新延长一次Token的过期时间。
     */
    public static Integer ACCESS_CONTROL_TOKEN_VERIFY_LIMIT;

    /**
     * 是否开启Token心跳，若开启，则每次验签通过后，都会将Token的时间重置为ACCESS_CONTROL_TOKEN_VERIFY_LIMIT设置的值，维持Token的有效性。
     */
    public static Boolean ACCESS_CONTROL_TOKEN_HEART_BEAT;


    @Value("${kitchen.gateway.access-control.sign.open:false}")
    public void setAccessControlOpenSign(Boolean openSign) {
        ACCESS_CONTROL_OPEN_SIGN = openSign;
    }
    @Value("${kitchen.gateway.access-control.sign.open-response-sign:false}")
    public void setAccessControlOpenReturnSign(Boolean openReturnSign) {
        ACCESS_CONTROL_OPEN_RETURN_SIGN = openReturnSign;
    }
    @Value("${kitchen.gateway.access-control.sign.open-timestamp-verify:false}")
    public void setAccessControlOpenTimestampVerify(Boolean openTimestampVerify) {
        ACCESS_CONTROL_OPEN_TIMESTAMP_VERIFY = openTimestampVerify;
    }
    @Value("${kitchen.gateway.access-control.sign.timestamp-verify-limit:120}")
    public void setAccessControlTimestampVerifyLimit(Integer timestampVerifyLimit) {
        ACCESS_CONTROL_TIMESTAMP_VERIFY_LIMIT = timestampVerifyLimit;
    }
    @Value("${kitchen.gateway.access-control.sign.open-token-verify:false}")
    public void setAccessControlOpenTokenVerify(Boolean openTokenVerify) {
        ACCESS_CONTROL_OPEN_TOKEN_VERIFY = openTokenVerify;
    }
    @Value("${kitchen.gateway.access-control.sign.token-verify-limit:0}")
    public void setAccessControlTokenVerifyLimit(Integer tokenVerifyLimit) {
        ACCESS_CONTROL_TOKEN_VERIFY_LIMIT = tokenVerifyLimit;
    }
    @Value("${kitchen.gateway.access-control.sign.token-heart-beat:true}")
    public void setAccessControlTokenHeartBeat(Boolean tokenHeartBeat) {
        ACCESS_CONTROL_TOKEN_HEART_BEAT = tokenHeartBeat;
    }
}
