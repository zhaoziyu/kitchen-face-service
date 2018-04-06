package com.restaurant.dinner.portal.util;

import com.restaurant.dinner.portal.thread.BizThreadPool;
import com.restaurant.dinner.portal.thread.BizThreadPoolConfig;

/**
 * 析构工具类
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/11
 */
public class DestructUtil {
    /**
     * 系统关闭时需要做一系列处理
     */
    public static void destruct() {

        // 避免系统未加载BizThreadPool类，却在关闭时出发类加载初始化了线程池的问题
        BizThreadPoolConfig.IS_SUBMIT_BUSINESS_THREAD = false;
        BizThreadPool.shutdown();
    }
}
