package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AddCommentInfo;
import com.sky.app.bean.AskItem;
import com.sky.app.bean.AskOne;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.CommentPresenter;
import com.sky.app.ui.activity.ask.AskAddCommentInfo;

import java.util.List;

/**
 * Created by zhanglf on 2017/6/10.
 * 评论页面
 */

public class CommentModel extends BaseModel<CommentPresenter> implements OrderContract.CommentModel {

    public CommentModel(Context context, CommentPresenter commentPresenter) {
        super(context, commentPresenter);
    }

    @Override
    public void getData(AddCommentInfo info) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .addComment(UserBean.getInstance().getCacheUid(), info),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().getResult();
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    //请求问一问的评论内容
    @Override
    public void getAskData(String user_id, String id, String layer, String eval_id, int i, String eval_comment, AskAddCommentInfo addCommentInfo) {
        List<String> pics = addCommentInfo.getPics();
        String urls = "";
        if (pics.size() > 0) {
            for (int x = 0; x < pics.size(); x++) {
                String url = pics.get(x);
                urls = urls + "," + url;
            }
        }
        if (!urls.equals("")) {
            urls = urls.substring(1, urls.length());
        }
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .askSecondComent(user_id, id, layer, eval_id, i, eval_comment, urls),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        Log.e("lbh55", success);
                        getPresenter().getAskResult();
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));

    }

    /**
     * 第一页的评论
     * @param cacheUid
     * @param two_dir_id
     * @param i
     * @param askcoment
     * @param addCommentInfo
     */
    @Override
    public void requestCenterAsk(String cacheUid, String two_dir_id, int i, String askcoment, AskOne addCommentInfo) {
        List<String> pics = addCommentInfo.getPics();
        String urls = "";
        if (pics.size() > 0) {
            for (int x = 0; x < pics.size(); x++) {
                String url = pics.get(x);
                urls = urls + "," + url;
            }
        }
        if (!urls.equals("")) {
            urls = urls.substring(1, urls.length());
        }
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCenterAsk1(cacheUid, two_dir_id, i, askcoment, urls),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().getAskResult();
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }


}


