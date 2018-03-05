package com.sky.app.bean;

import java.util.List;

/**
 * 供应出参
 */

public class SupplyOut extends Page{

    private List<SupplyDetail> list;

    public List<SupplyDetail> getList() {
        return list;
    }

    public void setList(List<SupplyDetail> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SupplyOut{" +
                "list=" + list +
                '}';
    }
}
