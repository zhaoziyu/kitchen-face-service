package com.restaurant.dinner.portal.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/10
 */
public class BizThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(BizThreadPool.class);
    private static ThreadPoolExecutor threadPoolExecutor = null;

    static {
        synchronized (BizThreadPool.class) {
            if (BizThreadPoolConfig.IS_SUBMIT_BUSINESS_THREAD) {
                BlockingQueue<Runnable> blockingQueue = createBlockingQueue();

                if (BizThreadPoolConfig.IS_BLOCK_THREAD) {
                    // 阻塞线程
                    createBlockThread(blockingQueue);
                } else {
                    // 非阻塞线程
                    createUnBlockThread(blockingQueue);
                }

                logger.info("业务线程池初始化完毕");
            }
        }
    }

    /**
     * 创建阻塞队列
     * @return
     */
    private static BlockingQueue<Runnable> createBlockingQueue() {
        BlockingQueue<Runnable> blockingQueue;
        switch (BizThreadPoolConfig.SERVER_QUEUE_TYPE) {
            case Array :
                // 基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序。
                blockingQueue = new ArrayBlockingQueue<>(BizThreadPoolConfig.WORK_QUEUE_SIZE);
                break;
            case Linked :
                // 基于链表结构的阻塞队列，此队列按FIFO （先进先出） 排序元素，吞吐量通常要高于ArrayBlockingQueue。
                blockingQueue = new LinkedBlockingQueue<>(BizThreadPoolConfig.WORK_QUEUE_SIZE);
                break;
            case Synchronous:
                // 不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue。
                blockingQueue = new SynchronousQueue<>();
                break;
            case Priority:
                // 具有优先级得无限阻塞队列。
                blockingQueue = new PriorityBlockingQueue<>();
                break;
            default:
                // 默认阻塞队列
                blockingQueue = new SynchronousQueue<>();
                break;
        }
        return blockingQueue;
    }

    /**
     * 创建阻塞线程池
     * @param blockingQueue
     */
    private static void createBlockThread(BlockingQueue<Runnable> blockingQueue) {
        threadPoolExecutor = new ThreadPoolExecutor(
                BizThreadPoolConfig.SERVER_CORE_POOL_SIZE,
                BizThreadPoolConfig.SERVER_MAXIMUM_POOL_SIZE,
                60L,
                TimeUnit.SECONDS,
                blockingQueue,
                Executors.defaultThreadFactory(),
                new BizThreadRejectedExecutionHandler());

        if (BizThreadPoolConfig.ALLOW_CORE_THREAD_TIMEOUT) {
            // 允许核心线程超时退出线程池
            threadPoolExecutor.allowCoreThreadTimeOut(true);
        }
    }

    /**
     * 创建非阻塞线程，处理不了抛出异常
     * @param blockingQueue
     */
    private static void createUnBlockThread(BlockingQueue<Runnable> blockingQueue) {
        threadPoolExecutor = new ThreadPoolExecutor(
                BizThreadPoolConfig.SERVER_CORE_POOL_SIZE,
                BizThreadPoolConfig.SERVER_MAXIMUM_POOL_SIZE,
                60L,
                TimeUnit.SECONDS,
                blockingQueue);
        // 当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。
        // 这个策略默认情况下是AbortPolicy，表示无法处理新任务时抛出异常。

        if (BizThreadPoolConfig.ALLOW_CORE_THREAD_TIMEOUT) {
            // 允许核心线程超时退出线程池
            threadPoolExecutor.allowCoreThreadTimeOut(true);
        }
    }

    /**
     * 调起线程（无法回值）
     *
     * @param task
     * @throws Exception
     */
    public static void executeBusinessTask(Runnable task) {
        if(threadPoolExecutor == null) {
            logger.error("未初始化线程池");
            return;
        }
        threadPoolExecutor.execute(task);
    }

    /**
     * 调起线程（带返回值）
     * @param task
     * @return
     * @throws Exception
     */
    public static <T> Future<T> submitBusinessTask(Callable<T> task) {
        if(threadPoolExecutor == null) {
            logger.error("未初始化线程池");
            return null;
        }
        return threadPoolExecutor.submit(task);
    }

    public static void shutdown() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
            logger.info("业务线程池已关闭");
        }
    }
}
