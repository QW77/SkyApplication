package com.sky.app.library.base.model;

import android.content.Context;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sky on 2017/2/17.
 */

public abstract class BaseModel<T extends IBasePresenter> implements IBaseModel {

    private T presenter;
    protected Context context;
    private CompositeSubscription mCompositeSubscription;

    public BaseModel(Context context, T presenter){
        this.context = context;
        this.presenter = presenter;
    }

    /**
     * 移除请求
     */
    private void onUnsubscribe() {
        if (null != mCompositeSubscription && !mCompositeSubscription.isUnsubscribed()
                && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 订阅请求
     * @param subscriber
     */
    public void addSubscription(Subscription subscriber) {
        if (null == mCompositeSubscription) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscriber);
    }

    @Override
    public void destroy() {
        onUnsubscribe();
    }

    public T getPresenter() {
        return presenter;
    }
}
