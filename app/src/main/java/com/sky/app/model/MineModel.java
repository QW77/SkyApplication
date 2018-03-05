package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.BaseUser;
import com.sky.app.bean.Empty;
import com.sky.app.bean.Phone;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.MineActivityPresenter;

/**
 * Created by sky on 2017/2/18.
 * 个人中心
 */

public class MineModel extends BaseModel<MineActivityPresenter> implements UserContract.IMineModel{

    public MineModel(Context context, MineActivityPresenter mineActivityPresenter){
        super(context, mineActivityPresenter);
    }

    @Override
    public void getUserData() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                .requestCenter(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
            @Override
            public void onSuccess(String success) {
                Log.e("8888880",success);
                UserBean userBean = new Gson().fromJson(success, UserBean.class);
                UserBean.getInstance().setUserInfo(userBean);
                getPresenter().updateInfo();
            }

            @Override
            public void onFailure(String error) {
                getPresenter().showError(error);
            }
        }));
    }

    @Override
    public void requestMobile() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestMobile(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        Phone phone = new Gson().fromJson(success, Phone.class);
                        getPresenter().responseMobile(phone.getPhone());
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

}
