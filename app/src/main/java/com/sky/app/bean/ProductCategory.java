package com.sky.app.bean;

import java.util.List;

/**
 * 商品分类
 * Created by sky on 2017/3/21.
 */

public class ProductCategory {
    private String one_dir_id;
    private String one_dir_name;
    private String two_dir_id;
    private String two_dir_name;
    private String company_id;
    private String icon_image_url;
    List<ProductCategory> two_dirs;

    public List<ProductCategory> getTwo_dirs() {
        return two_dirs;
    }

    public void setTwo_dirs(List<ProductCategory> two_dirs) {
        this.two_dirs = two_dirs;
    }

    public String getOne_dir_id() {
        return one_dir_id;
    }

    public void setOne_dir_id(String one_dir_id) {
        this.one_dir_id = one_dir_id;
    }

    public String getOne_dir_name() {
        return one_dir_name;
    }

    public void setOne_dir_name(String one_dir_name) {
        this.one_dir_name = one_dir_name;
    }

    public String getTwo_dir_id() {
        return two_dir_id;
    }

    public void setTwo_dir_id(String two_dir_id) {
        this.two_dir_id = two_dir_id;
    }

    public String getTwo_dir_name() {
        return two_dir_name;
    }

    public void setTwo_dir_name(String two_dir_name) {
        this.two_dir_name = two_dir_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getIcon_image_url() {
        return icon_image_url;
    }

    public void setIcon_image_url(String icon_image_url) {
        this.icon_image_url = icon_image_url;
    }
}
