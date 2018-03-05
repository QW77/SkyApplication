package com.sky.app.bean;

/**
 * 我的收藏请求
 * Created by hongbang on 2017/5/7.
 */

public class CollectRequest {

    /**
     * "user_id ":"170406001362" //消息ID
     "page":1, //页数空则取第一页
     "rows":20 // 每页个数，空则取所有的
     "type":1  // 1 商品 2商户 3发布
     */

            String user_id;
            String page;
            String rows;
            String type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
