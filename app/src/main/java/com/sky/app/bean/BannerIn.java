package com.sky.app.bean;

/**
 * 忘记密码入参
 * Created by sky on 2017/3/21.
 */

public class BannerIn {
    private String start_type;
    private String end_type;

    public String getStart_type() {
        return start_type;
    }

    public void setStart_type(String start_type) {
        this.start_type = start_type;
    }

    public String getEnd_type() {
        return end_type;
    }

    public void setEnd_type(String end_type) {
        this.end_type = end_type;
    }

    @Override
    public String toString() {
        return "BannerIn{" +
                "start_type='" + start_type + '\'' +
                ", end_type='" + end_type + '\'' +
                '}';
    }
}
