package com.sky.shop.bean;

/**
 * Created by hongbang on 2017/5/8.
 * 商品详情请求
 */

public class ProductDetailRequest {

           String product_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    String user_id;
}
