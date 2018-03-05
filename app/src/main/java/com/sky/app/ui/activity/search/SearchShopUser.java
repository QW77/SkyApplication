package com.sky.app.ui.activity.search;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class SearchShopUser {
    private String decorative_id;
    private int tp;
    private String two_dir_id;
    private String one_dir_id;
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDecorative_id() {
        return decorative_id;
    }

    public void setDecorative_id(String decorative_id) {
        this.decorative_id = decorative_id;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public String getTwo_dir_id() {
        return two_dir_id;
    }

    public void setTwo_dir_id(String two_dir_id) {
        this.two_dir_id = two_dir_id;
    }


    public String getOne_dir_id() {
        return one_dir_id;
    }

    public void setOne_dir_id(String one_dir_id) {
        this.one_dir_id = one_dir_id;
    }

    @Override
    public String toString() {
        return "SearchShopUser{" +
                "decorative_id='" + decorative_id + '\'' +
                ", tp='" + tp + '\'' +
                ", two_dir_id='" + two_dir_id + '\'' +
                '}';
    }
}
