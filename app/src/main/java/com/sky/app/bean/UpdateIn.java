package com.sky.app.bean;

/**
 * 升级入参
 */

public class UpdateIn{
    private String app_type;
    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    @Override
    public String toString() {
        return "UpdateIn{" +
                "app_type='" + app_type + '\'' +
                '}';
    }
}


