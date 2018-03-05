package com.sky.shop.bean;

/**
 * 增加银行账户入参
 * Created by 张凌峰 on 2017/6/8.
 */

public class Add_Account_ApplyBean {
    private String bank_type; //银行账户类型:1银行卡；2支付宝；2微信
    private String user_id; //用户ID
    private String bank_name; //银行名称
    private String bank_name_sub; //招商银行南京支行",//支行
    private String bank_user_name; //韦小宝", //银行主体名称
    private String bank_no; //卡号
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_name_sub() {
        return bank_name_sub;
    }

    public void setBank_name_sub(String bank_name_sub) {
        this.bank_name_sub = bank_name_sub;
    }

    public String getBank_user_name() {
        return bank_user_name;
    }

    public void setBank_user_name(String bank_user_name) {
        this.bank_user_name = bank_user_name;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }
}
