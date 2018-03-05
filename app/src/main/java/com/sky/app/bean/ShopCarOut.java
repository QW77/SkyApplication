package com.sky.app.bean;

import java.util.List;

/**
 * 购物车列表出参
 * @author wjx
 *
 */
public class ShopCarOut {
    private List<ShopCarList> list;

    public List<ShopCarList> getList() {
        return list;
    }

    public void setList(List<ShopCarList> list) {
        this.list = list;
    }
}