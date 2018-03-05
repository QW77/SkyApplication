package com.sky.app.bean;

import java.io.Serializable;

/**
 * Created by hongbang on 2017/5/12.
 * 商户完善信息,比如上传营业执照啊,等等
 */

public class SellMessageComplete implements Serializable {

    String other_desc;

    public String getOther_desc() {
        return other_desc;
    }

    public void setOther_desc(String other_desc) {
        this.other_desc = other_desc;
    }

    SellUpload other_desc_bean;
    String user_id;

    public SellUpload getOther_desc_bean() {
        return other_desc_bean;
    }

    public void setOther_desc_bean(SellUpload other_desc_bean) {
        this.other_desc_bean = other_desc_bean;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOne_dir_id() {
        return one_dir_id;
    }

    public void setOne_dir_id(String one_dir_id) {
        this.one_dir_id = one_dir_id;
    }

    public String getTwo_dir_id() {
        return two_dir_id;
    }

    public void setTwo_dir_id(String two_dir_id) {
        this.two_dir_id = two_dir_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getDecorative_id() {
        return decorative_id;
    }

    public void setDecorative_id(String decorative_id) {
        this.decorative_id = decorative_id;
    }

    public String getDecorative_name() {
        return decorative_name;
    }

    public void setDecorative_name(String decorative_name) {
        this.decorative_name = decorative_name;
    }

    String one_dir_id;
    String two_dir_id;
    String area_id;
    String area_name;
    String decorative_id;
    String decorative_name;

}
