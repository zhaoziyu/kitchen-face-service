package com.restaurant.dinner.portal.extension.listener.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot启动完成，所有内容准备就绪
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/11
 */
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    private static Logger logger = LoggerFactory.getLogger(ApplicationReadyEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("Application Ready……");

    }
}
