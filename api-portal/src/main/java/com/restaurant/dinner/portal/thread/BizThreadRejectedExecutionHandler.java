package com.restaurant.dinner.portal.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 业务线程被拒绝时的处理
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-06-28
 */
class BizThreadRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            executor.getQueue().put(r);//重新加入到执行队列中
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
