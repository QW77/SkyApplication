package com.sky.app.ui.activity.ask;

import java.util.List;

/**
 * 问一问中的回复评论
 * Created by sky on 2017/3/29.
 */

public class AskAddCommentInfo {

    private String user_id;//123435",当前发表人
    private String eval_comments;//评价内容",
    private String id;// 回复的问问的id",
    private String eval_id;// 问问分类",
    private String layer;
    private String type;// 文件格式   jpg  还是
//    List<String> pics;//["http://abc.com/1.jpg","http://abc.com/2.jpg"] 文件地址  多种图片 用 逗号 分隔

    List<String> pics;//["http://abc.com/1.jpg","http://abc.com/2.jpg"]

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEval_comments() {
        return eval_comments;
    }

    public void setEval_comments(String eval_comments) {
        this.eval_comments = eval_comments;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getEval_id() {
        return eval_id;
    }

    public void setEval_id(String eval_id) {
        this.eval_id = eval_id;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




    public List<String> getPics() {

        return pics;
    }

    public void setPics(List<String> pics) {

        this.pics = pics;
    }
}
