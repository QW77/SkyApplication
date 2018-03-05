package com.sky.app.bean;

import java.util.List;

/**
 * 购物车合并下单前查询商品信息入参
 */

public class MutiShopCarOut {
    private List<ShopCarDetail> list;

    public List<ShopCarDetail> getList() {
        return list;
    }

    public void setList(List<ShopCarDetail> list) {
        this.list = list;
    }
}
