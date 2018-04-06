package com.restaurant.dinner.portal.extension.listener.lifecycle;

import com.restaurant.dinner.portal.util.DestructUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot启动异常时执行事件
 * 在异常发生时，最好是添加虚拟机对应的钩子进行资源的回收与释放，能友善的处理异常信息
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/11
 */
public class ApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {
    private static Logger logger = LoggerFactory.getLogger(ApplicationFailedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationFailedEvent applicationFailedEvent) {
        logger.info("Application Failed……");

        DestructUtil.destruct();
    }
}
