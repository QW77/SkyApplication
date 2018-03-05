package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.MyPublishIn;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.SupplyList;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.MyPublishModel;

/**
 * Created by sky on 2017/2/10.
 * 我的发布处理业务逻辑
 */
public class MyPublishPresenter extends BasePresenter<PublishContract.IMyPublishlView>
        implements PublishContract.IMyPublishPresenter {
    private PublishContract.IMyPublishModel iMyPublishModel;

    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public MyPublishPresenter(Context context, PublishContract.IMyPublishlView iMyPublishlView) {
        super(context, iMyPublishlView);
        iMyPublishModel = new MyPublishModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iMyPublishModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadMore() {
        MyPublishIn myPublishIn = new MyPublishIn();
        myPublishIn.setPage(page + 1);
        myPublishIn.setRows(rows);
        iMyPublishModel.requestList(Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData() {
        MyPublishIn myPublishIn = new MyPublishIn();
        myPublishIn.setPage(1);
        myPublishIn.setRows(rows);
        myPublishIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMyPublishModel.requestList(Constants.ListStatus.REFRESH);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(SupplyList supplyList) {
        this.page = supplyList.getPage();
        this.total = supplyList.getTotal();
        getView().getRefreshData(supplyList.getList());
    }

    @Override
    public void loadMoreData(SupplyList supplyList) {
        this.page = supplyList.getPage();
        this.total = supplyList.getTotal();
        getView().getLoadMoreData(supplyList.getList());
    }

    @Override
    public void del(ProductIn productIn) {
        iMyPublishModel.del(productIn);
    }

    @Override
    public void showDelSuccess(String msg) {
        getView().showDelSuccess(msg);
    }
}