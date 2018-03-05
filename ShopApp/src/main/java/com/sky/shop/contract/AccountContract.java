package com.sky.shop.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.shop.bean.AccountBankList;
import com.sky.shop.bean.AccountBean;
import com.sky.shop.bean.AccountMoney;
import com.sky.shop.bean.Add_Account_ApplyBean;
import com.sky.shop.bean.ApplyAccountIn;
import com.sky.shop.bean.ApplyRecordBean;
import com.sky.shop.bean.ApplyRecordIn;
import com.sky.shop.bean.ApplyRecordOut;
import com.sky.shop.bean.BankIn;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 账户
 */

public class AccountContract {

    /**
     * 订单列表更新UI方法
     */
    public interface IAccountView extends IBaseView {
        void responseAccountMoney(AccountMoney accountMoney);
    }

    /**
     * 订单列表处理业务逻辑
     */
    public interface IAccountPresenter extends IBasePresenter {
        void requestAccountMoney();
        void responseAccountMoney(AccountMoney accountMoney);
    }

    /**
     * 订单列表网络请求
     */
    public interface IAccountModel extends IBaseModel{
        void requestAccountMoney();
    }

    /**
     * 申请账户更新UI方法
     */
    public interface IApplyAccountView extends IBaseView {
        void responseApplyAccount(String msg);
        void responseWithDraw(double rate);
    }

    /**
     * 申请账户处理业务逻辑
     */
    public interface IApplyAccountPresenter extends IBasePresenter {
        void applyAccount(ApplyAccountIn applyAccountIn);
        void responseApplyAccount(String msg);
        void requestWithDraw();
        void responseWithDraw(double rate);
    }

    /**
     * 申请账户网络请求
     */
    public interface IApplyAccountModel extends IBaseModel{
        void applyAccount(ApplyAccountIn applyAccountIn);
        void requestWithDraw();
    }

    /**
     * 账户查询更新UI方法
     */
    public interface IAccountQueryView extends IBaseView {
        void getRefreshData(List<ApplyRecordBean> t);
        void getLoadMoreData(List<ApplyRecordBean> t);
    }

    /**
     * 账户查询处理业务逻辑
     */
    public interface IAccountQueryPresenter extends IBasePresenter {
        void loadMore(ApplyRecordIn applyRecordIn);
        void loadData(ApplyRecordIn applyRecordIn);
        boolean hasMore();
        void refreshData(ApplyRecordOut applyRecordOut);
        void loadMoreData(ApplyRecordOut applyRecordOut);
    }

    /**
     * 账户查询网络请求
     */
    public interface IAccountQueryModel extends IBaseModel{
        void queryRecord(ApplyRecordIn applyRecordIn, int flag);
    }

    /**
     * 提现账户更新UI方法
     */
    public interface IAccountDetailView extends IBaseView {
        void getRefreshData(List<AccountBean> t);
        void showDelSuccess(String msg);
    }

    /**
     * 提现账户处理业务逻辑
     */
    public interface IAccountDetailPresenter extends IBasePresenter {
        void loadData();
        void refreshData(AccountBankList accountBankList);
        void del(BankIn bankIn);
        void showDelSuccess(String msg);
    }

    /**
     * 提现账户网络请求
     */
    public interface IAccountDetailModel extends IBaseModel{
        void queryMyAccountList();
        void del(BankIn bankIn);
    }

    /**
     * 添加提现账户更新UI方法
     */
    public interface Add_Account_ApplyView extends IBaseView {
        void onSuccess();
    }

    /**
     * 增加提现账户
     */
    public interface Add_Account_ApplyPresenter extends IBasePresenter {
        void forget(String userid, Add_Account_ApplyBean info);
        void showSuccess();
    }

    /**
     *添加提现账户网络请求
     */
    public interface Add_Account_ApplyModel extends IBaseModel{
        void getAdd_Account_ApplyModel(String userid, Add_Account_ApplyBean info);
    }
}