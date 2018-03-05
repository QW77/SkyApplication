package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.ApplyRecordIn;
import com.sky.shop.bean.ApplyRecordOut;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.presenter.fragment.AccountQueryFragmentPresenter;

/**
 * 账户中心
 * Created by sky on 2017/2/18.
 */

public class AccountQueryModel extends BaseModel<AccountQueryFragmentPresenter> implements AccountContract.IAccountQueryModel{

    public AccountQueryModel(Context context, AccountQueryFragmentPresenter accountQueryFragmentPresenter){
        super(context, accountQueryFragmentPresenter);
    }

    @Override
    public void queryRecord(ApplyRecordIn applyRecordIn, final int flag) {
        applyRecordIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .queryRecord(UserBean.getInstance().getCacheUid(), applyRecordIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag){
                            case Constants.ListStatus.REFRESH:
                                getPresenter().refreshData(new Gson().fromJson(success, ApplyRecordOut.class));
                                break;
                            case Constants.ListStatus.LOADMORE:
                                getPresenter().loadMoreData(new Gson().fromJson(success, ApplyRecordOut.class));
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
