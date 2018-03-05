package com.sky.app.bean;

import java.util.List;

/**
 * 供应商列表出参
 * Created by sky on 2017/3/21.
 */

public class SupplyList extends Page{
    private List<SupplyDetail> list;

    public List<SupplyDetail> getList() {
        return list;
    }

    public void setList(List<SupplyDetail> list) {
        this.list = list;
    }
}
