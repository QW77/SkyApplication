package com.sky.shop.bean;

import java.io.Serializable;

/**
 * Created by hongbang on 2017/5/12.
 * 商户完善信息,比如上传营业执照啊,等等
 */

public class SellMessageComplete implements Serializable {
    String other_desc;
    String user_id;
    String one_dir_id;
    String key_words;
    String area_id;
    String area_name;
    String decorative_id;
    String decorative_name;
    String manufacturer_type_id;
    String manufacturer_type_name;
    private String city_id;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getManufacturer_type_id() {
        return manufacturer_type_id;
    }

    public void setManufacturer_type_id(String manufacturer_type_id) {
        this.manufacturer_type_id = manufacturer_type_id;
    }

    public String getManufacturer_type_name() {
        return manufacturer_type_name;
    }

    public void setManufacturer_type_name(String manufacturer_type_name) {
        this.manufacturer_type_name = manufacturer_type_name;
    }

    public String getKey_words() {
        return key_words;
    }

    public void setKey_words(String key_words) {
        this.key_words = key_words;
    }

    public String getOther_desc() {
        return other_desc;
    }

    public void setOther_desc(String other_desc) {
        this.other_desc = other_desc;
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
}
