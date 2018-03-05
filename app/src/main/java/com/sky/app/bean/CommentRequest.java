package com.sky.app.bean;

/**
 * Created by hongbang on 2017/5/8.
 * 评论请求
 */

public class CommentRequest extends Page{

    private String value; //type为0时是订单编号，为1时是商户编号，为2时是产品编号
    private String pic_type;

    public String getPic_type() {
        return pic_type;
    }

    public void setPic_type(String pic_type) {
        this.pic_type = pic_type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;//":1 // 0 订单评论 1商户评论 2产品评论
}
