package com.sky.shop.presenter;

import android.content.Context;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.OSSUtils;
import com.sky.app.library.utils.StringUtil;
import com.sky.app.library.utils.ThreadPoolManager;
import com.sky.shop.contract.ImageContract;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 *上传图片
 */

public class UploadImagePresenter extends BasePresenter<ImageContract.IUploadView>
        implements ImageContract.IUploadPresenter{

    public UploadImagePresenter(Context context, ImageContract.IUploadView view) {
        super(context, view);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void uploadFile(final String url) {
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                OSSUtils.getInstance(context).uploadFile(Md5Util.md5(System.currentTimeMillis() + StringUtil.randStr(10)),
                        url, new OSSUtils.OSSCallBack() {
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
}