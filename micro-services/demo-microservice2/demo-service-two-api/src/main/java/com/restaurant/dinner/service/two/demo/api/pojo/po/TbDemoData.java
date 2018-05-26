package com.restaurant.dinner.service.two.demo.api.pojo.po;

import com.kitchen.common.api.pojo.po.PoBase;

public class TbDemoData extends PoBase {
    private Integer id;

    private String demoData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDemoData() {
        return demoData;
    }

    public void setDemoData(String demoData) {
        this.demoData = demoData == null ? null : demoData.trim();
    }

    @Override
    public void cloneSelf(Object o) throws CloneNotSupportedException {

    }
}