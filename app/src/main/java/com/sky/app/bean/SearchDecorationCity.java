package com.sky.app.bean;

/**
 * Created by hongbang on 2017/5/4.
 * t通过区域id查询呢装饰城
 *
 *  "two_dir_id":"CS10000101",  //区域ID
 "three_dir_name":"装饰城"  //装饰城名称 空则查询所有装饰城
 }
 */

public class SearchDecorationCity {

private  String two_dir_id;
private  String three_dir_name;

    public String getThree_dir_name() {
        return three_dir_name;
    }

    public void setThree_dir_name(String three_dir_name) {
        this.three_dir_name = three_dir_name;
    }

    public String getTwo_dir_id() {
        return two_dir_id;
    }

    public void setTwo_dir_id(String two_dir_id) {
        this.two_dir_id = two_dir_id;
    }


    @Override
    public String toString() {
        return "SearchDecorationCity{" +
                "two_dir_id='" + two_dir_id + '\'' +
                ", three_dir_name='" + three_dir_name + '\'' +
                '}';
    }
}
