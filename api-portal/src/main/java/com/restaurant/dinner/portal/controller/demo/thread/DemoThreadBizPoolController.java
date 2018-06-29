package com.restaurant.dinner.portal.controller.demo.thread;

import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.kitchen.common.api.constant.CommonReturnCode;
import com.restaurant.dinner.portal.controller.demo.thread.service.DemoThreadService;
import com.restaurant.dinner.portal.thread.BizThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 使用自定义的BizThreadPool进行多线程业务处理的示例
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/3/12
 */

@RestController
@RequestMapping(value = "/demo/thread", method = {RequestMethod.GET,RequestMethod.POST})
public class DemoThreadBizPoolController {

    @Autowired
    private DemoThreadService demoThreadService;

    @RequestMapping("/biz-pool")
    public JsonObjectVo<String> main() throws ExecutionException, InterruptedException {
        JsonObjectVo<String> result = new JsonObjectVo<>();

        long start = System.currentTimeMillis();

        Future<String> taskFuture1  = BizThreadPool.submitBusinessTask(() -> {
            demoThreadService.process(2000);
            return "任务1处理结果";
        });
        Future<String> taskFuture2 = BizThreadPool.submitBusinessTask(() -> {
            demoThreadService.process(3000);
            return "任务2处理结果";
        });

        String result1 = taskFuture1.get();
        System.out.println(result1);
        String result2 = taskFuture2.get();
        System.out.println(result2);

        long end = System.currentTimeMillis();

        String info = String.format("任务1用时2秒，任务2用时3秒，并行处理两个任务，共耗时%s毫米", (end-start));

        result.setSuccess(true);
        result.setCompose(CommonReturnCode.SUCCESS);
        result.setData(info);

        return result;
    }
}
