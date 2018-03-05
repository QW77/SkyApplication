package com.sky.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/24 0024.
 */

public class SearshDecorationLongAndlatitude {


    /**
     * total : 1.0
     * rows : 200.0
     * page : 1.0
     * list : [{"total":0,"rows":20,"page":1,"start":0,"orderBy":"create_time desc","one_dir_id":"CS100001","company_id":"CP2017021701","two_dir_id":"CS10000106","icon_image_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/10000101001.png","three_dir_id":"ZSC10000101001","three_dir_name":"板桥红太阳装饰城","longitude":"118.666361","latitude":"31.938381","type":3,"state":1}]
     */

    private double total;
    private double rows;
    private double page;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {


        /**
         * total : 0.0
         * rows : 20.0
         * page : 1.0
         * start : 0.0
         * orderBy : create_time desc
         * one_dir_id : CS100001
         * company_id : CP2017021701
         * two_dir_id : CS10000106
         * icon_image_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/10000101001.png
         * three_dir_id : ZSC10000101001
         * three_dir_name : 板桥红太阳装饰城
         * longitude : 118.666361
         * latitude : 31.938381
         * type : 3.0
         * state : 1.0
         */

        private double total;
        private double rows;
        private double page;
        private double start;
        private String orderBy;
        private String one_dir_id;
        private String company_id;
        private String two_dir_id;
        private String icon_image_url;
        private String three_dir_id;
        private String three_dir_name;
        private String longitude;
        private String latitude;
        private double type;
        private double state;

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

        public double getStart() {
            return start;
        }

        public void setStart(double start) {
            this.start = start;
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

        public String getIcon_image_url() {
            return icon_image_url;
        }

        public void setIcon_image_url(String icon_image_url) {
            this.icon_image_url = icon_image_url;
        }

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

        public double getType() {
            return type;
        }

        public void setType(double type) {
            this.type = type;
        }

        public double getState() {
            return state;
        }

        public void setState(double state) {
            this.state = state;
        }
        @Override
        public String toString() {
            return "ListBean{" +
                    "total=" + total +
                    ", rows=" + rows +
                    ", page=" + page +
                    ", start=" + start +
                    ", orderBy='" + orderBy + '\'' +
                    ", one_dir_id='" + one_dir_id + '\'' +
                    ", company_id='" + company_id + '\'' +
                    ", two_dir_id='" + two_dir_id + '\'' +
                    ", icon_image_url='" + icon_image_url + '\'' +
                    ", three_dir_id='" + three_dir_id + '\'' +
                    ", three_dir_name='" + three_dir_name + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", type=" + type +
                    ", state=" + state +
                    '}';
        }
    }



}
