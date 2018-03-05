package com.sky.app.presenter;

import android.content.Context;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.sky.app.bean.SellMessageComplete;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.SellerContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.OSSUtils;
import com.sky.app.library.utils.StringUtil;
import com.sky.app.library.utils.ThreadPoolManager;
import com.sky.app.model.SellMessageCompleteModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 *上传图片
 */

public class UploadActivityPresenter extends BasePresenter<SellerContract.IUploadView>
        implements SellerContract.IUploadPresenter{


    SellerContract.IUploadModel  iUploadModel;

    public UploadActivityPresenter(Context context, SellerContract.IUploadView view) {
        super(context, view);
        iUploadModel=new SellMessageCompleteModel(context,this);
    }

    @Override
    public void destroy() {
        if (null != iUploadModel){
            iUploadModel.destroy();
        }
        super.destroy();
    }


    @Override
    public void showError(String msg) {
        getView().showError(msg);
    }

    @Override
    public void uploadFirstFile(final String url) {
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
                                    getView().getFirstImageUrl(Constants.Url.BASE_IMAGE_URL + request.getObjectKey());
                                }
                            }

                            @Override
                            public void onFailure() {
                                getView().showError("上传失败");
                            }
                        });
            }
        });
    }

    @Override
    public void uploadSecondFile(final String url) {
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
                                    getView().getSecondImageUrl(Constants.Url.BASE_IMAGE_URL + request.getObjectKey());
                                }
                            }

                            @Override
                            public void onFailure() {
                                getView().showError("上传失败");
                            }
                        });
            }
        });
    }

    @Override
    public void sellRegister(SellMessageComplete sellMessageComplete) {
        iUploadModel.sellRegister(sellMessageComplete);
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        getView().registerSuccess(userBean);
    }


}