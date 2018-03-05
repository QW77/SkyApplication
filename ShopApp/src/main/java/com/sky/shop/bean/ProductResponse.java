package com.sky.shop.bean;

/**
 * Created by hongbang on 2017/5/8.
 * 搜索产品,产品的返回
 */

public class ProductResponse {

    String product_id;
    String product_name;
    String product_image_url;
    int num_good;
    double product_price_old;
    double product_price_now;
    int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public double getProduct_price_old() {
        return product_price_old;
    }

    public void setProduct_price_old(double product_price_old) {
        this.product_price_old = product_price_old;
    }

    public double getProduct_price_now() {
        return product_price_now;
    }

    public void setProduct_price_now(double product_price_now) {
        this.product_price_now = product_price_now;
    }

    public int getNum_good() {
        return num_good;
    }

    public void setNum_good(int num_good) {
        this.num_good = num_good;
    }
}
