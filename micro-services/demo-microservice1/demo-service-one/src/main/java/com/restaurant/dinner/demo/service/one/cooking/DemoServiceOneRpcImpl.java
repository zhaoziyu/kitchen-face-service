package com.restaurant.dinner.demo.service.one.cooking;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.restaurant.dinner.service.one.demo.api.recipe.DemoServiceOne;
import com.restaurant.dinner.service.one.demo.api.recipe.DemoServiceOnePlus;
import com.restaurant.dinner.service.two.demo.api.recipe.DemoServiceTwo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(
        version = "1.0.0",
        timeout = 5000,
        retries = -1
)
public class DemoServiceOneRpcImpl implements DemoServiceOne {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceOne.class);

    @Reference
    private DemoServiceTwo demoServiceTwo;

    @Autowired
    private DemoServiceOnePlus demoServiceOnePlus;

    @Override
    public String bizOne(String name) {
        logger.info("Biz one is run……");

        logger.info("调用本地Spring服务");
        String resultBizOnePlus = demoServiceOnePlus.bizOnePlus(name);

        logger.info("调用Dubbo服务");
        String resultBizTwo = demoServiceTwo.bizTwo(name);

        return String.format("Hello %s. Biz one is down. %s %s", name, " |" + resultBizOnePlus, " |" + resultBizTwo);
    }
}
