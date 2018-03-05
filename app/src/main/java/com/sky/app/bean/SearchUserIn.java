package com.sky.app.bean;

/**
 * 搜索用户入参
 * Created by sky on 2017/3/21.
 */

public class SearchUserIn {
    private String manufacturer_type_id;
    private String three_dir_id;
    private String two_dir_id;

    public String getManufacturer_type_id() {
        return manufacturer_type_id;
    }

    public void setManufacturer_type_id(String manufacturer_type_id) {
        this.manufacturer_type_id = manufacturer_type_id;
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
