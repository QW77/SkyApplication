package com.sky.shop.bean;

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
    private int page;

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
}