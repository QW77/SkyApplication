package com.sky.shop.bean;

/**
 * 手续费出参
 * Created by sky on 2017/3/21.
 */

public class Rate {
    private String diction_id;
    private String function_id;
    private String type;
    private double value;
    private String extend_01;
    private String remark;

    public String getDiction_id() {
        return diction_id;
    }

    public void setDiction_id(String diction_id) {
        this.diction_id = diction_id;
    }

    public String getFunction_id() {
        return function_id;
    }

    public void setFunction_id(String function_id) {
        this.function_id = function_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getExtend_01() {
        return extend_01;
    }

    public void setExtend_01(String extend_01) {
        this.extend_01 = extend_01;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
