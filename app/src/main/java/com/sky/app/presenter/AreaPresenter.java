package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AreaIn;
import com.sky.app.bean.AreaOut;
import com.sky.app.contract.MineContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.AreaModel;

/**
 * Created by sky on 2017/2/10.
 * 选择区域
 */
public class AreaPresenter extends BasePresenter<MineContract.IAreaView>
        implements MineContract.IAreaPresenter {

    private MineContract.IAreaModel iAreaModel;

    /**
     * 构造
     */
    public AreaPresenter(Context context, MineContract.IAreaView iAreaView) {
        super(context, iAreaView);
        iAreaModel = new AreaModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iAreaModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void get(AreaIn areaIn) {
        iAreaModel.get(areaIn);
    }

    @Override
    public void showSuccess(AreaOut areaOut) {
        getView().showSuccess(areaOut.getList());
    }
}