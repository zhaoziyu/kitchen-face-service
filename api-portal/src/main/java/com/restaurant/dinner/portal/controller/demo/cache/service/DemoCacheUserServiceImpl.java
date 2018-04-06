package com.restaurant.dinner.portal.controller.demo.cache.service;

import com.restaurant.dinner.portal.controller.demo.cache.model.DemoCacheUserInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/25
 */
@Component
public class DemoCacheUserServiceImpl implements IDemoCacheUserService {

    @Override
    @Cacheable(value = "DEMO_CACHE_USER_INFO", key = "#userInfo.userId", unless = "#result == null")
    public DemoCacheUserInfo selectUserName(DemoCacheUserInfo userInfo) throws InterruptedException {
        Thread.sleep(3000);

        userInfo.setUserName(userInfo.getUserId() + "的姓名");
        userInfo.setEmail(userInfo.getUserId() + "的邮箱");
        return userInfo;
    }

    @Override
    @Cacheable(value = "DEMO_CACHE_USER_INFO1", key = "#userInfo.userId", unless = "#result == null")
    public DemoCacheUserInfo selectUserName1(DemoCacheUserInfo userInfo) throws InterruptedException {
        Thread.sleep(1000);

        userInfo.setUserName(userInfo.getUserId() + "的姓名");
        userInfo.setEmail(userInfo.getUserId() + "的邮箱");
        return userInfo;
    }

    @Override
    @CacheEvict(value = "user-info")
    public void deleteUser(String userId) {
        System.out.println("删除用户，同时缓存清除");
    }

    @Override
    @CachePut(value="user-info", key = "#userInfo.userId")
    public DemoCacheUserInfo addUser(DemoCacheUserInfo userInfo) {
        System.out.println("添加用户，同时添加缓存");
        return userInfo;
    }
}
