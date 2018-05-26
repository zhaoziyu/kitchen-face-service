package com.restaurant.dinner.portal.controller.demo.rpc;

import com.alibaba.dubbo.config.annotation.Reference;
import com.restaurant.dinner.service.one.demo.api.recipe.DemoServiceOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DemoLocalService {

    private static final Logger logger = LoggerFactory.getLogger(DemoLocalService.class);

    @Reference(version = "1.0.0")
    private DemoServiceOne demoServiceOne;

    public void test() {
        logger.info(demoServiceOne.bizOne("DemoLocalService"));
    }
}
