package com.sky.app.bean;

import java.util.List;

/**
 * banner出参
 * Created by sky on 2017/3/21.
 */

public class BannerOut extends Page{
    private List<BannerDetail> list;

    public List<BannerDetail> getList() {
        return list;
    }

    public void setList(List<BannerDetail> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BannerOut{" +
                "list=" + list +
                '}';
    }
}
