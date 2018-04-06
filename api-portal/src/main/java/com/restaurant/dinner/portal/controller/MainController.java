package com.restaurant.dinner.portal.controller;

import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.portal.constant.CommonReturnCode;
import com.restaurant.dinner.portal.util.LocaleUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务接口根路径控制器
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Api(value = "MainController", description = "服务接口根路径控制器")
@RestController
@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @ApiOperation(value="欢迎", notes="通常用于测试接口系统是否可以正常运行，返回信息支持国际化")
    @RequestMapping("/")
    public JsonObjectVo<String> main(HttpServletRequest request) {

        JsonObjectVo<String> result = new JsonObjectVo<>();
        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);
        result.setMsg(LocaleUtil.message("welcome.msg", request));
        result.setData(LocaleUtil.message("welcome.data", request));

        logger.info("{} 调用了根接口", request.getSession().getId());

        return result;
    }
}
