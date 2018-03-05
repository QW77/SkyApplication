package com.sky.app.bean;

import java.util.List;

/**
 * 收藏的发布返回
 * Created by hongbang on 2017/5/6.
 */

public class CollectPublishList extends Page {

    List<CollectPublish>  list;

    public List<CollectPublish> getList() {
        return list;
    }

    public void setList(List<CollectPublish> list) {
        this.list = list;
    }
}
