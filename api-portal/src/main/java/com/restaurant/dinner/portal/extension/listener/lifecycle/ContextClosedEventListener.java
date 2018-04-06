package com.restaurant.dinner.portal.extension.listener.lifecycle;

import com.restaurant.dinner.portal.util.DestructUtil;
import com.restaurant.dinner.portal.util.LocaleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/11
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    private static Logger logger = LoggerFactory.getLogger(ContextClosedEventListener.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        logger.info(LocaleUtil.message("system.info.exit"));

        DestructUtil.destruct();
    }
}
