package com.sky.app.bean;

/**
 * 购物车商品明细
 * @author wjx
 *
 */
public class ShopCarDetail {
    private boolean isSelected;
    private boolean type;
    private String cart_id;
    private String product_id;
    private String product_name;
    private String product_image_url;
    private int product_num;
    private String attr_name;
    private String attr_id;
    private double attr_price_now;
    private double product_price_now;

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public double getProduct_price_now() {
        return product_price_now;
    }

    public void setProduct_price_now(double product_price_now) {
        this.product_price_now = product_price_now;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
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

    public int getProduct_num() {
        return product_num;
    }

    public void setProduct_num(int product_num) {
        this.product_num = product_num;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public double getAttr_price_now() {
        return attr_price_now;
    }

    public void setAttr_price_now(double attr_price_now) {
        this.attr_price_now = attr_price_now;
    }
}