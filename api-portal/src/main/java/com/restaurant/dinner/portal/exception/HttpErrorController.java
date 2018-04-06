package com.restaurant.dinner.portal.exception;

import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.portal.constant.CommonReturnCode;
import com.restaurant.dinner.portal.constant.ProjectConstant;
import com.restaurant.dinner.portal.util.EnvironmentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 包装HTTP异常，返回统一的JSON结构的信息
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/8
 */
@RestController
public class HttpErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(HttpErrorController.class);

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = ProjectConstant.ERROR_PATH)
    public JsonObjectVo<Object> handleError(HttpServletRequest request) {
        /**
         * 是否包含或打印堆栈信息，取决于是否开启Debug（配置文件中的debug属性）
         */
        boolean includeStackTrace = EnvironmentUtil.isDebug();

        Map<String,Object> errorAttributes = getErrorAttributes(request, includeStackTrace);

        Integer status = (Integer)errorAttributes.get("status");
        String error = (String)errorAttributes.get("error");
        String path = (String) errorAttributes.get("path");
        if(includeStackTrace) {
            String trace = (String) errorAttributes.get("trace");
            if (trace != null && !trace.isEmpty()) {
                error = error + " StackTrace:" + trace;
            }
        }

        JsonObjectVo<Object> result = new JsonObjectVo<>();
        result.setSuccess(false);
        result.setCode(CommonReturnCode.RETURN_CODE_10000);
        result.setMsg(CommonReturnCode.RETURN_CODE_10000_DESC);
        result.setBizCode(status.toString());
        result.setBizMsg(error);

        logger.warn(CommonReturnCode.RETURN_CODE_10000_DESC + " 状态码:" + status.toString() + " 路径:" + path + " 异常描述:" + error);

        return result;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest webRequest = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }


    @Override
    public String getErrorPath() {
        return ProjectConstant.ERROR_PATH;
    }
}
