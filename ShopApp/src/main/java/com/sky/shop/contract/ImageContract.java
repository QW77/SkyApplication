package com.sky.shop.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;

/**
 * Created by sky on 2017/2/10.
 * 上传图片契约类
 */

public class ImageContract {

    /**
     * 上传图片更新UI方法
     */
    public interface IUploadView extends IBaseView {
        void getImageUrl(String url);
    }

    /**
     * 上传图片处理业务逻辑
     */
    public interface IUploadPresenter extends IBasePresenter {
        void uploadFile(String url);
    }

    /**
     * 上传图片网络请求
     */
    public interface IUploadModel extends IBaseModel{
    }
}