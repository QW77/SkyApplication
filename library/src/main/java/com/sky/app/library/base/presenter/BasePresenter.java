package com.sky.app.library.base.presenter;

import android.content.Context;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;

/**
 * Created by sky on 2017/2/17.
 */

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter {

    private V view;
    protected Context context = null;

    public BasePresenter(Context context, V view){
        this.context = context;
        this.view = view;
    }

    /**
     * 获取视图
     * @return
     */
    public V getView(){
        return view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void showError(String error) {

    }


}
