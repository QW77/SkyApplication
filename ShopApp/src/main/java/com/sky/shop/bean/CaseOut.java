package com.sky.shop.bean;

import java.util.List;

/**
 * 案例出参
 * @author wjx
 *
 */
public class CaseOut extends Page{
    private List<Case> list;

    public List<Case> getList() {
        return list;
    }

    public void setList(List<Case> list) {
        this.list = list;
    }
}