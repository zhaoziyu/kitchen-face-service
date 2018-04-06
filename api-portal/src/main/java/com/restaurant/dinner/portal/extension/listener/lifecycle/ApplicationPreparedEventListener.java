package com.restaurant.dinner.portal.extension.listener.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot上下文context创建完成，但此时spring中的bean是没有完全加载完成的。
 * 在获取完上下文后，可以将上下文传递出去做一些额外的操作。
 * 在该监听器中是无法获取自定义bean并进行操作的。
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/11
 */
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {
    private static Logger logger = LoggerFactory.getLogger(ApplicationPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        logger.info("Application Prepared……");
    }
}
