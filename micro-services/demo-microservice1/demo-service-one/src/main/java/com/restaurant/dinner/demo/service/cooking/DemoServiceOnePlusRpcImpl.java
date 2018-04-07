package com.restaurant.dinner.demo.service.cooking;

import com.alibaba.dubbo.config.annotation.Service;
import com.restaurant.dinner.service.demo.api.recipe.DemoServiceOnePlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DemoServiceOnePlusRpcImpl implements DemoServiceOnePlus {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceOnePlus.class);

    @Override
    public String bizOnePlus(String name) {
        logger.info("Biz one plus is run……");
        return String.format("Hello %s. Biz one plus is down. %s", name);
    }
}
