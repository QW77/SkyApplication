package com.sky.app.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.sky.app.bean.ProductIn;
import com.sky.app.bean.PublishIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.PublishContentModel;

/**
 * Created by sky on 2017/2/10.
 * 发布内容处理业务逻辑
 */
public class PublishContentPresenter extends BasePresenter<PublishContract.IPublishContentView>
        implements PublishContract.IPublishContentPresenter {
    private PublishContract.IPublishContentModel iPublishContentModel;

    /**
     * 构造
     */
    public PublishContentPresenter(Context context, PublishContract.IPublishContentView iPublishContentView) {
        super(context, iPublishContentView);
        iPublishContentModel = new PublishContentModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iPublishContentModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void publish(PublishIn publishIn) {
        publishIn.setUser_id(UserBean.getInstance().getCacheUid());
        if (TextUtils.isEmpty(publishIn.getProduct_id())){
            iPublishContentModel.publish(publishIn);
        }else{
            iPublishContentModel.edit(publishIn);
        }
    }

    @Override
    public void response(String msg) {
        getView().showSuccess(msg);
    }

    @Override
    public void requestDetail(ProductIn productIn) {
        iPublishContentModel.requestDetail(productIn);
    }

    @Override
    public void responseDetail(SupplyDetail supplyDetail) {
        getView().responseDetail(supplyDetail);
    }
}