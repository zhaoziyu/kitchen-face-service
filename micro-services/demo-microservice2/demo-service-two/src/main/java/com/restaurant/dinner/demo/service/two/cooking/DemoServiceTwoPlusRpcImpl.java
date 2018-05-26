package com.restaurant.dinner.demo.service.two.cooking;

import com.alibaba.dubbo.config.annotation.Service;
import com.restaurant.dinner.service.two.demo.api.recipe.DemoServiceTwoPlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DemoServiceTwoPlusRpcImpl implements DemoServiceTwoPlus {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceTwoPlus.class);

    @Override
    public String bizTwoPlus(String name) {
        logger.info("Biz two plus is run……");
        return String.format("Hello %s. Biz two plus is down. %s", name);
    }
}
