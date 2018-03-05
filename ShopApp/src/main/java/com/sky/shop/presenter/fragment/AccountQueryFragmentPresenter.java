package com.sky.shop.presenter.fragment;

import android.content.Context;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.ApplyRecordIn;
import com.sky.shop.bean.ApplyRecordOut;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.model.AccountQueryModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 注册
 */

public class AccountQueryFragmentPresenter extends BasePresenter<AccountContract.IAccountQueryView>
        implements AccountContract.IAccountQueryPresenter {

    private AccountContract.IAccountQueryModel iAccountQueryModel;
    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public AccountQueryFragmentPresenter(Context context,
                                         AccountContract.IAccountQueryView iAccountQueryView) {
        super(context, iAccountQueryView);
        this.iAccountQueryModel = new AccountQueryModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iAccountQueryModel){
            iAccountQueryModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadMore(ApplyRecordIn applyRecordIn) {
        applyRecordIn.setPage(page + 1);
        applyRecordIn.setRows(rows);
        iAccountQueryModel.queryRecord(applyRecordIn, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(ApplyRecordIn applyRecordIn) {
        applyRecordIn.setPage(1);
        applyRecordIn.setRows(rows);
        iAccountQueryModel.queryRecord(applyRecordIn, Constants.ListStatus.REFRESH);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(ApplyRecordOut applyRecordOut) {
        getView().getRefreshData(applyRecordOut.getList());
    }

    @Override
    public void loadMoreData(ApplyRecordOut applyRecordOut) {
        getView().getLoadMoreData(applyRecordOut.getList());
    }
}