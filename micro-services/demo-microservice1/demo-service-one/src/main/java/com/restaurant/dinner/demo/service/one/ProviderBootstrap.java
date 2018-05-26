package com.restaurant.dinner.demo.service.one;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * RPC服务提供者的启动类（入口类）
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/2/1
 */
@SpringBootApplication
@EnableScheduling
@EnableDubbo(scanBasePackages = "com.restaurant")
public class ProviderBootstrap {
    private static final Logger logger = LoggerFactory.getLogger(ProviderBootstrap.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProviderBootstrap.class);

        //设置Spring Boot
        app.setBannerMode(Banner.Mode.LOG);
        app.setWebApplicationType(WebApplicationType.NONE);

        // 启动
        app.run(args);
    }

    /**
     * 维持程序运行
     */
    @Scheduled(initialDelay = 2000, fixedRate = 300000)
    private void heartbeat() {
        logger.info("维持心跳");
    }

}
