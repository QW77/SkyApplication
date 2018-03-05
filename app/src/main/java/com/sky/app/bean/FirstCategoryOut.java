package com.sky.app.bean;

import java.util.List;

/**
 * 一级分类入参
 */

public class FirstCategoryOut extends Page {
    private List<FirstCategoryDetail> list;

    public List<FirstCategoryDetail> getList() {
        list.toString();
        return list;
    }

    public void setList(List<FirstCategoryDetail> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "FirstCategoryOut{" +
                "list=" + list +
                '}';
    }
}
