package com.sky.shop.bean;

/**
 * 装饰城返回
 * Created by hongbang on 2017/5/3.
 */

public class DecorationCity extends Page {

     private String manufacturer_type_id;
    private String company_id;
    private String icon_image_url;
    private String one_dir_id;
    private String one_dir_name;
    private String two_dir_name;
    private String three_dir_id;
    private String three_dir_name;

    public String getThree_dir_id() {
        return three_dir_id;
    }

    public void setThree_dir_id(String three_dir_id) {
        this.three_dir_id = three_dir_id;
    }

    public String getThree_dir_name() {
        return three_dir_name;
    }

    public void setThree_dir_name(String three_dir_name) {
        this.three_dir_name = three_dir_name;
    }

    private String orderBy;
    private String remark;
    private String user_id;
    private double start;
    private double type;

    public String getManufacturer_type_id() {
        return manufacturer_type_id;
    }

    public void setManufacturer_type_id(String manufacturer_type_id) {
        this.manufacturer_type_id = manufacturer_type_id;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getType() {
        return type;
    }

    public void setType(double type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DecorationCity{" +
                "manufacturer_type_id='" + manufacturer_type_id + '\'' +
                ", company_id='" + company_id + '\'' +
                ", icon_image_url='" + icon_image_url + '\'' +
                ", one_dir_id='" + one_dir_id + '\'' +
                ", one_dir_name='" + one_dir_name + '\'' +
                ", two_dir_name='" + two_dir_name + '\'' +
                ", three_dir_id='" + three_dir_id + '\'' +
                ", three_dir_name='" + three_dir_name + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", remark='" + remark + '\'' +
                ", user_id='" + user_id + '\'' +
                ", start=" + start +
                ", type=" + type +
                '}';
    }
}
