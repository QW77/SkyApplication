package com.sky.app.bean;

import java.util.List;

/**
 * 我的订单出参
 */

public class OrderOut extends Page{
    private List<OrderDetail> list;

    public List<OrderDetail> getList() {
        return list;
    }

    public void setList(List<OrderDetail> list) {
        this.list = list;
    }
}
