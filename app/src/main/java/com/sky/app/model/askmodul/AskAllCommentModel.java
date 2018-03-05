package com.sky.app.model.askmodul;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AreaOut;
import com.sky.app.bean.AskFilter;
import com.sky.app.bean.CommentList;
import com.sky.app.bean.CommentRequest;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.AllCommentPresenter;
import com.sky.app.presenter.askpresenter.AskAllCommentPresenter;
import com.sky.app.ui.activity.ask.AskSecondComent;
import com.sky.app.ui.activity.ask.MyAnser;
import com.sky.app.ui.activity.ask.MyAsk;


/**
 * 全部评价
 * Created by sky on 2017/2/18.
 */

public class AskAllCommentModel extends BaseModel<AskAllCommentPresenter> implements ShopContract.IAskAllCommentModel {

    public AskAllCommentModel(Context context, AskAllCommentPresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void AskrequestComment(String i, String eval_id, String id, AskFilter askFilter, final int flag) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).AskrequestComment(i, eval_id, id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        Log.e("lbh99", success);
                        switch (flag) {
                            case Constants.ListStatus.LOADMORE:
                                getPresenter().loadMoreData1(new Gson().fromJson(success, AskSecondComent.class));
                                break;
                            case Constants.ListStatus.REFRESH:
                                AskSecondComent askSecondComent = new Gson().fromJson(success, AskSecondComent.class);
                                getPresenter().refreshData1(askSecondComent);
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

    //请求问一问中的我的提问
    @Override
    public void requestMyAsk(String cacheUid, int i) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestMyAsk(cacheUid, i),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        Log.e("lbh456", success);
                        MyAsk ask = new Gson().fromJson(success, MyAsk.class);
                        getPresenter().showMyAsk(ask);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    //请求问一问中的我的回答
    @Override
    public void requestMyAnser(String cacheUid, int i) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestMyAnser(cacheUid, i),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        MyAnser anser = new Gson().fromJson(success, MyAnser.class);
                        getPresenter().showMyAnser(anser);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    /**
     * 点赞的请求
     *
     * @param id
     * @param cacheUid
     */
    @Override
    public void requestAskPrise(String id, String cacheUid) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestAskCancelPrise(id, cacheUid),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {

//                        getPresenter().responsePrise("点赞成功");
                    }

                    @Override
                    public void onFailure(String error) {

                        getPresenter().showError(error);
                    }
                }
        ));
    }

    /**
     * 取消点赞的请求
     *
     * @param id
     * @param cacheUid
     */
    @Override
    public void requestAskCancelPrise(String id, String cacheUid) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestAskCancelPrise(id, cacheUid),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responsePrise("取消点赞");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }


    @Override
    public void requestAllCommentNum(CommentRequest commentRequest, final int flag) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestComment(commentRequest),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {

                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
