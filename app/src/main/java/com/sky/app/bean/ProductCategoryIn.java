package com.sky.app.bean;

/**
 * 商品分类入参
 * Created by sky on 2017/3/21.
 */

public class ProductCategoryIn {
    private String level;
    private String one_dir_id;
    private String user_id;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOne_dir_id() {
        return one_dir_id;
    }

    public void setOne_dir_id(String one_dir_id) {
        this.one_dir_id = one_dir_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
