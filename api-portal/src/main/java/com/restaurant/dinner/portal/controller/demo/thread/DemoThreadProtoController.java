package com.restaurant.dinner.portal.controller.demo.thread;

import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.portal.constant.CommonReturnCode;
import com.restaurant.dinner.portal.controller.demo.thread.service.DemoThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * 使用原生线程机制，进行多线程业务处理的示例
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/3/12
 */
@RestController
@RequestMapping(value = "/demo/thread", method = {RequestMethod.GET,RequestMethod.POST})
public class DemoThreadProtoController {
    private int countDown = 5;
    private volatile long taskProcessTime = 2000;
    @Autowired
    private DemoThreadService demoThreadService;

    @RequestMapping("/proto")
    public JsonObjectVo<String> main() {
        JsonObjectVo<String> result = new JsonObjectVo<>();

        long start = System.currentTimeMillis();

        CountDownLatch downLatch = new CountDownLatch(countDown);

        for (int i = 0; i < countDown; i++) {
            new ProcessThread(downLatch).start();
        }

        try {
            // 等待处理完毕
            downLatch.await();

            long end = System.currentTimeMillis();

            result.setSuccess(true);
            result.setCodeCompose(CommonReturnCode.SUCCESS);
            result.setData(String.format("完成%s项任务的处理，每项任务耗时%s毫秒，多线程并发处理，共耗时%s毫秒.", countDown, taskProcessTime, (end - start)));
        } catch (InterruptedException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setCodeCompose(CommonReturnCode.PROGRAM_ERROR);
        }
        return result;
    }

    private class ProcessThread extends Thread {
        private CountDownLatch _countDownLatch;
        public ProcessThread(CountDownLatch countDownLatch) {
            this._countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            demoThreadService.process(taskProcessTime);

            // 完成
            this._countDownLatch.countDown();
        }
    }
}
