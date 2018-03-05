package com.sky.app.bean;

import java.util.List;

/**
 * 装饰城返回
 * Created by hongbang on 2017/5/3.
 */

public class DecorationCityList extends Page {

    public List<DecorationCity> list;


    public List<DecorationCity> getList() {
        return list;
    }

    public void setList(List<DecorationCity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DecorationCityList{" +
                "list=" + list +
                '}';
    }
}
