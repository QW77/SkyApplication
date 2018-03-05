package com.sky.app.presenter.askpresenter;

import android.content.Context;
import android.util.Log;

import com.sky.app.bean.AskFilter;
import com.sky.app.bean.CommentList;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.SupplyFilter;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.AllCommentModel;
import com.sky.app.model.askmodul.AskAllCommentModel;
import com.sky.app.ui.activity.ask.AskSecondComent;
import com.sky.app.ui.activity.ask.MyAnser;
import com.sky.app.ui.activity.ask.MyAsk;

/**
 * Created by sky on 2017/2/10.
 * 全部评价处理业务逻辑
 */
public class AskAllCommentPresenter extends BasePresenter<ShopContract.IAskAllCommentView>
        implements ShopContract.IAskAllComentsPresenter {

    private ShopContract.IAskAllCommentModel iAskAllCommentModel;
    private int page = 1;
    private int total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public AskAllCommentPresenter(Context context, ShopContract.IAskAllCommentView iAskAllCommentView) {
        super(context, iAskAllCommentView);
        iAskAllCommentModel = new AskAllCommentModel(context, this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }


    @Override
    public void responsePrise(String msg) {
        getView().showCollectView(msg);
        getView().hideProgress();
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total / rows) > page) {
            return true;
        }
        return false;
    }

    @Override
    public void loadMore(String i, String eval_id, String id, AskFilter askFilter) {
        askFilter.setPage(page + 1);
        askFilter.setRows(rows);
        iAskAllCommentModel.AskrequestComment(i, eval_id, id, askFilter, Constants.ListStatus.LOADMORE);
    }


    @Override
    public void loadData(String i, String eval_id, String id, AskFilter askFilter) {
        askFilter.setPage(1);
        askFilter.setRows(rows);
        iAskAllCommentModel.AskrequestComment(i, eval_id, id, askFilter, Constants.ListStatus.REFRESH);
    }

    //刷新
    @Override
    public void refreshData1(AskSecondComent askSecondComent) {
        this.page = (int) askSecondComent.getPage();
        this.total = askSecondComent.getTotal();
        getView().getRefreshData1(askSecondComent.getList(), askSecondComent.getTotal());
    }

    //加载更多
    @Override
    public void loadMoreData1(AskSecondComent askSecondComent) {
        this.page = (int) askSecondComent.getPage();
        this.total = askSecondComent.getTotal();
        getView().getLoadMoreData1(askSecondComent.getList());
    }


    /**
     * 取消点赞
     * @param id
     * @param cacheUid
     */
    @Override
    public void requestAskCancelPrise(String id, String cacheUid) {
        iAskAllCommentModel.requestAskCancelPrise(id, cacheUid);
    }

    @Override
    public void AskrequestComment(String i, String eval_id, String id) {

    }
    @Override
    public void showAskComent(AskSecondComent askSecondComent) {
        getView().showAskComent(askSecondComent.getList());

    }

    //返回我的提问数据
    @Override
    public void showMyAsk(MyAsk ask) {
        getView().showMyAsk(ask.getList());
    }

    @Override
    public void showMyAnser(MyAnser anser) {
        getView().showMyAnser(anser.getList());
    }

    //请求我的提问
    @Override
    public void requestMyAsk(String cacheUid, int i) {
        iAskAllCommentModel.requestMyAsk(cacheUid, i);

    }

    @Override
    public void requestMyAnser(String cacheUid, int i) {
        iAskAllCommentModel.requestMyAnser(cacheUid, i);
    }


}