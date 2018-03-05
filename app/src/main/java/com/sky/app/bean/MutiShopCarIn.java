package com.sky.app.bean;

import java.util.List;

/**
 * 购物车合并下单前查询商品信息入参
 */

public class MutiShopCarIn extends BaseUser{
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
