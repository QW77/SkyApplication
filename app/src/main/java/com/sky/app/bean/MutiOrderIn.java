package com.sky.app.bean;

import java.util.List;

/**
 * 购物车个订单确认入参
 * Created by sky on 2017/3/21.
 */

public class MutiOrderIn extends BaseUser{
    private String remark;
    private String address_id;
    private List<String> ids;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
