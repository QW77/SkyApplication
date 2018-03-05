package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.PubCommentIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.PubCommentModel;

/**
 * Created by sky on 2017/2/10.
 * 发布评价处理业务逻辑
 */
public class PubCommentPresenter extends BasePresenter<ShopContract.IPubCommentView>
        implements ShopContract.IPubCommentPresenter {

    private ShopContract.IPubCommentModel iPubCommentModel;

    /**
     * 构造
     */
    public PubCommentPresenter(Context context, ShopContract.IPubCommentView iPubCommentView) {
        super(context, iPubCommentView);
        iPubCommentModel = new PubCommentModel(context, this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void showSuccess(String msg) {
        getView().showSuccess(msg);
    }

    @Override
    public void publish(PubCommentIn pubCommentIn) {
        pubCommentIn.setUser_id(UserBean.getInstance().getCacheUid());
        iPubCommentModel.publish(pubCommentIn);
    }
}