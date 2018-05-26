package com.restaurant.dinner.demo.service.one.cooking;

import com.alibaba.dubbo.config.annotation.Service;
import com.restaurant.dinner.demo.service.one.dao.TbDemoDataMapper;
import com.restaurant.dinner.service.one.demo.api.pojo.po.TbDemoData;
import com.restaurant.dinner.service.one.demo.api.recipe.DemoServiceOnePlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(
        version = "1.0.0",
        timeout = 5000
)
@Component
public class DemoServiceOnePlusRpcImpl implements DemoServiceOnePlus {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceOnePlus.class);

    @Autowired
    private TbDemoDataMapper tbDemoDataMapper;

    @Override
    public String bizOnePlus(String name) {
        logger.info("Biz one plus is running……");
        logger.info("从数据库中查询到的数据");
        TbDemoData tbDemoData = tbDemoDataMapper.selectByPrimaryKey(1);
        logger.info("数据为：{}", tbDemoData.getDemoData());

        logger.info("执行修改数据库方法");
        tbDemoData.setDemoData(tbDemoData.getDemoData()+"|");
        tbDemoDataMapper.updateByPrimaryKeySelective(tbDemoData);
        logger.info("数据库修改完成");

        return String.format("Hello %s. Biz one plus is down.", name);
    }

    @Override
    public void bizOneAsync(String name) {
        logger.info("Hi {} Biz one async is run……", name);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Hi {} Biz one async is down……", name);
    }
}
