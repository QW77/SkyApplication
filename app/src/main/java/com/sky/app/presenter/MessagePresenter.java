package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AskFilter;
import com.sky.app.bean.AskItem;
import com.sky.app.bean.AskOne;
import com.sky.app.bean.Message;
import com.sky.app.bean.MessageAskTable;
import com.sky.app.bean.MessageIn;
import com.sky.app.bean.MessageList;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.MessageModel;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 消息
 */

public class MessagePresenter extends BasePresenter<UserContract.IMessageView>
        implements UserContract.IMessagePresenter {

    private UserContract.IMessageModel iMessageModel;

    private int page = 1;
    private int total = 1;
    private int rows = 20;
    private String cacheUid;

    /**
     * 构造
     */
    public MessagePresenter(Context context,
                            UserContract.IMessageView mIsearch) {
        super(context, mIsearch);
        this.iMessageModel = new MessageModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iMessageModel) {
            iMessageModel.destroy();
        }
        super.destroy();
    }


    //请求问一问的Tablelayout
    @Override
    public void requestAskTable() {
        iMessageModel.requestAskTable();
    }

    @Override
    public void showAskTable(MessageAskTable messageAskTable) {
        getView().showAskTable(messageAskTable);

    }


    @Override
    public void requestAskList(int i, String eval_id) {
        iMessageModel.requestAskList(i, eval_id);

    }
    @Override
    public void loadMore(int i, String eval_id) {
        AskOne askOne = new AskOne();
        askOne.setPage(page + 1);
        askOne.setRows(rows);
        iMessageModel.requestAskList(i, eval_id,askOne, Constants.ListStatus.LOADMORE);
    }
    @Override
    public void loadData(int i, String eval_id) {
        AskOne askOne = new AskOne();
        askOne.setPage(1);
        askOne.setRows(rows);
        iMessageModel.requestAskList(i,eval_id,askOne, Constants.ListStatus.REFRESH);
    }

    @Override
    public void refreshData1(AskItem askItem) {
        List<AskItem.ListBean> list = askItem.getList();
        this.page = askItem.getPage();
        this.total = askItem.getTotal();
        getView().getRefreshData1(list);
    }



    @Override
    public void showAskUp() {
        getView().showAskUp();
    }

    @Override
    public void showAskComment() {
        getView().showAskComment();
    }

    @Override
    public void requestCenterAsk( String two_dir_id, int i, String askcoment,AskOne askOne) {
        cacheUid = UserBean.getInstance().getCacheUid();
        iMessageModel.requestCenterAsk(cacheUid, two_dir_id, i, askcoment,askOne);
    }

    @Override
    public void deleteMessage(Message message) {
        iMessageModel.deleteMessage(message);
    }

    @Override
    public void deleteSuccess(String msg) {
        getView().deleteSuccess(msg);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadMore() {
        MessageIn messageIn = new MessageIn();
        messageIn.setPage(page + 1);
        messageIn.setRows(rows);
        iMessageModel.getMessage(messageIn, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData() {
        MessageIn messageIn = new MessageIn();
        messageIn.setPage(1);
        messageIn.setRows(rows);
        iMessageModel.getMessage(messageIn, Constants.ListStatus.REFRESH);
    }

    @Override
    public void showAskList(List<AskItem.ListBean> list) {
        getView().showAskList(list);
    }

    @Override
    public void requestAskUp(String id) {
        String cacheUid = UserBean.getInstance().getCacheUid();
        iMessageModel.requestAskUp(id,cacheUid);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total / rows) > page) {
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(MessageList messageList) {
        this.page = messageList.getPage();
        this.total = messageList.getTotal();
        getView().getRefreshData(messageList.getList());
    }




    @Override
    public void loadMoreData(MessageList messageList) {
        this.page = messageList.getPage();
        this.total = messageList.getTotal();
        getView().getLoadMoreData(messageList.getList());
    }
}