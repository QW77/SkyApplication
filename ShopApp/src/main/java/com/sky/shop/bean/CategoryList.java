package com.sky.shop.bean;

import java.util.List;

/**
 * 分类返回
 * Created by hongbang on 2017/5/3.
 */

public class CategoryList extends Page {

    public List<Category> list;
    public List<Category> retData;

    public List<Category> getRetData() {
        return retData;
    }

    public void setRetData(List<Category> retData) {
        this.retData = retData;
    }

    public List<Category> getList() {
        return list;
    }

    public void setList(List<Category> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CategoryList{" +
                "list=" + list +
                ", retData=" + retData +
                '}';
    }
}
