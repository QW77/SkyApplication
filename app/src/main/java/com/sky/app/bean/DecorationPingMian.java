package com.sky.app.bean;


import java.util.List;

/**
 * 装饰城平面图
 * Created by hongbang on 2017/5/3.
 */

public class DecorationPingMian {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : c162f06c-eae1-11e7-8bce-7cd30abdd1e6
         * decorative_id : ZSC10000101108
         * decorative_name : 正大百货家具装饰城
         * address_pic_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/3664615455058067931.jpg
         * address_title : 正大百货家具装饰城平面图
         * create_time : 2017-12-27 16:41:39
         */

        private String id;
        private String decorative_id;
        private String decorative_name;
        private String address_pic_url;
        private String address_title;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getAddress_pic_url() {
            return address_pic_url;
        }

        public void setAddress_pic_url(String address_pic_url) {
            this.address_pic_url = address_pic_url;
        }

        public String getAddress_title() {
            return address_title;
        }

        public void setAddress_title(String address_title) {
            this.address_title = address_title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "id='" + id + '\'' +
                    ", decorative_id='" + decorative_id + '\'' +
                    ", decorative_name='" + decorative_name + '\'' +
                    ", address_pic_url='" + address_pic_url + '\'' +
                    ", address_title='" + address_title + '\'' +
                    ", create_time='" + create_time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DecorationPingMian{" +
                "list=" + list +
                '}';
    }
}
