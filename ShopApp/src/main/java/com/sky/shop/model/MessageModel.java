package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.Message;
import com.sky.shop.bean.MessageIn;
import com.sky.shop.bean.MessageList;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.UserContract;
import com.sky.shop.presenter.fragment.MessagePresenter;


/**
 *消息
 * Created by sky on 2017/2/18.
 */

public class MessageModel extends BaseModel<MessagePresenter> implements UserContract.IMessageModel{


    public MessageModel(Context context, MessagePresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void deleteMessage(Message message) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .deleteMessage(UserBean.getInstance().getCacheUid(), message),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().deleteSuccess("删除成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void getMessage(MessageIn messageIn, final int flag) {
        messageIn.setTo_user_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .getMessage(UserBean.getInstance().getCacheUid(), messageIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag){
                            case Constants.ListStatus.LOADMORE:
                                getPresenter().loadMoreData(new Gson().fromJson(success, MessageList.class));
                                break;
                            case Constants.ListStatus.REFRESH:
                                getPresenter().refreshData(new Gson().fromJson(success, MessageList.class));
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
