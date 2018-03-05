package com.sky.app.library.base.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基础Fragment
 *
 * @param <T>
 */
public abstract class BaseViewFragment<T extends IBasePresenter> extends RxFragment implements IBaseView {

    protected Context context = null;
    private T presenter;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (0 != getContentViewLayoutID()) {
            return inflater.inflate(getContentViewLayoutID(), null);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setPresenter(presenter());

        init();

        initViewsAndEvents();
    }

    @Override
    public void onDestroyView() {
        if (null != presenter) {
            getPresenter().destroy();
        }
        if (null != unbinder) {
            unbinder.unbind();
        }
        onDestoryFragment();
        super.onDestroyView();
    }

    public void setPresenter(T t) {
        this.presenter = t;
    }

    public T getPresenter() {
        return presenter;
    }

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 初始化事件
     */
    protected abstract void initViewsAndEvents();

    /**
     * 初始化界面
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 销毁fragment
     */
    protected abstract void onDestoryFragment();

    /**
     * 获取present
     *
     * @return
     */
    protected abstract T presenter();

    @Override
    public void showError(String error) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
