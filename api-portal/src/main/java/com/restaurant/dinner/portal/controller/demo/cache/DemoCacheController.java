package com.restaurant.dinner.portal.controller.demo.cache;

import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.portal.constant.CommonReturnCode;
import com.restaurant.dinner.portal.controller.demo.cache.model.DemoCacheUserInfo;
import com.restaurant.dinner.portal.controller.demo.cache.service.IDemoCacheUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 详见Service中的缓存注解及说明
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/25
 */
@RestController
@RequestMapping(value = "/demo/cache")
public class DemoCacheController {
    @Autowired
    private IDemoCacheUserService demoCacheUserService;

    @RequestMapping("/getUserName/{userId}")
    public JsonObjectVo<String> getUserName(@PathVariable String userId) throws InterruptedException {
        JsonObjectVo<String> result = new JsonObjectVo<>();

        DemoCacheUserInfo userInfo = new DemoCacheUserInfo();
        userInfo.setUserId(userId);
        userInfo = demoCacheUserService.selectUserName(userInfo);

        result.setData(userInfo.getUserName());
        result.setCompose(CommonReturnCode.SUCCESS);

        return result;
    }

    @RequestMapping("/getUserName1/{userId}")
    public JsonObjectVo<String> getUserName1(@PathVariable String userId) throws InterruptedException {
        JsonObjectVo<String> result = new JsonObjectVo<>();

        DemoCacheUserInfo userInfo = new DemoCacheUserInfo();
        userInfo.setUserId(userId);
        userInfo = demoCacheUserService.selectUserName1(userInfo);

        result.setData(userInfo.getUserName());
        result.setCompose(CommonReturnCode.SUCCESS);

        return result;
    }

    @RequestMapping("/deleteUser/{userId}")
    public JsonObjectVo<String> deleteUser(@PathVariable String userId) {
        JsonObjectVo<String> result = new JsonObjectVo<>();

        demoCacheUserService.deleteUser(userId);

        result.setCompose(CommonReturnCode.SUCCESS);

        return result;
    }

    @RequestMapping("/addUser/{userId}/{userName}/{email}")
    public JsonObjectVo<String> addUser(@PathVariable String userId, @PathVariable String userName, @PathVariable String email) {
        JsonObjectVo<String> result = new JsonObjectVo<>();

        DemoCacheUserInfo userInfo = new DemoCacheUserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(userName);
        userInfo.setEmail(email);
        userInfo = demoCacheUserService.addUser(userInfo);

        result.setCompose(CommonReturnCode.SUCCESS);

        return result;
    }



    /*
    Error:
    EL1008E: Property or field 'userId' cannot be found on object of type 'org.springframework.cache.interceptor.CacheExpressionRootObject' - maybe not public?
     */
    /*@Resource
    private DemoCacheService demoCacheService;
    @RequestMapping("/getUserName1")
    @Cacheable(value = "user-info", key="userId")
    public JsonObjectVo<String> getUserName1(String userId) throws InterruptedException {
        JsonObjectVo<String> result = new JsonObjectVo<>();

        result.setData(demoCacheService.selectUserName(userId));
        result.setCompose(CommonReturnCode.SUCCESS);

        return result;
    }*/
}
