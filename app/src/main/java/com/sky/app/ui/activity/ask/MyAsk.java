package com.sky.app.ui.activity.ask;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class MyAsk {


    /**
     * total : 7.0
     * rows : 20.0
     * page : 1.0
     * all_page : 1.0
     * list : [{"id":"214c2282-79a6-4184-985b-ff57608a6542","eval_id":"FW50000123","eval_leval":"5","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-16 17:58:16","flag":"1","praise_num":"0","layer":"0"},{"id":"68a08b19-8e0f-4302-a3a1-892f5a60592a","eval_id":"FW50000123","eval_leval":"5","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-16 17:58:20","flag":"1","praise_num":"0","layer":"0"},{"id":"113b9cf5-4863-4986-a5ef-d73aaaf4add9","eval_id":"FW50000123","eval_leval":"5","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-16 17:58:22","flag":"1","praise_num":"0","layer":"0"},{"id":"4514dd5f-c64e-48a0-be79-43706406e46f","eval_id":"FW50000123","eval_leval":"5","eval_comments":"???","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-16 21:40:26","flag":"1","praise_num":"0","layer":"0"},{"id":"593a8f8b-7ee9-438f-912f-6ca96404a784","eval_id":"FW50000123","eval_leval":"5","eval_comments":"???","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-16 22:01:07","flag":"1","praise_num":"0","layer":"0"},{"id":"7095d76a-81d8-429c-ad3b-daaef6134d48","eval_id":"FW50000123","eval_leval":"5","eval_comments":"???","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-16 22:01:19","flag":"1","praise_num":"0","layer":"0"},{"id":"f1faca85-d7da-4538-bf98-6a66f647f619","eval_id":"FW50000123","eval_leval":"5","eval_comments":"???","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-16 22:03:15","flag":"1","praise_num":"0","layer":"0"}]
     */

    private double total;
    private double rows;
    private double page;
    private double all_page;
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

    public double getAll_page() {
        return all_page;
    }

    public void setAll_page(double all_page) {
        this.all_page = all_page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 214c2282-79a6-4184-985b-ff57608a6542
         * eval_id : FW50000123
         * eval_leval : 5
         * user_id : 171027017659
         * pic_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg
         * creator_id : 171027017659
         * create_time : 2018-01-16 17:58:16
         * flag : 1
         * praise_num : 0
         * layer : 0
         * eval_comments : ???
         */

        private String id;
        private String eval_id;
        private String eval_leval;
        private String user_id;
        private String pic_url;
        private String creator_id;
        private String create_time;
        private String flag;
        private String praise_num;
        private String layer;
        private String eval_comments;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEval_id() {
            return eval_id;
        }

        public void setEval_id(String eval_id) {
            this.eval_id = eval_id;
        }

        public String getEval_leval() {
            return eval_leval;
        }

        public void setEval_leval(String eval_leval) {
            this.eval_leval = eval_leval;
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

        public String getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(String creator_id) {
            this.creator_id = creator_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }

        public String getEval_comments() {
            return eval_comments;
        }

        public void setEval_comments(String eval_comments) {
            this.eval_comments = eval_comments;
        }
    }
}
