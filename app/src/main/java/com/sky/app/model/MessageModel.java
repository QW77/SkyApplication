package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AskItem;
import com.sky.app.bean.AskOne;
import com.sky.app.bean.Empty;
import com.sky.app.bean.Message;
import com.sky.app.bean.MessageAskTable;
import com.sky.app.bean.MessageIn;
import com.sky.app.bean.MessageList;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.MessagePresenter;
import com.sky.app.ui.activity.ask.AskSecondComent;

import java.util.List;

import okhttp3.RequestBody;


/**
 * 消息
 * Created by sky on 2017/2/18.
 */

public class MessageModel extends BaseModel<MessagePresenter> implements UserContract.IMessageModel {


    public MessageModel(Context context, MessagePresenter presenter) {
        super(context, presenter);
    }

    //请求问一问的Tablelayout
    @Override
    public void requestAskTable() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestAskTable(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        MessageAskTable messageAskTable = new Gson().fromJson(success, MessageAskTable.class);
                        getPresenter().showAskTable(messageAskTable);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestAskList(int i, String eval_id) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestAskList(i, eval_id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        AskItem askItem = new Gson().fromJson(success, AskItem.class);
                        getPresenter().showAskList(askItem.getList());
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestAskList(int i, String eval_id, AskOne askOne, final int flag) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestAskList(i, eval_id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag) {
                            case Constants.ListStatus.LOADMORE:
                                AskItem askItem = new Gson().fromJson(success, AskItem.class);
                                getPresenter().showAskList(askItem.getList());
                                break;
                            case Constants.ListStatus.REFRESH:
                                Log.e("lbh666", success);
                                AskItem askItem1 = new Gson().fromJson(success, AskItem.class);
                                getPresenter().refreshData1(askItem1);
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

    //请求点赞数
    @Override
    public void requestAskUp(String id, String cacheUid) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestAskCancelPrise(id,cacheUid),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        Log.e("jfdhdf", success);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestCenterAsk(String cacheUid, String two_dir_id, int i, String askcoment, AskOne askOne) {
        List<String> pics = askOne.getPics();
//        String urls = "";
//        if (pics.size() > 0) {
//            for (int x = 0; x < pics.size() - 1; x++) {
//                String url = pics.get(x);
//                urls = urls + "," + url;
//            }
//        }
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCenterAsk(cacheUid, two_dir_id, i, askcoment,askOne),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        AskItem askItem = new Gson().fromJson(success, AskItem.class);
                        getPresenter().showAskList(askItem.getList());
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
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
                        switch (flag) {
                            case Constants.ListStatus.LOADMORE:
                                MessageList messageList = new Gson().fromJson(success, MessageList.class);
                                getPresenter().loadMoreData(messageList);
                                break;
                            case Constants.ListStatus.REFRESH:
                                MessageList messageList1 = new Gson().fromJson(success, MessageList.class);
                                getPresenter().refreshData(messageList1);
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
