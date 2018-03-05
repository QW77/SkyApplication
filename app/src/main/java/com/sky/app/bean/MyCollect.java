package com.sky.app.bean;

/**
 * 我的收藏
 * @author wjx
 *
 */
public class MyCollect {
    private String product_id;
    private String product_name;
    private String product_image_url;
    private double product_price_old;
    private double product_price_now;
    private int num_good;
    private int num_comment;
    private int num_collect;
    private int num_read;
    private int buy_num;
    private String seller_name;

    private String user_id;
    private String pic_url;
    private String nick_name;
    private String main_business_desc;
    private String create_time;
    private String province_name;
    private String city_name;
    private String area_name;
    private String address;
    private String seller_type;

    public String getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(String seller_type) {
        this.seller_type = seller_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getMain_business_desc() {
        return main_business_desc;
    }

    public void setMain_business_desc(String main_business_desc) {
        this.main_business_desc = main_business_desc;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public double getProduct_price_old() {
        return product_price_old;
    }

    public void setProduct_price_old(double product_price_old) {
        this.product_price_old = product_price_old;
    }

    public double getProduct_price_now() {
        return product_price_now;
    }

    public void setProduct_price_now(double product_price_now) {
        this.product_price_now = product_price_now;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getNum_good() {
        return num_good;
    }

    public void setNum_good(int num_good) {
        this.num_good = num_good;
    }

    public int getNum_comment() {
        return num_comment;
    }

    public void setNum_comment(int num_comment) {
        this.num_comment = num_comment;
    }

    public int getNum_collect() {
        return num_collect;
    }

    public void setNum_collect(int num_collect) {
        this.num_collect = num_collect;
    }

    public int getNum_read() {
        return num_read;
    }

    public void setNum_read(int num_read) {
        this.num_read = num_read;
    }

    public int getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(int buy_num) {
        this.buy_num = buy_num;
    }
}