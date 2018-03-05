package com.sky.shop.bean;

/**
 * 所有商品
 * Created by zhanglf on 2017/6/9.
 *
 */

public class CollectGoods {

            String product_id;
            String product_name;
            String product_image_url;
            String product_price_old;
            String product_price_now;
            String create_time;
            int num_good;
            int num_comment;
            int num_collect;
            String seller_name;

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
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

    public String getProduct_price_old() {
        return product_price_old;
    }

    public void setProduct_price_old(String product_price_old) {
        this.product_price_old = product_price_old;
    }

    public String getProduct_price_now() {
        return product_price_now;
    }

    public void setProduct_price_now(String product_price_now) {
        this.product_price_now = product_price_now;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getNum_read() {
        return num_read;
    }

    public void setNum_read(String num_read) {
        this.num_read = num_read;
    }

    public String getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(String buy_num) {
        this.buy_num = buy_num;
    }

    String num_read;
            String buy_num;
}
