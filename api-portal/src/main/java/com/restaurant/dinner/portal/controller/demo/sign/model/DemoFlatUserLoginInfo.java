package com.restaurant.dinner.portal.controller.demo.sign.model;

import com.kitchen.common.api.pojo.ro.BaseRequestObject;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/24
 */
public class DemoFlatUserLoginInfo extends BaseRequestObject {
    private String deviceId;
    private String userName;
    private String encryptPassword;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }
}
