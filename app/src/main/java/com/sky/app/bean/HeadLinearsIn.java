package com.sky.app.bean;

/**
 * 供应入参
 */

public class HeadLinearsIn {


    private int isEffective ;//状态 1有效，0无效
    private String noticeTitle;

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    @Override
    public String toString() {
        return "HeadLinearsIn{" +
                "isEffective=" + isEffective +
                ", noticeTitle='" + noticeTitle + '\'' +
                '}';
    }
}
