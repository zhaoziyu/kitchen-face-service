package com.restaurant.dinner.portal.extension.listener.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/11
 */
public class ContextStartedEventListener implements ApplicationListener<ContextStartedEvent> {
    private static Logger logger = LoggerFactory.getLogger(ContextStartedEventListener.class);

    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        logger.info("Context Started……");
    }
}
