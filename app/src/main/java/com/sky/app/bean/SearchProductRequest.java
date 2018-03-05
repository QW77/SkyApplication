package com.sky.app.bean;

/**
 * Created by hongbang on 2017/5/8.
 * 查询商品请求
 */

public class SearchProductRequest extends Page{
    private String user_id;
    private String product_name;
    private String min_price;
    private String max_price;
    private String order_by;
    private String one_dir_id;
    private String two_dir_id;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public String getOne_dir_id() {
        return one_dir_id;
    }

    public void setOne_dir_id(String one_dir_id) {
        this.one_dir_id = one_dir_id;
    }

    public String getTwo_dir_id() {
        return two_dir_id;
    }

    public void setTwo_dir_id(String two_dir_id) {
        this.two_dir_id = two_dir_id;
    }

    @Override
    public String toString() {
        return "SearchProductRequest{" +
                "user_id='" + user_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", min_price='" + min_price + '\'' +
                ", max_price='" + max_price + '\'' +
                ", order_by='" + order_by + '\'' +
                ", one_dir_id='" + one_dir_id + '\'' +
                ", two_dir_id='" + two_dir_id + '\'' +
                ", state=" + state +
                '}';
    }
}

