package com.restaurant.dinner.portal.exception.sign;

import com.restaurant.dinner.portal.constant.CommonReturnCode;

/**
 * 签名异常
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-25
 */
public class SignException extends RuntimeException {

    private CommonReturnCode returnCode;
    public SignException(CommonReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public CommonReturnCode getReturnCode() {
        return this.returnCode;
    }
}
