package com.sky.app.bean;

/**
 * 订单详细
 */

public class OrderDetail extends BaseUser{
    private String order_id;
    private String product_id;
    private String product_name;
    private int product_num;
    private String create_time;
    private String company_id;
    private String remark;
    private int pay_state;
    private int state;
    private String attr_id;
    private String attr_name;
    private String shop_user_id;
    private String seller_name;
    private String province_name;
    private String city_name;
    private String area_name;
    private String address;
    private String product_image_url;
    private double attr_price_old;
    private double attr_price_now;
    private double order_price;

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
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

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public int getProduct_num() {
        return product_num;
    }

    public void setProduct_num(int product_num) {
        this.product_num = product_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPay_state() {
        return pay_state;
    }

    public void setPay_state(int pay_state) {
        this.pay_state = pay_state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

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

    public String getShop_user_id() {
        return shop_user_id;
    }

    public void setShop_user_id(String shop_user_id) {
        this.shop_user_id = shop_user_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
