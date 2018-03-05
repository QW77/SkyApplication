package com.sky.app.bean;

/**
 * Created by Administrator on 2017/11/25 0025.
 */

public class HeadlinearsDetail {


    /**
     * id : 5a21713f-d8f8-11e7-8bce-7cd30abdd1e6
     * createTime : 2017-12-04 21:38:03.0
     * noticetitle : 51工匠商户前期入驻中
     * noticeCode : 20171204213803
     * noticeContent : 现在入驻51工匠平台享受以下优惠：1、享受免费半年；2、预缴500元享免两年会员费；
     * isEffective : 1
     */

    private String id;
    private String createTime;
    private String noticetitle;
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

    public String getNoticetitle() {
        return noticetitle;
    }

    public void setNoticetitle(String noticetitle) {
        this.noticetitle = noticetitle;
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

    @Override
    public String toString() {
        return "HeadlinearsDetail{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", noticetitle='" + noticetitle + '\'' +
                ", noticeCode='" + noticeCode + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", isEffective='" + isEffective + '\'' +
                '}';
    }
}
