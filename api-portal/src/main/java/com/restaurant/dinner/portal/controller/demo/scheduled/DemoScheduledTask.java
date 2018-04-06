package com.restaurant.dinner.portal.controller.demo.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/30
 */
@Component
public class DemoScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(DemoScheduledTask.class);

    //@Scheduled(fixedRate = 5000)：上一次开始执行时间点之后5秒再执行
    //@Scheduled(fixedDelay = 5000)：上一次执行完毕时间点之后5秒再执行
    //@Scheduled(initialDelay=1000, fixedRate=5000)：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
    //@Scheduled(cron="/5")：通过cron表达式定义规则
    @Scheduled(initialDelay = 2000, fixedRate = 20000)
    private void reportCurrentTime() {
        log.info("定时任务示例：心跳时间 {}", new Date());
    }
}
