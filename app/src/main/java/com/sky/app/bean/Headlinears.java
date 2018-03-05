package com.sky.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24 0024.
 */

public class Headlinears {
    /**
     * id : b0211c7a-d10c-11e7-944d-7cd30ab8a81c
     * createTime : 2017-11-24 12:51:24.0
     * noticeCode : NO0001
     * noticeContent : 我是毛大大
     * isEffective : 1
     */

    private String id;
    private String createTime;
    private String noticeCode;
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


    private List<HeadlinearsDetail> list;

    public List<HeadlinearsDetail> getList() {
        return list;
    }

    public void setList(List<HeadlinearsDetail> list) {
        this.list = list;
    }



}
