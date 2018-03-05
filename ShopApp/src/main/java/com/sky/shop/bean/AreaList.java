package com.sky.shop.bean;

import java.util.List;

/**
 * 区域返回
 * Created by hongbang on 2017/5/2.
 */

public class AreaList extends  Page {

    public  List<Area> list;

    public List<Area> getList() {
        return list;
    }

    public void setList(List<Area> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AreaList{" +
                "list=" + list +
                '}';
    }
}



