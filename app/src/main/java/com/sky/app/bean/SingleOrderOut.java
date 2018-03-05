package com.sky.app.bean;

/**
 * 单个订单确认出参
 * Created by sky on 2017/3/21.
 */

public class SingleOrderOut {
    private String order_id;
    private String main_order_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMain_order_id() {
        return main_order_id;
    }

    public void setMain_order_id(String main_order_id) {
        this.main_order_id = main_order_id;
    }
}
