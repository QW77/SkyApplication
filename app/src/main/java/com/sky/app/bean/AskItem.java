package com.sky.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

public class AskItem {
    /**
     * total : 2
     * rows : 20
     * page : 1
     * all_page : 1
     * list : [{"id":"e39e079b-84d1-46bd-b7c8-f8134557a603","eval_id":"180104114036053370","eval_leval":"5","eval_comments":"很可能已经","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-24 21:39:00","flag":"1","praise_num":"0","layer":"0","fileList":[]},{"id":"67f367f1-6959-4f26-9753-d0c475f63c0a","eval_id":"180104114036053370","eval_leval":"5","eval_comments":"四六级科目","user_id":"180119058756","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png","creator_id":"180119058756","create_time":"2018-01-24 21:39:07","flag":"1","praise_num":"0","layer":"0","fileList":[]}]
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
         * id : e39e079b-84d1-46bd-b7c8-f8134557a603
         * eval_id : 180104114036053370
         * eval_leval : 5
         * eval_comments : 很可能已经
         * user_id : 180119058756
         * pic_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170322145812..png
         * creator_id : 180119058756
         * create_time : 2018-01-24 21:39:00
         * flag : 1
         * praise_num : 0
         * layer : 0
         * fileList : []
         */

        private String id;
        private String eval_id;
        private String eval_leval;
        private String eval_comments;
        private String user_id;
        private String pic_url;
        private String creator_id;
        private String create_time;
        private String flag;
        private String praise_num;
        private String replyNum;
        private String layer;
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

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getPraise_num() {
            return praise_num;
        }

        public String getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(String replyNum) {
            this.replyNum = replyNum;
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

        public List<FileListBean> getFileList() {
            return fileList;
        }

        public void setFileList(List<FileListBean> fileList) {
            this.fileList = fileList;
        }
    }

    public static class FileListBean {
        /**
         * id : 5e69d0c3-fbfe-11e7-8bce-7cd30abdd1e6
         * file_id : 88a35472-9199-47c8-99e1-87457cedeed6
         * file_name : null
         * file_type : null
         * file_url : 123456
         * module_id : null
         * create_time : 2018-01-18 11:19:18
         * base_path : null
         * flag : null
         */

        private String id;
        private String file_id;
        private Object file_name;
        private Object file_type;
        private String file_url;
        private Object module_id;
        private String create_time;
        private Object base_path;
        private Object flag;

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

        public Object getFile_name() {
            return file_name;
        }

        public void setFile_name(Object file_name) {
            this.file_name = file_name;
        }

        public Object getFile_type() {
            return file_type;
        }

        public void setFile_type(Object file_type) {
            this.file_type = file_type;
        }

        public String getFile_url() {
            return file_url;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public Object getModule_id() {
            return module_id;
        }

        public void setModule_id(Object module_id) {
            this.module_id = module_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public Object getBase_path() {
            return base_path;
        }

        public void setBase_path(Object base_path) {
            this.base_path = base_path;
        }

        public Object getFlag() {
            return flag;
        }

        public void setFlag(Object flag) {
            this.flag = flag;
        }
    }
}

