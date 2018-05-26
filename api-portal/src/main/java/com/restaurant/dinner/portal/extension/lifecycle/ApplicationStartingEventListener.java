package com.restaurant.dinner.portal.extension.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot启动开始时执行的事件
 * 在该事件中可以获取到SpringApplication对象，可做一些执行前的设置
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/10
 */
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {

    private static Logger logger = LoggerFactory.getLogger(ApplicationStartingEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        logger.info("Application Starting……");
    }
}
