package com.sky.app.bean;

/**
 * 设置中绑定入参
 */

public class BindIn extends BaseUser{
    private int type;
    private String value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
