package com.sky.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24 0024.
 */

public class RetDataBean {
    /**
     * id : 1
     * createTime : 2017-11-24 12:51:24.0
     * noticeCode : 123
     * noticeTime : null
     * noticeContent : 内容
     * isEffective : 1
     */

    private String id;
    private String createTime;
    private String noticeCode;
    private Object noticeTime;
    private String noticeContent;
    private String isEffective;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNoticeCode() {
        return noticeCode;
    }

    public void setNoticeCode(String noticeCode) {
        this.noticeCode = noticeCode;
    }

    public Object getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Object noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    @Override
    public String toString() {
        return "RetDataBean{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", noticeCode='" + noticeCode + '\'' +
                ", noticeTime=" + noticeTime +
                ", noticeContent='" + noticeContent + '\'' +
                ", isEffective='" + isEffective + '\'' +
                '}';
    }
}
