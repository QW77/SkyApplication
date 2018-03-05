package com.sky.shop.bean;

import java.util.List;

/**
 * Created by hongbang on 2017/5/8.
 */

public class ProductList extends Page{

    String all_page;
    List<ProductResponse> list;

    public String getAll_page() {
        return all_page;
    }

    public void setAll_page(String all_page) {
        this.all_page = all_page;
    }

    public List<ProductResponse> getList() {
        return list;
    }
    public void setList(List<ProductResponse> list) {
        this.list = list;
    }
}
