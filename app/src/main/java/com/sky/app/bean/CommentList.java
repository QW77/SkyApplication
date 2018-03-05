package com.sky.app.bean;

import java.util.List;

/**
 * Created by hongbang on 2017/5/9.
 * 商品详情,评论列表返回
 */

public class CommentList extends Page{

            String all_page;

    public String getAll_page() {
        return all_page;
    }

    public void setAll_page(String all_page) {
        this.all_page = all_page;
    }

    List<CommentResponse>  list;

    public List<CommentResponse> getList() {
        return list;
    }

    public void setList(List<CommentResponse> list) {
        this.list = list;
    }
}
