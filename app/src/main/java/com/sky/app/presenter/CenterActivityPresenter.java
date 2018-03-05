package com.sky.app.presenter;

import android.content.Context;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.OSSUtils;
import com.sky.app.library.utils.StringUtil;
import com.sky.app.library.utils.ThreadPoolManager;
import com.sky.app.model.CenterModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 个人中心
 */

public class CenterActivityPresenter extends BasePresenter<UserContract.ICenterView>
        implements UserContract.ICenterPresenter {

    private UserContract.ICenterModel iCenterModel;

    /**
     * 构造
     */
    public CenterActivityPresenter(Context context,
                                   UserContract.ICenterView mICenterView) {
        super(context, mICenterView);
        this.iCenterModel = new CenterModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iCenterModel){
            iCenterModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void getUserInfo() {
        getView().showProgress();
        iCenterModel.getUserInfo();
    }

    @Override
    public void setUserInfo() {
        iCenterModel.setUserInfo();
    }

    @Override
    public void showError(String msg) {
        getView().showError(msg);
        getView().hideProgress();
    }

    @Override
    public void uploadFile(final String url) {
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                OSSUtils.getInstance(context).uploadFile(Md5Util.md5(System.currentTimeMillis() + StringUtil.randStr(10)), url,
                        new OSSUtils.OSSCallBack() {
                            @Override
                            public void progress(PutObjectRequest request, long currentSize, long totalSize) {


                        }
                            @Override
                            public void onSuccess(final PutObjectRequest request, final PutObjectResult result) {
                                if (200 == result.getStatusCode()){
                                    getView().getImageUrl(Constants.Url.BASE_IMAGE_URL + request.getObjectKey());
                                }
                            }

                            @Override
                            public void onFailure() {
                                getView().showError("头像上传失败");
                            }
                        });
            }
        });
    }



    @Override
    public void showSuccess() {
        getView().refresh(UserBean.getInstance());
        getView().hideProgress();
    }

    @Override
    public void showOnSuccess(String msg) {
        getView().showOnSuccess(msg);
    }
}