package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.contract.UserContract;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.CodeModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 验证码
 */

public class CodeActivityPresenter extends BasePresenter<IBaseView> implements UserContract.ICodePresenter {

    private UserContract.ICodeModel iCodeModel;

    /**
     * 构造
     */
    public CodeActivityPresenter(Context context,
                                 IBaseView iBaseView) {
        super(context, iBaseView);
        this.iCodeModel = new CodeModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iCodeModel){
            iCodeModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void sendCode(String mobile) {
        iCodeModel.sendCode(mobile);
    }
}