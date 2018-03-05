package com.sky.app.bean;

import java.util.List;

/**
 * 我的收藏出参
 * @author wjx
 *
 */
public class MyCollectOut extends Page{
    private List<MyCollect> list;

    public List<MyCollect> getList() {
        return list;
    }

    public void setList(List<MyCollect> list) {
        this.list = list;
    }
}