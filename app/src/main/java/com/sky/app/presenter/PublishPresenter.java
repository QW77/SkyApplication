package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.SupplyFilter;
import com.sky.app.bean.SupplyList;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.PublishModel;

/**
 * Created by sky on 2017/2/10.
 * 发布处理业务逻辑
 */
public class PublishPresenter extends BasePresenter<PublishContract.IPublishView> implements PublishContract.IPublishPresenter {
    private PublishContract.IPublishModel iPublishModel;

    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public PublishPresenter(Context context, PublishContract.IPublishView iPublishView) {
        super(context, iPublishView);
        iPublishModel = new PublishModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iPublishModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadMore(SupplyFilter supplyFilter) {
        supplyFilter.setPage(page + 1);
        supplyFilter.setRows(rows);
        supplyFilter.setUser_id(UserBean.getInstance().getCacheUid());
        iPublishModel.requestPublishList(supplyFilter, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(SupplyFilter supplyFilter) {
        supplyFilter.setPage(1);
        supplyFilter.setRows(rows);
        supplyFilter.setUser_id(UserBean.getInstance().getCacheUid());
        iPublishModel.requestPublishList(supplyFilter, Constants.ListStatus.REFRESH);
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
}