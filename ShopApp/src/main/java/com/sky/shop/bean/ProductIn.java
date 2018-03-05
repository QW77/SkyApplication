package com.sky.shop.bean;

/**
 * 商品入参
 * Created by sky on 2017/3/21.
 */

public class ProductIn extends BaseUser {
    private String product_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}