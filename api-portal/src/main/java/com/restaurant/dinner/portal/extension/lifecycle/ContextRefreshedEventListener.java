package com.restaurant.dinner.portal.extension.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/11
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = LoggerFactory.getLogger(ContextRefreshedEventListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Context Refreshed……");
    }
}
