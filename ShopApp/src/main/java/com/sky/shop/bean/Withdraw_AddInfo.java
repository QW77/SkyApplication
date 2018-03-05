package com.sky.shop.bean;

/**
 * 增加银行账户入参
 * Created by 张凌峰 on 2017/6/8.
 */

public class Withdraw_AddInfo {
    private String withdrawal_money;//提现金额，前台加一个限制可提现金额
    private String user_id;//用户ID
    private String bank_id;//对应提现账户的ID
    private String poundage_money;//手续费单位元
    private String actual_money;//实际到账金额

    public String getWithdrawal_money() {
        return withdrawal_money;
    }

    public void setWithdrawal_money(String withdrawal_money) {
        this.withdrawal_money = withdrawal_money;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getPoundage_money() {
        return poundage_money;
    }

    public void setPoundage_money(String poundage_money) {
        this.poundage_money = poundage_money;
    }

    public String getActual_money() {
        return actual_money;
    }

    public void setActual_money(String actual_money) {
        this.actual_money = actual_money;
    }

}
