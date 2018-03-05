package com.sky.shop.bean;

import java.io.Serializable;

/**
 * 店铺简介出参
 */

public class ProductIntroduceOut implements Serializable{
    private String user_id;
    private String nick_name;
    private String pic_url;
    private String personal_pic;
    private String qq;
    private String weixin;
    private int is_collect;
    private String mobile;
    private String main_business_desc;
    private String worktime;
    private String design_concept;
    private String awards_honor;
    private String representative_works;
    private String schooling_professional;
    private String post_code;
    private int num_collect;
    private String address;
    private int num_comment;
    private int num_good;
    private String shoper_desc;
    private String area_id;
    private String area_name;
    private String decorative_id;
    private String manufacturer_type_id;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getManufacturer_type_id() {
        return manufacturer_type_id;
    }

    public void setManufacturer_type_id(String manufacturer_type_id) {
        this.manufacturer_type_id = manufacturer_type_id;
    }

    public String getShoper_desc() {
        return shoper_desc;
    }

    public void setShoper_desc(String shoper_desc) {
        this.shoper_desc = shoper_desc;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getNum_comment() {
        return num_comment;
    }

    public void setNum_comment(int num_comment) {
        this.num_comment = num_comment;
    }

    public int getNum_good() {
        return num_good;
    }

    public void setNum_good(int num_good) {
        this.num_good = num_good;
    }

    public String getSchooling_professional() {
        return schooling_professional;
    }

    public void setSchooling_professional(String schooling_professional) {
        this.schooling_professional = schooling_professional;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNum_collect() {
        return num_collect;
    }

    public void setNum_collect(int num_collect) {
        this.num_collect = num_collect;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPersonal_pic() {
        return personal_pic;
    }

    public void setPersonal_pic(String personal_pic) {
        this.personal_pic = personal_pic;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getMain_business_desc() {
        return main_business_desc;
    }

    public void setMain_business_desc(String main_business_desc) {
        this.main_business_desc = main_business_desc;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDesign_concept() {
        return design_concept;
    }

    public void setDesign_concept(String design_concept) {
        this.design_concept = design_concept;
    }

    public String getAwards_honor() {
        return awards_honor;
    }

    public void setAwards_honor(String awards_honor) {
        this.awards_honor = awards_honor;
    }

    public String getRepresentative_works() {
        return representative_works;
    }

    public void setRepresentative_works(String representative_works) {
        this.representative_works = representative_works;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }
}
