package com.restaurant.dinner.portal.controller.demo.rpc;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.kitchen.common.api.constant.CommonReturnCode;
import com.restaurant.dinner.portal.thread.BizThreadPool;
import com.restaurant.dinner.service.one.demo.api.recipe.DemoServiceOnePlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/2/7
 */
@RestController
@RequestMapping(value = "/demo/rpc/async", method = {RequestMethod.GET,RequestMethod.POST})
public class DemoRpcAsyncController {

    private static final Logger logger = LoggerFactory.getLogger(DemoRpcAsyncController.class);

    @Reference(version = "1.0.0", async = true)
    private DemoServiceOnePlus demoServiceOnePlus;

    /**
     * dubbo服务异步调用（基于 NIO 的非阻塞实现并行调用，客户端不需要启动多线程即可完成并行调用多个远程服务，相对多线程开销较小。）
     *
     * @param name
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @RequestMapping("/{name}")
    public JsonObjectVo<String> async(@PathVariable String name) throws InterruptedException, ExecutionException {
        JsonObjectVo<String> result = new JsonObjectVo<>();
        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);

        long start = System.currentTimeMillis();
        String nullValue = demoServiceOnePlus.bizOnePlus(name);
        Future<String> bizOneResult = RpcContext.getContext().getFuture();
        System.out.println("异步调用返回NULL：" + nullValue);

        Thread.sleep(3000);
        System.out.println("本地业务处理完毕");

        result.setData(bizOneResult.get());

        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));

        return result;
    }


    @Autowired
    private DemoLocalService demoLocalService;
    @RequestMapping("/void/{name}")
    public JsonObjectVo<String> asyncVoid(@PathVariable String name) {
        JsonObjectVo<String> result = new JsonObjectVo<>();
        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);

        logger.info("调用无返回参数的异步方法（应该处理3秒）：{}", System.currentTimeMillis());
        demoServiceOnePlus.bizOneAsync(name);

        logger.info("调用本地Spring注入的方法，并在DemoLocalService中注入Dubbo服务：{}", System.currentTimeMillis());
        demoLocalService.test();

        return result;
    }

    /**
     * 嵌套线程
     * 使用自己的线程池+dubbo异步调用
     * 注意：此处仅作为演示线程池内调用本类注入方法的可用性，在实际使用时，可直接通过dubbo的异步方法实现即可，相较于本地多线程，开销较小。
     * dubbo异步调用基于 NIO 的非阻塞实现并行调用，客户端不需要启动多线程即可完成并行调用多个远程服务，相对多线程开销较小。
     * @param name
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("/threads/{name}")
    public JsonObjectVo<String> threads(@PathVariable String name) throws ExecutionException, InterruptedException {
        JsonObjectVo<String> result = new JsonObjectVo<>();
        result.setSuccess(true);
        result.setCodeCompose(CommonReturnCode.SUCCESS);


        Future<Future<String>> future = BizThreadPool.submitBusinessTask(() -> {
            demoServiceOnePlus.bizOnePlus(name);
            return RpcContext.getContext().getFuture();
        });

        String data = future.get().get();
        result.setData(data);

        return result;
    }
}
