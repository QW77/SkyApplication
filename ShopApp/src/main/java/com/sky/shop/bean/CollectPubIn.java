package com.sky.shop.bean;

/**
 * 收藏入参
 * Created by sky on 2017/3/21.
 */

public class CollectPubIn extends BaseUser{
    private String type;
    private String collect_value;
    private String page;

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

    private String rows;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCollect_value() {
        return collect_value;
    }

    public void setCollect_value(String collect_value) {
        this.collect_value = collect_value;
    }
}
