package com.restaurant.dinner.demo.service.cooking;

import com.alibaba.dubbo.config.annotation.Service;
import com.restaurant.dinner.service.demo.api.recipe.DemoServiceOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(
        version = "1.0.0"
)
public class DemoServiceOneRpcImpl implements DemoServiceOne {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceOne.class);

    /*@Reference
    private DemoServiceOnePlus demoServiceTwo;*/

    @Override
    public String bizOne(String name) {
        logger.info("Biz one is run……");
        //return String.format("Hello %s. Biz one is down. %s", name, "|" + demoServiceTwo.bizOnePlus(name));
        return String.format("Hello %s. Biz one is down.", name);
    }
}
