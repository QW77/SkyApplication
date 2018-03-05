package com.sky.shop.bean;

/**
 * 申请提现账户
 * Created by sky on 2017/3/21.
 */

public class ApplyAccountIn extends BaseUser{
    private int page;//当前分页
    private int rows;//分页条数
    private double withdrawal_money;
    private String bank_id;
    private double poundage_money;
    private double actual_money;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getWithdrawal_money() {
        return withdrawal_money;
    }

    public void setWithdrawal_money(double withdrawal_money) {
        this.withdrawal_money = withdrawal_money;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
