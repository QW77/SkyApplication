package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.CommentList;
import com.sky.app.bean.CommentRequest;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.AllCommentModel;

/**
 * Created by sky on 2017/2/10.
 * 全部评价处理业务逻辑
 */
public class AllCommentPresenter extends BasePresenter<ShopContract.IAllCommentView>
        implements ShopContract.IAllCommentPresenter {

    private ShopContract.IAllCommentModel iAllCommentModel;

    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public AllCommentPresenter(Context context, ShopContract.IAllCommentView iAllCommentView) {
        super(context, iAllCommentView);
        iAllCommentModel = new AllCommentModel(context, this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadMore(CommentRequest commentRequest, int tag) {
        commentRequest.setPage(page + 1);
        commentRequest.setRows(rows);
        iAllCommentModel.requestAllComment(commentRequest, Constants.ListStatus.LOADMORE, tag);
    }

    @Override
    public void loadData(CommentRequest commentRequest, int tag) {
        commentRequest.setPage(1);
        commentRequest.setRows(rows);
        iAllCommentModel.requestAllComment(commentRequest, Constants.ListStatus.REFRESH, tag);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(CommentList commentList, int tag) {
        this.page = commentList.getPage();
        this.total = commentList.getTotal();
        getView().getRefreshData(commentList.getList(), tag, commentList.getTotal());
    }

    @Override
    public void loadMoreData(CommentList commentList, int tag) {
        this.page = commentList.getPage();
        this.total = commentList.getTotal();
        getView().getLoadMoreData(commentList.getList(), tag, commentList.getTotal());
    }

    @Override
    public void requestAllCommentNum(CommentRequest commentRequest) {
        commentRequest.setPage(1);
        commentRequest.setRows(rows);
        iAllCommentModel.requestAllCommentNum(commentRequest, 1);

        commentRequest.setPic_type("1");
        iAllCommentModel.requestAllCommentNum(commentRequest, 2);
    }

    @Override
    public void responseAllCommentNum(CommentList commentList, int flag) {
        switch (flag){
            case 1:
                getView().responseAllCommentNum(commentList.getTotal());
                break;
            case 2:
                getView().responsePicNum(commentList.getTotal());
                break;
        }
    }
}