package com.restaurant.dinner.portal.constant;

import com.kitchen.common.api.constant.code.IReturnCode;
import org.springframework.http.HttpStatus;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/19
 */
public enum ProjectReturnCode implements IReturnCode {
    USER_AUTH_ERROR(ProjectReturnCode.RETURN_CODE_51000, ProjectReturnCode.RETURN_CODE_51000_DESC, "AUTH_ERROR", "认证失败,用户名或密码错误");

    /**
     * 工程自定义返回码
     */
    public final static String RETURN_CODE_51000 = "51000";
    public final static String RETURN_CODE_51000_DESC = "用户异常";

    private String _code;
    private String _msg;
    private String _bizCode;
    private String _bizMsg;

    ProjectReturnCode(String code, String msg, String bizCode, String bizMsg) {
        this._code = code;
        this._msg = msg;
        this._bizCode = bizCode;
        this._bizMsg = bizMsg;
    }

    ProjectReturnCode(String code, String msg, HttpStatus httpStatus) {
        this._code = code;
        this._msg = msg;
        this._bizCode = String.valueOf(httpStatus.value());
        this._bizMsg = httpStatus.getReasonPhrase();
    }

    @Override
    public String getCode() {
        return this._code;
    }

    @Override
    public String getMsg() {
        return this._msg;
    }

    @Override
    public String getBizCode() {
        return this._bizCode;
    }

    @Override
    public String getBizMsg() {
        return this._bizMsg;
    }

    @Override
    public String toString() {
        return this._code + "-" + this._bizCode;
    }
}
