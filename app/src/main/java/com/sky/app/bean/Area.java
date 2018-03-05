package com.sky.app.bean;

/**
 * 区域返回
 * Created by sky on 2017/3/21.
 */

public class Area extends  Page{
    private String one_dir_id;
    private String company_id;
    private String two_dir_id;
    private String two_dir_name;// "白下区"//名称
    private String orderBy;
    private String icon_image_url;
    private int   start;


    public String getIcon_image_url() {
        return icon_image_url;
    }

    public void setIcon_image_url(String icon_image_url) {
        this.icon_image_url = icon_image_url;
    }

    @Override
    public String toString() {
        return "Area{" +
                "one_dir_id='" + one_dir_id + '\'' +
                ", company_id='" + company_id + '\'' +
                ", two_dir_id='" + two_dir_id + '\'' +
                ", two_dir_name='" + two_dir_name + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", start=" + start +'\'' +

                ", icon_image_url=" + icon_image_url +
                '}';
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }




    public String getOne_dir_id() {
        return one_dir_id;
    }

    public void setOne_dir_id(String one_dir_id) {
        this.one_dir_id = one_dir_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
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
}
