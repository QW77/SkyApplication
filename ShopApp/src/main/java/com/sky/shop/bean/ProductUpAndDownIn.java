package com.sky.shop.bean;

/**
 * 商品入参
 * Created by sky on 2017/3/21.
 */

public class ProductUpAndDownIn extends ProductIn {
    private int state;//0:下架 1：上架

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}