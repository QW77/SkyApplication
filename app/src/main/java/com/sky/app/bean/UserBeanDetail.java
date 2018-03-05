package com.sky.app.bean;

/**
 * 用户信息
 * @author wjx
 *
 */
public class UserBeanDetail extends UserBean{
    private String  city_name;
    private String  main_business_desc;
    private String  manufacturer_type_name;
    private String  province_name;
    private String  num_collect;
    private String seller_type;//店铺 个人主页 名片
    private Double longitude; //商户经度
    private Double latitude;  // 商户纬度


    private int page;
    private int other_flag;

    public int getOther_flag() {
        return other_flag;
    }

    public void setOther_flag(int other_flag) {
        this.other_flag = other_flag;
    }

    public String getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(String seller_type) {
        this.seller_type = seller_type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getMain_business_desc() {
        return main_business_desc;
    }

    public void setMain_business_desc(String main_business_desc) {
        this.main_business_desc = main_business_desc;
    }

    public String getManufacturer_type_name() {
        return manufacturer_type_name;
    }

    public void setManufacturer_type_name(String manufacturer_type_name) {
        this.manufacturer_type_name = manufacturer_type_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getNum_collect() {
        return num_collect;
    }

    public void setNum_collect(String num_collect) {
        this.num_collect = num_collect;
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "UserBeanDetail{" +
                "city_name='" + city_name + '\'' +
                ", main_business_desc='" + main_business_desc + '\'' +
                ", manufacturer_type_name='" + manufacturer_type_name + '\'' +
                ", province_name='" + province_name + '\'' +
                ", num_collect='" + num_collect + '\'' +
                ", seller_type='" + seller_type + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", page=" + page +
                ", other_flag=" + other_flag +
                '}';
    }
}