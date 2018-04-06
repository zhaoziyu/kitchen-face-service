package com.restaurant.dinner.portal.controller.demo.sign.model;

import com.kitchen.common.api.pojo.ro.BaseTokenRequestObject;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/24
 */
public class DemoFlatBizInfo extends BaseTokenRequestObject {
    private String field1;
    private Integer field2;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public Integer getField2() {
        return field2;
    }

    public void setField2(Integer field2) {
        this.field2 = field2;
    }
}
