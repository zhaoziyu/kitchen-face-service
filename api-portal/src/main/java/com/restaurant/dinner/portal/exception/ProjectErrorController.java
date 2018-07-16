package com.restaurant.dinner.portal.exception;

import com.kitchen.common.api.constant.CommonReturnCode;
import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.portal.extension.sign.exception.SignException;
import com.restaurant.dinner.portal.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一处理程序或框架内所有从Controller中抛出的异常（包括自定义异常）
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/4
 */
@ControllerAdvice
public class ProjectErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectErrorController.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonObjectVo<Object> handlerError(HttpServletRequest request, Exception e) throws Exception {
        JsonObjectVo<Object> result = new JsonObjectVo<>();
        result.setSuccess(false);

        if (e instanceof SignException) {
            // 处理签名异常
            SignException signException = (SignException) e;
            result.setCompose(signException.getReturnCode());

            // 记录签名异常日志
            LoggerUtil.logWarnReturnCode(logger, signException.getReturnCode());
        } else {
            String errorMsg = e.getMessage();
            result.setCompose(CommonReturnCode.PROGRAM_ERROR);
            result.setBizMsg(errorMsg);

            // 记录异常日志
            logger.error(CommonReturnCode.PROGRAM_ERROR.getMsg(), e);
        }

        return result;
    }
}
