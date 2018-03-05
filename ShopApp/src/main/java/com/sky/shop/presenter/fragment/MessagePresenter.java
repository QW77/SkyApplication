package com.sky.shop.presenter.fragment;

import android.content.Context;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.Message;
import com.sky.shop.bean.MessageIn;
import com.sky.shop.bean.MessageList;
import com.sky.shop.contract.UserContract;
import com.sky.shop.model.MessageModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 消息
 */

public class MessagePresenter extends BasePresenter<UserContract.IMessageView>
        implements UserContract.IMessagePresenter {

    private UserContract.IMessageModel iMessageModel;

    private int page = 1;
    private float total = 1;
    private int rows = 20;

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
        if (null != iMessageModel){
            iMessageModel.destroy();
        }
        super.destroy();
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
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
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