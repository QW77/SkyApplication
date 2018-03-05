package com.sky.app.bean;

import java.util.List;

/**
 * 收藏的商品返回
 * Created by hongbang on 2017/5/7.
 */

public class CollectGoodsList extends Page {

    List<CollectGoods> list;
    private int  all_page;

    public int getAll_page() {
        return all_page;
    }

    public void setAll_page(int all_page) {
        this.all_page = all_page;
    }

    public List<CollectGoods> getList() {

        return list;
    }

    public void setList(List<CollectGoods> list) {
        this.list = list;
    }
}
