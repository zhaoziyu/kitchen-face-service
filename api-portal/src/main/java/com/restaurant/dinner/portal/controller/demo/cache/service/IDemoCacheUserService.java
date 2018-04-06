package com.restaurant.dinner.portal.controller.demo.cache.service;

import com.restaurant.dinner.portal.controller.demo.cache.model.DemoCacheUserInfo;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/25
 */
//@CacheConfig(cacheNames = "user-info")
public interface IDemoCacheUserService {
    DemoCacheUserInfo selectUserName(DemoCacheUserInfo userInfo) throws InterruptedException;

    DemoCacheUserInfo selectUserName1(DemoCacheUserInfo userInfo) throws InterruptedException;


    void deleteUser(String userId);

    DemoCacheUserInfo addUser(DemoCacheUserInfo userInfo);
}
