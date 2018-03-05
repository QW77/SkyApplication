package com.sky.app.bean;

/**
 * Created by Administrator on 2018/1/7 0007.
 */

public class AskEveal {
    private String user_id;
    private String eval_id;  //问问分类
    private String eval_leval;//评价等级
    private String eval_comments;  //问问内容
    private String eval_url;    //文件地址  多种图片 用 逗号 分隔
    private String file_type;    // 文件格式   jpg  还是

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getEval_url() {
        return eval_url;
    }

    public void setEval_url(String eval_url) {
        this.eval_url = eval_url;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }
}
