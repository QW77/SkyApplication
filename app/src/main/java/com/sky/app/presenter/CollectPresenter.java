package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.CollectionModel;

/**
 * Created by sky on 2017/2/10.
 * 收藏处理业务逻辑
 */
public class CollectPresenter extends BasePresenter<ShopContract.ICollectionView>
        implements ShopContract.ICollectionPresenter {
    private ShopContract.ICollectionModel iCollectionModel;
    private String cacheUid;

    /**
     * 构造
     */
    public CollectPresenter(Context context, ShopContract.ICollectionView iCollectionView) {
        super(context, iCollectionView);
        iCollectionModel = new CollectionModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iCollectionModel.destroy();
    }

    @Override
    public void showCollectError(String error) {
        getView().showCollectError(error);
        getView().hideProgress();
    }

    @Override
    public void responseCollect(String msg) {
        getView().showCollectView(msg);
        getView().hideProgress();
    }

    @Override
    public void requestCollect(CollectPubIn collectPubIn) {
        getView().showProgress();
        collectPubIn.setUser_id(UserBean.getInstance().getCacheUid());
        iCollectionModel.requestCollect(collectPubIn);
    }

    @Override
    public void cancelCollect(CollectPubIn collectPubIn) {
        getView().showProgress();
        collectPubIn.setUser_id(UserBean.getInstance().getCacheUid());
        iCollectionModel.cancelCollect(collectPubIn);
    }


}