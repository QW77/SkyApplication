package com.sky.shop.bean;

import java.util.List;

/**
 * 用户信息
 * @author wjx
 *
 */
public class UserBeanList extends BaseUser{

    private int  all_page;

    public int getAll_page() {
        return all_page;
    }

    public void setAll_page(int all_page) {
        this.all_page = all_page;
    }

    private List<UserBeanDetail> list;

    public List<UserBeanDetail> getList() {
        return list;
    }

    public void setList(List<UserBeanDetail> list) {
        this.list = list;
    }
}