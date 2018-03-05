package com.sky.shop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺下的主页出参
 * @author wjx
 *
 */
public class ShopProductOut implements Serializable{
    private List<ShopProductDetail> list;

    public List<ShopProductDetail> getList() {
        return list;
    }

    public void setList(List<ShopProductDetail> list) {
        this.list = list;
    }
}