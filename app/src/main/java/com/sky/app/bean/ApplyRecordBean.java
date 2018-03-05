package com.sky.app.bean;

/**
 * 提现记录
 * Created by sky on 2017/3/21.
 */

public class ApplyRecordBean extends Page {
    private String withdrawal_id;
    private double withdrawal_money;
    private String user_id;
    private String create_time;
    private int state;
    private String bank_id;
    private double poundage_money;
    private double actual_money;
    private String bank_name;
    private String bank_user_name;

    public String getWithdrawal_id() {
        return withdrawal_id;
    }

    public void setWithdrawal_id(String withdrawal_id) {
        this.withdrawal_id = withdrawal_id;
    }

    public double getWithdrawal_money() {
        return withdrawal_money;
    }

    public void setWithdrawal_money(double withdrawal_money) {
        this.withdrawal_money = withdrawal_money;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public double getPoundage_money() {
        return poundage_money;
    }

    public void setPoundage_money(double poundage_money) {
        this.poundage_money = poundage_money;
    }

    public double getActual_money() {
        return actual_money;
    }

    public void setActual_money(double actual_money) {
        this.actual_money = actual_money;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_user_name() {
        return bank_user_name;
    }

    public void setBank_user_name(String bank_user_name) {
        this.bank_user_name = bank_user_name;
    }
}