package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AddCommentInfo;
import com.sky.app.bean.AskOne;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.CommentModel;
import com.sky.app.ui.activity.ask.AskAddCommentInfo;

/**
 * Created by zhanglf on 2017/6/10.
 * 评论页面
 */
public class CommentPresenter extends BasePresenter<OrderContract.CommentView>
        implements OrderContract.CommentPresenter {

    private OrderContract.CommentModel mModel;
    private String cacheUid;

    /**
     * 构造
     */
    public CommentPresenter(Context context, OrderContract.CommentView iCommentView) {
        super(context, iCommentView);
        mModel = new CommentModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        mModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void getData(AddCommentInfo info) {
        info.setUser_id(UserBean.getInstance().getCacheUid());
        mModel.getData(info);
    }

    @Override
    public void getAskData(String user_id, String id, String layer, String eval_id, int i, String eval_comments, AskAddCommentInfo addCommentInfo) {
        mModel.getAskData(user_id, id, layer, eval_id, i, eval_comments, addCommentInfo);
    }

    //评论
    @Override
    public void requestCenterAsk(String two_dir_id, int i, String askcoment, AskOne addCommentInfo) {
        cacheUid = UserBean.getInstance().getCacheUid();
        mModel.requestCenterAsk(cacheUid, two_dir_id, i, askcoment,addCommentInfo);
    }


    @Override
    public void getResult() {
        getView().Succec();
    }

    @Override
    public void getAskResult() {
        getView().getAskResult();
    }
}