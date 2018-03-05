package com.sky.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺下的商品数据
 * @author wjx
 *
 */
public class ShopProductDetail implements Serializable{
    private String two_dir_id;
    private String two_dir_name;
    private String two_dir_image_url;
    private String remark;
    private List<ProductResponse> recom_products;
    private List<ProductResponse> all_products;

    public String getTwo_dir_image_url() {
        return two_dir_image_url;
    }

    public void setTwo_dir_image_url(String two_dir_image_url) {
        this.two_dir_image_url = two_dir_image_url;
    }

    public String getTwo_dir_id() {
        return two_dir_id;
    }

    public void setTwo_dir_id(String two_dir_id) {
        this.two_dir_id = two_dir_id;
    }

    public String getTwo_dir_name() {
        return two_dir_name;
    }

    public void setTwo_dir_name(String two_dir_name) {
        this.two_dir_name = two_dir_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ProductResponse> getRecom_products() {
        return recom_products;
    }

    public void setRecom_products(List<ProductResponse> recom_products) {
        this.recom_products = recom_products;
    }

    public List<ProductResponse> getAll_products() {
        return all_products;
    }

    public void setAll_products(List<ProductResponse> all_products) {
        this.all_products = all_products;
    }
}