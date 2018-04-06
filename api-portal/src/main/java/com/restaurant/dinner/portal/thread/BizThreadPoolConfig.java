package com.restaurant.dinner.portal.thread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 线程策略配置
 *
 * 1、用ThreadPoolExecutor自定义线程池，看线程的用途，如果任务量不大，可以用无界队列，如果任务量非常大，要用有界队列，防止OOM。
 * 2、如果任务量很大，还要求每个任务都处理成功，要对提交的任务进行阻塞提交，重写拒绝机制，改为阻塞提交。保证不抛弃一个任务.
 * 3、最大线程数一般设为2N+1最好，N是CPU核数.s
 * 4、核心线程数，看应用，如果是任务，一天跑一次，设置为0，合适，
 *    因为跑完就停掉了，如果是常用线程池，看任务量，是保留一个核心还是几个核心线程数。
 * 5、如果要获取任务执行结果，用CompletionService，但是注意，获取任务的结果的要重新开一个线程获取，
 *    如果在主线程获取，就要等任务都提交后才获取，就会阻塞大量任务结果，队列过大OOM，所以最好异步开个线程获取结果。ss
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-06-28
 */
@Component
public class BizThreadPoolConfig {

    /**
     * 是否提交到业务线程池中执行
     * false：在I/O线程中执行请求的处理过程（即不使用BizThreadPool）
     */
    public static boolean IS_SUBMIT_BUSINESS_THREAD;
    /**
     * 是否阻塞线程
     * true：阻塞线程池（保证每个任务都处理成功）
     * false：非阻塞线程池（处理不了的任务就抛出异常）
     */
    public static boolean IS_BLOCK_THREAD;
    /**
     * 核心线程池大小
     */
    public static int SERVER_CORE_POOL_SIZE;
    /**
     * 最大线程池大小
     */
    public static int SERVER_MAXIMUM_POOL_SIZE;
    /**
     * 是否允许核心线程超时退出
     */
    public static boolean ALLOW_CORE_THREAD_TIMEOUT;
    /**
     * 任务缓存队列的类型
     */
    public static BlockingQueueType SERVER_QUEUE_TYPE;
    /**
     * 任务缓存队列的大小
     */
    public static int WORK_QUEUE_SIZE;


    @Value("${kitchen.gateway.biz-thread-pool.is-submit-business-thread:true}")
    protected void setIsSubmitBusinessThread(boolean isSubmitBusinessThread) {
        IS_SUBMIT_BUSINESS_THREAD = isSubmitBusinessThread;
    }

    @Value("${kitchen.gateway.biz-thread-pool.is-block-thread:true}")
    protected void setIsBlockThread(boolean isBlockThread) {
        IS_BLOCK_THREAD = isBlockThread;
    }


    @Value("${kitchen.gateway.biz-thread-pool.server-core-pool-size:100}")
    protected void setServerCorePoolSize(int serverCorePoolSize) {
        SERVER_CORE_POOL_SIZE = serverCorePoolSize;
    }

    @Value("${kitchen.gateway.biz-thread-pool.server-maximum-pool-size:2147483647}")//Integer.MAX_VALUE
    protected void setServerMaximumPoolSize(int serverMaximumPoolSize) {
        SERVER_MAXIMUM_POOL_SIZE = serverMaximumPoolSize;
    }

    @Value("${kitchen.gateway.biz-thread-pool.allow-core-thread-timeout:false}")
    protected void setAllowCoreThreadTimeout(boolean allowCoreThreadTimeout) {
        ALLOW_CORE_THREAD_TIMEOUT = allowCoreThreadTimeout;
    }

    @Value("${kitchen.gateway.biz-thread-pool.server-queue-type:Synchronous}")
    protected void setServerQueueType(BlockingQueueType serverQueueType) {
        SERVER_QUEUE_TYPE = serverQueueType;
    }

    @Value("${kitchen.gateway.biz-thread-pool.work-queue-size:2147483647}")//Integer.MAX_VALUE
    protected void setWorkQueueSize(int workQueueSize) {
        WORK_QUEUE_SIZE = workQueueSize;
    }
}
