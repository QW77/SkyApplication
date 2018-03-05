package com.sky.shop.bean;

/**
 * Created by hongbang on 2017/5/8.
 */
public class ProductAttribute {
      String attr_id;
      String attr_name;
      int inventory_num;
      double attr_price_old;

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    double attr_price_now;

    public int getInventory_num() {
        return inventory_num;
    }

    public void setInventory_num(int inventory_num) {
        this.inventory_num = inventory_num;
    }

    public double getAttr_price_old() {
        return attr_price_old;
    }

    public void setAttr_price_old(double attr_price_old) {
        this.attr_price_old = attr_price_old;
    }

    public double getAttr_price_now() {
        return attr_price_now;
    }

    public void setAttr_price_now(double attr_price_now) {
        this.attr_price_now = attr_price_now;
    }
}
