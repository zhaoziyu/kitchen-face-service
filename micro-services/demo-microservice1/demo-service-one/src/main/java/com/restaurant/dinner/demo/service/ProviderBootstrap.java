package com.restaurant.dinner.demo.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RPC服务提供者的启动类（入口类）
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/2/1
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.restaurant")
public class ProviderBootstrap {
    private static final Logger logger = LoggerFactory.getLogger(ProviderBootstrap.class);

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ProviderBootstrap.class);

        //设置Spring Boot
        //…………
        app.setBannerMode(Banner.Mode.LOG);

        // 启动
        app.run(args);

        while (true) {

        }
    }


}
