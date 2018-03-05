package com.sky.app.bean;

/**
 *
 * 取消我的订单入参
 */

public class CancelOrderIn extends BaseUser{
    private String order_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
