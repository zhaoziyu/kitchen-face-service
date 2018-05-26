package com.restaurant.dinner.portal.extension.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot对应Enviroment已经准备完毕，但此时上下文context还没有创建。
 * 在该监听中获取到ConfigurableEnvironment后可以对配置信息做操作
 * 例如：修改默认的配置信息，增加额外的配置信息，解密配置属性值等
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/11
 */
public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static Logger logger = LoggerFactory.getLogger(ApplicationEnvironmentPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        logger.info("Application Environment Prepared……");
    }
}
