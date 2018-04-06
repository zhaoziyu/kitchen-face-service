package com.restaurant.dinner.portal.controller.demo.rpc;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.portal.constant.CommonReturnCode;
import com.restaurant.dinner.service.demo.api.recipe.DemoServiceOne;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/2/7
 */
@RestController
@RequestMapping(value = "/demo/rpc", method = {RequestMethod.GET,RequestMethod.POST})
public class DemoRpcController {

    @Reference(version = "1.0.0")
    private DemoServiceOne demoServiceOne;

    @RequestMapping("/{name}")
    public JsonObjectVo<String> main(@PathVariable String name) {
        JsonObjectVo<String> result = new JsonObjectVo<>();
        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);

        result.setData(demoServiceOne.bizOne(name));

        return result;
    }
}
