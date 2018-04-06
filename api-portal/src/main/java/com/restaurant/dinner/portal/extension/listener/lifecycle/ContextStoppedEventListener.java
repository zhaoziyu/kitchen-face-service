package com.restaurant.dinner.portal.extension.listener.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/11
 */
public class ContextStoppedEventListener implements ApplicationListener<ContextStoppedEvent> {
    private static Logger logger = LoggerFactory.getLogger(ContextStoppedEventListener.class);

    @Override
    public void onApplicationEvent(ContextStoppedEvent contextStoppedEvent) {
        logger.info("Context Stopped……");
    }
}
