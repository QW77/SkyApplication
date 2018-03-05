package com.sky.app.contract;

import com.sky.app.bean.AccountBankList;
import com.sky.app.bean.AccountBean;
import com.sky.app.bean.AccountMoney;
import com.sky.app.bean.ApplyAccountIn;
import com.sky.app.bean.ApplyRecordBean;
import com.sky.app.bean.ApplyRecordIn;
import com.sky.app.bean.ApplyRecordOut;
import com.sky.app.bean.BankIn;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;

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

    }

    /**
     * 申请账户处理业务逻辑
     */
    public interface IApplyAccountPresenter extends IBasePresenter {
        void applyAccount(ApplyAccountIn applyAccountIn);
    }

    /**
     * 申请账户网络请求
     */
    public interface IApplyAccountModel extends IBaseModel{
        void applyAccount(ApplyAccountIn applyAccountIn);
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
    }

    /**
     * 提现账户网络请求
     */
    public interface IAccountDetailModel extends IBaseModel{
        void queryMyAccountList();
        void del(BankIn bankIn);
    }
}