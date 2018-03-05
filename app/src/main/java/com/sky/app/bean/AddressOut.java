package com.sky.app.bean;

import java.util.List;

/**
 * 收货地址列表
 * @author wjx
 *
 */
public class AddressOut {
    private List<AddressDetail> list;

    public List<AddressDetail> getList() {
        return list;
    }

    public void setList(List<AddressDetail> list) {
        this.list = list;
    }
}