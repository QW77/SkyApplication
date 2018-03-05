package com.sky.app.bean;

/**
 * 账户
 * Created by sky on 2017/3/21.
 */

public class AccountBean extends BaseUser {
    private String bank_id;         //银行账号
    private String bank_type;       //账户类型
    private int state;              //账户审核状态
    private String company_id;      //
    private String bank_name;       //银行名称
    private String bank_name_sub;   //支行名称
    private String bank_user_name;  //账户用户名称
    private String bank_no;         //

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
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