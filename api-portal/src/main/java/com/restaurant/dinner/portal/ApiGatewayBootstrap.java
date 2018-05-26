package com.restaurant.dinner.portal;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.restaurant.dinner.portal.extension.lifecycle.*;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot启动应用入口
 *
 * 注解配置说明
 * SpringBootApplication：标记SpringBoot应用入口
 * EnableCaching：开启缓存
 * EnableScheduling：开启定时任务
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-10-18
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class ApiGatewayBootstrap {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ApiGatewayBootstrap.class);
        // 设置Spring Boot

        app.setBannerMode(Banner.Mode.LOG);
        /**
         * 添加生命周期监听器
         * 也可在监听类上加@Component注解，SpringBoot扫描后会自动添加至监听
         */
        app.addListeners(
                new ApplicationStartingEventListener(),//1.1
                new ApplicationEnvironmentPreparedEventListener(),//1.2
                new ApplicationPreparedEventListener(),//1.3
                new ContextRefreshedEventListener(),//1.4
                new ApplicationReadyEventListener(),//1.5
                new ContextClosedEventListener(),//2

                new ApplicationFailedEventListener(),
                new ContextStartedEventListener(),
                new ContextStoppedEventListener()
        );

        // 启动
        app.run(args);
    }
}
