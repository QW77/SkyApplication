package com.sky.app.bean;

import java.util.List;

/**
 * 购物车列表
 * @author wjx
 *
 */
public class ShopCarList {
    private boolean isSelected;//是否选中
    private boolean type;//完成 编辑
    private String seller_name;
    private String seller_id;
    private List<ShopCarDetail> products;

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

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public List<ShopCarDetail> getProducts() {
        return products;
    }

    public void setProducts(List<ShopCarDetail> products) {
        this.products = products;
    }
}