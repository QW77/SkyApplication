package com.sky.app.bean;

/**
 * 购物车入参【删除， 更新】
 */

public class ShopCarIn extends BaseUser{
    private String cart_id;
    private int product_num;

    public int getProduct_num() {
        return product_num;
    }

    public void setProduct_num(int product_num) {
        this.product_num = product_num;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }
}
