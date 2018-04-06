package com.restaurant.dinner.portal.controller.demo.cache.model;

import java.io.Serializable;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/25
 */
public class DemoCacheUserInfo implements Serializable {
    private String userId;
    private String userName;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
