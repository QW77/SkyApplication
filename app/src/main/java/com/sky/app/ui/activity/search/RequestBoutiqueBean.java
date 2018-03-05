package com.sky.app.ui.activity.search;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class RequestBoutiqueBean {
    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public String getDecorative_id() {
        return decorative_id;
    }

    public void setDecorative_id(String decorative_id) {
        this.decorative_id = decorative_id;
    }

    public String getUser_level_recommend() {
        return user_level_recommend;
    }

    public void setUser_level_recommend(String user_level_recommend) {
        this.user_level_recommend = user_level_recommend;
    }

    /**
     * tp=1
     * decorative_id  装饰城编码
     * user_level_recommend = 2 精选商店
     */
    private int tp;
    private String decorative_id;
    private String user_level_recommend;


}
