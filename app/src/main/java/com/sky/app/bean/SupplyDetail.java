package com.sky.app.bean;

/**
 * 供应详情
 */

public class SupplyDetail {
    private String orderBy;
    private String product_id;
    private int product_type;
    private String product_name;
    private String product_key_words;
    private String three_dir_id;
    private String product_desc;
    private String inventory_num;
    private String product_price_old;
    private String product_price_now;
    private String create_time;
    private String company_id;
    private String user_id;
    private String user_name;
    private String user_phone;
    private String user_pic_url;
    private int state;
    private int other_flag;
    private String longitude;
    private String latitude;
    private int num_good;
    private int num_comment;
    private int num_collect;
    private int num_read;
    private int is_collect;

    public int getProduct_type() {
        return product_type;
    }

    public void setProduct_type(int product_type) {
        this.product_type = product_type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_pic_url() {
        return user_pic_url;
    }

    public void setUser_pic_url(String user_pic_url) {
        this.user_pic_url = user_pic_url;
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

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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

    public String getProduct_key_words() {
        return product_key_words;
    }

    public void setProduct_key_words(String product_key_words) {
        this.product_key_words = product_key_words;
    }

    public String getThree_dir_id() {
        return three_dir_id;
    }

    public void setThree_dir_id(String three_dir_id) {
        this.three_dir_id = three_dir_id;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getInventory_num() {
        return inventory_num;
    }

    public void setInventory_num(String inventory_num) {
        this.inventory_num = inventory_num;
    }

    public String getProduct_price_old() {
        return product_price_old;
    }

    public void setProduct_price_old(String product_price_old) {
        this.product_price_old = product_price_old;
    }

    public String getProduct_price_now() {
        return product_price_now;
    }

    public void setProduct_price_now(String product_price_now) {
        this.product_price_now = product_price_now;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOther_flag() {
        return other_flag;
    }

    public void setOther_flag(int other_flag) {
        this.other_flag = other_flag;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "SupplyDetail{" +
                "orderBy='" + orderBy + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_type=" + product_type +
                ", product_name='" + product_name + '\'' +
                ", product_key_words='" + product_key_words + '\'' +
                ", three_dir_id='" + three_dir_id + '\'' +
                ", product_desc='" + product_desc + '\'' +
                ", inventory_num='" + inventory_num + '\'' +
                ", product_price_old='" + product_price_old + '\'' +
                ", product_price_now='" + product_price_now + '\'' +
                ", create_time='" + create_time + '\'' +
                ", company_id='" + company_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_pic_url='" + user_pic_url + '\'' +
                ", state=" + state +
                ", other_flag=" + other_flag +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", num_good=" + num_good +
                ", num_comment=" + num_comment +
                ", num_collect=" + num_collect +
                ", num_read=" + num_read +
                ", is_collect=" + is_collect +
                '}';
    }
}
