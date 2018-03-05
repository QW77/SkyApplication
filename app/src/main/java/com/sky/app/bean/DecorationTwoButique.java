package com.sky.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class DecorationTwoButique {

    /**
     * total : 7.0
     * rows : 20.0
     * page : 1.0
     * all_page : 1.0
     * list : [{"user_id":"171122026206","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/6120223479768599462.jpg","nick_name":"马斯柯矿物质漆","pwd":"","birthday":"","real_name":"","weixin":"","qq":"","address":"建邺区应天大街608号长江装饰城油漆厅C13","driver_license":"","idcard_front":"","idcard_back":"","other_flag":1,"province_name":"江苏","city_name":"南京","num_good":0,"num_comment":0,"num_collect":0,"num_read":48,"main_business_desc":"马斯柯矿物油漆","manufacturer_type_name":"其他","login_user_name":"","seller_type":"店铺","advertist_pic_url":""},{"user_id":"170917008016","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/7196075216883339782.jpg","nick_name":"金牛公元联塑管材","pwd":"","birthday":"","real_name":"","weixin":"","qq":"","address":"建邺区应天大街608号长江装饰城东区外包间5","driver_license":"","idcard_front":"","idcard_back":"","area_name":"雨花台区","other_flag":1,"province_name":"江苏","city_name":"南京","num_good":0,"num_comment":0,"num_collect":1,"num_read":50,"main_business_desc":"伟星、中财、金牛、公元、保利、联塑各种管材；","manufacturer_type_name":"其他","login_user_name":"","seller_type":"店铺","advertist_pic_url":""},{"user_id":"170917007951","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/5407122959262741786.jpg","nick_name":"西蒙开关","pwd":"","birthday":"","real_name":"","weixin":"","qq":"","address":"建邺区应天大街608号长江装饰城东楼2-31","driver_license":"","idcard_front":"","idcard_back":"","area_name":"雨花台区","other_flag":1,"province_name":"江苏","city_name":"南京","num_good":0,"num_comment":0,"num_collect":0,"num_read":70,"main_business_desc":"西蒙开关","manufacturer_type_name":"其他","login_user_name":"","seller_type":"店铺","advertist_pic_url":""},{"user_id":"170917007929","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/49271080520627701.jpg","nick_name":"一声喊照明","pwd":"","birthday":"","real_name":"","weixin":"","qq":"","address":"建邺区应天大街608号长江装饰城2楼","driver_license":"","idcard_front":"","idcard_back":"","area_name":"雨花台区","other_flag":1,"province_name":"江苏","city_name":"南京","num_good":0,"num_comment":0,"num_collect":1,"num_read":56,"main_business_desc":"各种照明灯具；","manufacturer_type_name":"其他","login_user_name":"","seller_type":"店铺","advertist_pic_url":""},{"user_id":"170917007904","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/7821096359835678379.jpg","nick_name":"贝犀五金","pwd":"","birthday":"","real_name":"","weixin":"","qq":"","address":"建邺区应天大街608号长江装饰城东区新大楼2-8","driver_license":"","idcard_front":"","idcard_back":"","area_name":"雨花台区","other_flag":1,"province_name":"江苏","city_name":"南京","num_good":0,"num_comment":0,"num_collect":1,"num_read":57,"main_business_desc":"装饰五金、家具五金、门控五金等。","manufacturer_type_name":"其他","login_user_name":"","seller_type":"店铺","advertist_pic_url":""},{"user_id":"170917007887","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/7651535347042747941.jpg","nick_name":"贝尔地板","pwd":"","birthday":"","real_name":"","weixin":"","qq":"","address":"建邺区应天大街608号长江装饰城精品区1-12","driver_license":"","idcard_front":"","idcard_back":"","area_name":"雨花台区","other_flag":1,"province_name":"江苏","city_name":"南京","num_good":0,"num_comment":0,"num_collect":1,"num_read":110,"main_business_desc":"地板","manufacturer_type_name":"其他","login_user_name":"","seller_type":"店铺","advertist_pic_url":""},{"user_id":"170916007600","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/5345820855250515707.jpg","nick_name":"格林地板","pwd":"","birthday":"","real_name":"","weixin":"","qq":"","address":"建邺区应天大街608号长江装饰城精品厅北区1-01","driver_license":"","idcard_front":"","idcard_back":"","area_name":"雨花台区","other_flag":1,"province_name":"江苏","city_name":"南京","num_good":0,"num_comment":0,"num_collect":1,"num_read":130,"main_business_desc":"GREEN FLOORING格林地板","manufacturer_type_name":"其他","login_user_name":"","seller_type":"店铺","advertist_pic_url":""}]
     */

    private double total;
    private double rows;
    private double page;
    private double all_page;
    private List<DecorationTwoButique.ListBean> list;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getRows() {
        return rows;
    }

    public void setRows(double rows) {
        this.rows = rows;
    }

    public double getPage() {
        return page;
    }

    public void setPage(double page) {
        this.page = page;
    }

    public double getAll_page() {
        return all_page;
    }

    public void setAll_page(double all_page) {
        this.all_page = all_page;
    }

    public List<DecorationTwoButique.ListBean> getList() {
        return list;
    }

    public void setList(List<DecorationTwoButique.ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * user_id : 171122026206
         * pic_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/6120223479768599462.jpg
         * nick_name : 马斯柯矿物质漆
         * pwd :
         * birthday :
         * real_name :
         * weixin :
         * qq :
         * address : 建邺区应天大街608号长江装饰城油漆厅C13
         * driver_license :
         * idcard_front :
         * idcard_back :
         * other_flag : 1.0
         * province_name : 江苏
         * city_name : 南京
         * num_good : 0.0
         * num_comment : 0.0
         * num_collect : 0.0
         * num_read : 48.0
         * main_business_desc : 马斯柯矿物油漆
         * manufacturer_type_name : 其他
         * login_user_name :
         * seller_type : 店铺
         * advertist_pic_url :
         * area_name : 雨花台区
         */

        private String user_id;
        private String pic_url;
        private String nick_name;
        private String pwd;
        private String birthday;
        private String real_name;
        private String weixin;
        private String qq;
        private String address;
        private String driver_license;
        private String idcard_front;
        private String idcard_back;
        private double other_flag;
        private String province_name;
        private String city_name;
        private double num_good;
        private double num_comment;
        private double num_collect;
        private double num_read;
        private String main_business_desc;
        private String manufacturer_type_name;
        private String login_user_name;
        private String seller_type;
        private String advertist_pic_url;
        private String area_name;

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

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDriver_license() {
            return driver_license;
        }

        public void setDriver_license(String driver_license) {
            this.driver_license = driver_license;
        }

        public String getIdcard_front() {
            return idcard_front;
        }

        public void setIdcard_front(String idcard_front) {
            this.idcard_front = idcard_front;
        }

        public String getIdcard_back() {
            return idcard_back;
        }

        public void setIdcard_back(String idcard_back) {
            this.idcard_back = idcard_back;
        }

        public double getOther_flag() {
            return other_flag;
        }

        public void setOther_flag(double other_flag) {
            this.other_flag = other_flag;
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

        public double getNum_good() {
            return num_good;
        }

        public void setNum_good(double num_good) {
            this.num_good = num_good;
        }

        public double getNum_comment() {
            return num_comment;
        }

        public void setNum_comment(double num_comment) {
            this.num_comment = num_comment;
        }

        public double getNum_collect() {
            return num_collect;
        }

        public void setNum_collect(double num_collect) {
            this.num_collect = num_collect;
        }

        public double getNum_read() {
            return num_read;
        }

        public void setNum_read(double num_read) {
            this.num_read = num_read;
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

        public String getLogin_user_name() {
            return login_user_name;
        }

        public void setLogin_user_name(String login_user_name) {
            this.login_user_name = login_user_name;
        }

        public String getSeller_type() {
            return seller_type;
        }

        public void setSeller_type(String seller_type) {
            this.seller_type = seller_type;
        }

        public String getAdvertist_pic_url() {
            return advertist_pic_url;
        }

        public void setAdvertist_pic_url(String advertist_pic_url) {
            this.advertist_pic_url = advertist_pic_url;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }
    }

}
