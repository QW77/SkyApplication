package com.sky.app.bean;

import java.io.Serializable;

/**
 * 店铺下的主页
 * @author wjx
 *
 */
public class ShopProductIn extends BaseUser implements Serializable{
    private String recom_product_size;
    private String all_product_size;

    public String getRecom_product_size() {
        return recom_product_size;
    }

    public void setRecom_product_size(String recom_product_size) {
        this.recom_product_size = recom_product_size;
    }

    public String getAll_product_size() {
        return all_product_size;
    }

    public void setAll_product_size(String all_product_size) {
        this.all_product_size = all_product_size;
    }
}