package com.restaurant.dinner.demo.service.cooking;

import com.alibaba.dubbo.config.annotation.Service;
import com.restaurant.dinner.service.demo.api.recipe.DemoServiceTwo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DemoServiceTwoRpcImpl implements DemoServiceTwo {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceTwo.class);

    @Override
    public String bizTwo(String name) {
        logger.info("Biz two is run……");
        return String.format("Hello %s. Biz one is down. %s", name);
    }
}
