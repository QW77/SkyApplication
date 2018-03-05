package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.CaseIn;
import com.sky.app.bean.CaseOut;
import com.sky.app.bean.CommentList;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.CommentResponse;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.SellerContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.SellerCenterModel;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 商户个人主页处理业务逻辑
 */
public class SellerCenterPresenter extends BasePresenter<SellerContract.ISellerCenterView>
        implements SellerContract.ISellerCenterPresenter {
    private SellerContract.ISellerCenterModel iSellerCenterModel;

    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public SellerCenterPresenter(Context context, SellerContract.ISellerCenterView iSellerCenterView) {
        super(context, iSellerCenterView);
        iSellerCenterModel = new SellerCenterModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iSellerCenterModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void requestDescData(ProductIntroduceIn productIntroduceIn) {
        getView().showProgress();
        productIntroduceIn.setLogin_user_name(UserBean.getInstance().getUserCache().getNick_name());
        productIntroduceIn.setLogin_user_id(UserBean.getInstance().getCacheUid());
        iSellerCenterModel.requestDescData(productIntroduceIn);
    }

    @Override
    public void responseDescData(ProductIntroduceOut productIntroduceOut) {
        getView().showSuccess(productIntroduceOut);
        getView().hideProgress();
    }

    @Override
    public void requestComment(CommentRequest commentRequest) {
        commentRequest.setType(1);
        commentRequest.setPage(1);
        commentRequest.setRows(1);
        commentRequest.setPic_type("0");
        iSellerCenterModel.requestComment(commentRequest);
    }

    @Override
    public void responseComment(CommentList commentList) {
        List<CommentResponse> commentResponses = commentList.getList();
        getView().responseComment(null == commentResponses || commentResponses.isEmpty() ? null : commentResponses.get(0));
    }

    @Override
    public void loadMore(CaseIn caseIn) {
        caseIn.setPage(page + 1);
        caseIn.setRows(rows);
        iSellerCenterModel.getCaseList(caseIn, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(CaseIn caseIn) {
        caseIn.setPage(1);
        caseIn.setRows(rows);
        iSellerCenterModel.getCaseList(caseIn, Constants.ListStatus.REFRESH);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(CaseOut caseOut) {
        this.page = caseOut.getPage();
        this.total = caseOut.getTotal();
        getView().getRefreshData(caseOut.getList());
    }

    @Override
    public void loadMoreData(CaseOut caseOut) {
        this.page = caseOut.getPage();
        this.total = caseOut.getTotal();
        getView().getLoadMoreData(caseOut.getList());
    }
}