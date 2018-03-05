package com.sky.shop.bean;

/**
 * Created by hongbang on 2017/5/4.
 * t通过区域id查询呢装饰城
 *
 *  "two_dir_id":"CS10000101",  //区域ID
 "three_dir_name":"装饰城"  //装饰城名称 空则查询所有装饰城
 }
 */

public class SearchUser {

    private String  page;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    private String manufacturer_type_id;

    public String getManufacturer_type_id() {
        return manufacturer_type_id;
    }

    public void setManufacturer_type_id(String manufacturer_type_id) {
        this.manufacturer_type_id = manufacturer_type_id;
    }

    private  String two_dir_id;
private  String three_dir_id;

    private String  nick_name;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getThree_dir_id() {
        return three_dir_id;
    }

    public void setThree_dir_id(String three_dir_id) {
        this.three_dir_id = three_dir_id;
    }

    public String getTwo_dir_id() {
        return two_dir_id;
    }

    public void setTwo_dir_id(String two_dir_id) {
        this.two_dir_id = two_dir_id;
    }
}
