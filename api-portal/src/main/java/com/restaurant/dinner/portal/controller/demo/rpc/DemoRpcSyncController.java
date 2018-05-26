package com.restaurant.dinner.portal.controller.demo.rpc;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.portal.constant.CommonReturnCode;
import com.restaurant.dinner.portal.thread.BizThreadPool;
import com.restaurant.dinner.service.one.demo.api.recipe.DemoServiceOne;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/demo/rpc/sync", method = {RequestMethod.GET,RequestMethod.POST})
public class DemoRpcSyncController {


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

    /**
     * 同步Dubbo服务的异步调用示例
     * @param name
     * @return
     */
    @RequestMapping("/async/{name}")
    public JsonObjectVo<String> async(@PathVariable String name) {
        JsonObjectVo<String> result = new JsonObjectVo<>();
        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);

        // 使用自有线程池调用Dubbo同步服务，实现异步调用
        Future<String> future = BizThreadPool.submitBusinessTask(() -> demoServiceOne.bizOne(name));

        try {
            result.setData(future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 注意此处
            Thread.currentThread().interrupt();
        }

        return result;
    }
}
