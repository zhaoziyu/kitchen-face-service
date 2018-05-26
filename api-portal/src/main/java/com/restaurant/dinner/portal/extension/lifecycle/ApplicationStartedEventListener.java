package com.restaurant.dinner.portal.extension.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot启动完成的事件
 * ApplicationStartedEvent在上下文刷新后但在调用任何应用程序和命令行参数之前发送。
 * ApplicationReadyEvent在任何应用程序和命令行参数被调用后发送。它表示应用程序已准备好为请求提供服务。
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/10
 */
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private static Logger logger = LoggerFactory.getLogger(ApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        logger.info("Application Started……");
    }
}
