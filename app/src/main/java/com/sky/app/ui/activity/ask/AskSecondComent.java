package com.sky.app.ui.activity.ask;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class AskSecondComent {
    /**
     * total : 8
     * rows : 20
     * page : 1
     * all_page : 1
     * list : [{"id":"a76fea6d-302d-4605-8080-a9b1ed7393fa","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"一九九","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-18 14:59:37","praise_num":"0","layer":"1","fileList":[]},{"id":"d554b39a-9170-4f7b-8d32-0033ccc75f8a","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"大航海","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-18 17:55:06","praise_num":"0","layer":"1","fileList":[]},{"id":"cde24502-63fe-4160-9960-39da7eb7a908","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"太古汇","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-19 10:24:45","praise_num":"0","layer":"1","fileList":[]},{"id":"1d7b6cc0-891e-4cb6-b2ae-29d673789d77","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"电话","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-19 11:54:45","praise_num":"0","layer":"1","fileList":[]},{"id":"55beffbf-1f49-48fa-89b6-74d3c72594ff","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"很多基督教","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-19 17:56:49","praise_num":"0","layer":"1","fileList":[]},{"id":"f56d7149-04a3-4231-a71b-6ff9a8d16277","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/image/ea2110938a62f2f2acce2bbbebc0bf31.png","eval_comments":"回到家","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-20 09:35:33","praise_num":"0","layer":"1","fileList":[{"id":"34da914c-fd82-11e7-8bce-7cd30abdd1e6","file_id":"f56d7149-04a3-4231-a71b-6ff9a8d16277","file_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/image/ea2110938a62f2f2acce2bbbebc0bf31.png","create_time":"2018-01-20 09:35:33"}]},{"id":"9446791c-17a9-4cf2-8893-61cd087b64db","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"规划","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-20 09:43:21","praise_num":"0","layer":"1","fileList":[]},{"id":"c5ff2f83-0c92-4e4b-9587-fcc1dd5a1450","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-20 13:36:08","praise_num":"0","layer":"1","fileList":[]}]
     */

    private int total;
    private int rows;
    private int page;
    private int all_page;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getAll_page() {
        return all_page;
    }

    public void setAll_page(int all_page) {
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
         * id : a76fea6d-302d-4605-8080-a9b1ed7393fa
         * eval_id : FW50000101
         * reply_id : 88a35472-9199-47c8-99e1-87457cedeed6
         * eval_leval : 5
         * eval_comments : 一九九
         * user_id : 171027017659
         * pic_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg
         * creator_id : 171027017659
         * create_time : 2018-01-18 14:59:37
         * praise_num : 0
         * layer : 1
         * fileList : []
         * eval_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/image/ea2110938a62f2f2acce2bbbebc0bf31.png
         */

        private String id;
        private String eval_id;
        private String reply_id;
        private String eval_leval;
        private String eval_comments;
        private String user_id;
        private String pic_url;
        private String creator_id;
        private String create_time;
        private String praise_num;
        private String layer;
        private String eval_url;
        private List<FileListBean> fileList;

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

        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }

        public String getEval_leval() {
            return eval_leval;
        }

        public void setEval_leval(String eval_leval) {
            this.eval_leval = eval_leval;
        }

        public String getEval_comments() {
            return eval_comments;
        }

        public void setEval_comments(String eval_comments) {
            this.eval_comments = eval_comments;
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

        public String getEval_url() {
            return eval_url;
        }

        public void setEval_url(String eval_url) {
            this.eval_url = eval_url;
        }

        public List<FileListBean> getFileList() {
            return fileList;
        }

        public void setFileList(List<FileListBean> fileList) {
            this.fileList = fileList;
        }
    }

    public static class FileListBean {
        /**
         * id : 34da914c-fd82-11e7-8bce-7cd30abdd1e6
         * file_id : f56d7149-04a3-4231-a71b-6ff9a8d16277
         * file_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/image/ea2110938a62f2f2acce2bbbebc0bf31.png
         * create_time : 2018-01-20 09:35:33
         */

        private String id;
        private String file_id;
        private String file_url;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFile_id() {
            return file_id;
        }

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public String getFile_url() {
            return file_url;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

//    /**
//     * total : 4.0
//     * rows : 20.0
//     * page : 1.0
//     * all_page : 1.0
//     * list : [{"id":"a76fea6d-302d-4605-8080-a9b1ed7393fa","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"一九九","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-18 14:59:37","praise_num":"0","layer":"1","fileList":[]},{"id":"d554b39a-9170-4f7b-8d32-0033ccc75f8a","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"大航海","user_id":"171027017659","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg","creator_id":"171027017659","create_time":"2018-01-18 17:55:06","praise_num":"0","layer":"1","fileList":[]},{"id":"cde24502-63fe-4160-9960-39da7eb7a908","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"太古汇","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-19 10:24:45","praise_num":"0","layer":"1","fileList":[]},{"id":"1d7b6cc0-891e-4cb6-b2ae-29d673789d77","eval_id":"FW50000101","reply_id":"88a35472-9199-47c8-99e1-87457cedeed6","eval_leval":"5","eval_comments":"电话","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-19 11:54:45","praise_num":"0","layer":"1","fileList":[]}]
//     */
//
//    private double total;
//    private double rows;
//    private double page;
//    private double all_page;
//    private List<ListBean> list;
//
//    public double getTotal() {
//        return total;
//    }
//
//    public void setTotal(double total) {
//        this.total = total;
//    }
//
//    public double getRows() {
//        return rows;
//    }
//
//    public void setRows(double rows) {
//        this.rows = rows;
//    }
//
//    public double getPage() {
//        return page;
//    }
//
//    public void setPage(double page) {
//        this.page = page;
//    }
//
//    public double getAll_page() {
//        return all_page;
//    }
//
//    public void setAll_page(double all_page) {
//        this.all_page = all_page;
//    }
//
//    public List<ListBean> getList() {
//        return list;
//    }
//
//    public void setList(List<ListBean> list) {
//        this.list = list;
//    }
//
//    public static class ListBean {
//        /**
//         * id : a76fea6d-302d-4605-8080-a9b1ed7393fa
//         * eval_id : FW50000101
//         * reply_id : 88a35472-9199-47c8-99e1-87457cedeed6
//         * eval_leval : 5
//         * eval_comments : 一九九
//         * eval_url: http://51gongjiang.oss-cn-shanghai.aliyuncs.com/image/ea2110938a62f2f2acce2bbbebc0bf31.png
//         * user_id : 171027017659
//         * pic_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101143421..jpg
//         * creator_id : 171027017659
//         * create_time : 2018-01-18 14:59:37
//         * praise_num : 0
//         * layer : 1
//         * fileList : []
//         */
//
//        private String id;
//        private String eval_id;
//        private String reply_id;
//        private String eval_leval;
//        private String eval_comments;
//        private String eval_url;
//        private String user_id;
//        private String pic_url;
//        private String creator_id;
//        private String create_time;
//        private String praise_num;
//        private String layer;
//        private List<String > fileList;
//
//        public void setEval_url(String eval_url){
//            this.eval_url=eval_url;
//        }
//
//        public String getEval_url(String eval_url){
//            return eval_url;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getEval_id() {
//            return eval_id;
//        }
//
//        public void setEval_id(String eval_id) {
//            this.eval_id = eval_id;
//        }
//
//        public String getReply_id() {
//            return reply_id;
//        }
//
//        public void setReply_id(String reply_id) {
//            this.reply_id = reply_id;
//        }
//
//        public String getEval_leval() {
//            return eval_leval;
//        }
//
//        public void setEval_leval(String eval_leval) {
//            this.eval_leval = eval_leval;
//        }
//
//        public String getEval_comments() {
//            return eval_comments;
//        }
//
//        public void setEval_comments(String eval_comments) {
//            this.eval_comments = eval_comments;
//        }
//
//        public String getUser_id() {
//            return user_id;
//        }
//
//        public void setUser_id(String user_id) {
//            this.user_id = user_id;
//        }
//
//        public String getPic_url() {
//            return pic_url;
//        }
//
//        public void setPic_url(String pic_url) {
//            this.pic_url = pic_url;
//        }
//
//        public String getCreator_id() {
//            return creator_id;
//        }
//
//        public void setCreator_id(String creator_id) {
//            this.creator_id = creator_id;
//        }
//
//        public String getCreate_time() {
//            return create_time;
//        }
//
//        public void setCreate_time(String create_time) {
//            this.create_time = create_time;
//        }
//
//        public String getPraise_num() {
//            return praise_num;
//        }
//
//        public void setPraise_num(String praise_num) {
//            this.praise_num = praise_num;
//        }
//
//        public String getLayer() {
//            return layer;
//        }
//
//        public void setLayer(String layer) {
//            this.layer = layer;
//        }
//
//        public List<String > getFileList() {
//            return fileList;
//        }
//
//        public void setFileList(List<String > fileList) {
//            this.fileList = fileList;
//        }

//        List<String> pics;
//
//        public List<String> getPics() {
//            return pics;
//        }
//
//        public void setPics(List<String> pics) {
//            this.pics = pics;
//        }
//    }


    /**
     * id : f56d7149-04a3-4231-a71b-6ff9a8d16277
     * eval_id : FW50000101
     * reply_id : 88a35472-9199-47c8-99e1-87457cedeed6
     * eval_leval : 5
     * eval_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/image/ea2110938a62f2f2acce2bbbebc0bf31.png
     * eval_comments : 回到家
     * user_id : 180119058756
     * pic_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png
     * creator_id : 180119058756
     * create_time : 2018-01-20 09:35:33
     * praise_num : 0
     * layer : 1
     * fileList : [{"id":"34da914c-fd82-11e7-8bce-7cd30abdd1e6","file_id":"f56d7149-04a3-4231-a71b-6ff9a8d16277","file_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/image/ea2110938a62f2f2acce2bbbebc0bf31.png","create_time":"2018-01-20 09:35:33"}]
     */

//    private String id;
//    private String eval_id;
//    private String reply_id;
//    private String eval_leval;
//    private String eval_url;
//    private String eval_comments;
//    private String user_id;
//    private String pic_url;
//    private String creator_id;
//    private String create_time;
//    private String praise_num;
//    private String layer;
//    private List<FileListBean> fileList;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getEval_id() {
//        return eval_id;
//    }
//
//    public void setEval_id(String eval_id) {
//        this.eval_id = eval_id;
//    }
//
//    public String getReply_id() {
//        return reply_id;
//    }
//
//    public void setReply_id(String reply_id) {
//        this.reply_id = reply_id;
//    }
//
//    public String getEval_leval() {
//        return eval_leval;
//    }
//
//    public void setEval_leval(String eval_leval) {
//        this.eval_leval = eval_leval;
//    }
//
//    public String getEval_url() {
//        return eval_url;
//    }
//
//    public void setEval_url(String eval_url) {
//        this.eval_url = eval_url;
//    }
//
//    public String getEval_comments() {
//        return eval_comments;
//    }
//
//    public void setEval_comments(String eval_comments) {
//        this.eval_comments = eval_comments;
//    }
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getPic_url() {
//        return pic_url;
//    }
//
//    public void setPic_url(String pic_url) {
//        this.pic_url = pic_url;
//    }
//
//    public String getCreator_id() {
//        return creator_id;
//    }
//
//    public void setCreator_id(String creator_id) {
//        this.creator_id = creator_id;
//    }
//
//    public String getCreate_time() {
//        return create_time;
//    }
//
//    public void setCreate_time(String create_time) {
//        this.create_time = create_time;
//    }
//
//    public String getPraise_num() {
//        return praise_num;
//    }
//
//    public void setPraise_num(String praise_num) {
//        this.praise_num = praise_num;
//    }
//
//    public String getLayer() {
//        return layer;
//    }
//
//    public void setLayer(String layer) {
//        this.layer = layer;
//    }


}
