package com.sky.app.bean;

/**
 * 账户金额
 * Created by sky on 2017/3/21.
 */

public class AccountMoney {
    private String account_id;
    private double balance;
    private double ensure_money;
    private double frozen_money;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getEnsure_money() {
        return ensure_money;
    }

    public void setEnsure_money(double ensure_money) {
        this.ensure_money = ensure_money;
    }

    public double getFrozen_money() {
        return frozen_money;
    }

    public void setFrozen_money(double frozen_money) {
        this.frozen_money = frozen_money;
    }
}
