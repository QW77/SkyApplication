package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.MyCollectIn;
import com.sky.app.bean.MyCollectOut;
import com.sky.app.bean.ProductIn;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.MyCollectModel;

/**
 * Created by sky on 2017/2/10.
 * 我的收藏处理业务逻辑
 */
public class MyCollectPresenter extends BasePresenter<UserContract.IMyCollectView>
        implements UserContract.IMyCollectPresenter {

    private UserContract.IMyCollectModel iMyCollectModel;

    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public MyCollectPresenter(Context context, UserContract.IMyCollectView iMyCollectView) {
        super(context, iMyCollectView);
        iMyCollectModel = new MyCollectModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iMyCollectModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadMore(MyCollectIn myCollectIn) {
        myCollectIn.setPage(page + 1);
        myCollectIn.setRows(rows);
        iMyCollectModel.queryMyCollect(myCollectIn, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(MyCollectIn myCollectIn) {
        myCollectIn.setPage(1);
        myCollectIn.setRows(rows);
        iMyCollectModel.queryMyCollect(myCollectIn, Constants.ListStatus.REFRESH);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(MyCollectOut myCollectOut) {
        getView().getRefreshData(myCollectOut.getList());
    }

    @Override
    public void loadMoreData(MyCollectOut myCollectOut) {
        getView().getLoadMoreData(myCollectOut.getList());
    }

    @Override
    public void del(ProductIn productIn) {
        iMyCollectModel.del(productIn);
    }

    @Override
    public void showDelSuccess(String msg) {
        getView().showDelSuccess(msg);
    }
}