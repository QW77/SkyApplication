package com.sky.shop.bean;

import java.util.List;

/**
 * 提现记录
 * Created by sky on 2017/3/21.
 */

public class ApplyRecordOut extends Page {
    private List<ApplyRecordBean> list;

    public List<ApplyRecordBean> getList() {
        return list;
    }

    public void setList(List<ApplyRecordBean> list) {
        this.list = list;
    }
}