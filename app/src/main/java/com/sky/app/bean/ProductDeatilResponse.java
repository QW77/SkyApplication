package com.sky.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hongbang on 2017/5/8.
 */

public class ProductDeatilResponse implements Serializable{
    Seller  seller;
    private List<ProductAttribute> attrs;
            String product_id;
            String product_name;
    private List<String> product_image_urls;

    public List<String> getProduct_image_urls() {
        return product_image_urls;
    }

    public void setProduct_image_urls(List<String> product_image_urls) {
        this.product_image_urls = product_image_urls;
    }

    public List<ProductAttribute> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<ProductAttribute> attrs) {
        this.attrs = attrs;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
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

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
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

    public String getOther_flag() {
        return other_flag;
    }

    public void setOther_flag(String other_flag) {
        this.other_flag = other_flag;
    }

    public int getNum_good() {
        return num_good;
    }

    public void setNum_good(int num_good) {
        this.num_good = num_good;
    }

    public int getNum_comment() {
        return num_comment;
    }

    public void setNum_comment(int num_comment) {
        this.num_comment = num_comment;
    }

    public int getNum_collect() {
        return num_collect;
    }

    public void setNum_collect(int num_collect) {
        this.num_collect = num_collect;
    }

    public int getNum_read() {
        return num_read;
    }

    public void setNum_read(int num_read) {
        this.num_read = num_read;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    String product_image_url;
    String product_desc;
    double product_price_old;
    double product_price_now;
    String other_flag;
    int num_good;
    int num_comment;
    int num_collect;
    int num_read;
    String user_id;
    int is_collect;
    String store_activity;
    private String product_key_words;

    public String getProduct_key_words() {
        return product_key_words;
    }

    public void setProduct_key_words(String product_key_words) {
        this.product_key_words = product_key_words;
    }

    public String getStore_activity() {
        return store_activity;
    }

    public void setStore_activity(String store_activity) {
        this.store_activity = store_activity;
    }
}
