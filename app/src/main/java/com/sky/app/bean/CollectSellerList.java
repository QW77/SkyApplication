package com.sky.app.bean;

import java.util.List;

/**
 * 收藏的店铺返回
 * Created by hongbang on 2017/5/7.
 */

public class CollectSellerList extends Page {

    List<CollectSeller>  list;

    public List<CollectSeller> getList() {
        return list;
    }

    public void setList(List<CollectSeller> list) {
        this.list = list;
    }
}
