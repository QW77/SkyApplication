package com.sky.app.library.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;

/**
 * 阿里云文件上传下载
 * Created by sky on 2017/3/22.
 */

public class OSSUtils {
    private static OSSUtils instance = null;
    private OSS oss;
    private Context context;

    /**
     * 配置参数
     * http://oss-cn-hangzhou.aliyuncs.com
     */
    private static final String APP_ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com/";
    private static final String APP_ACCESSKEYID = "LTAIPTm5O09Ux42e";
    private static final String APP_ACCESSKEYSECRET = "pB0Yg26TG7vAxLifmqboGrPePQl9fK";

    private static final String APP_BUCKET = "51gongjiang";
    private static final String APP_UPLOADOBJECT = "image";

    public static OSSUtils getInstance(Context context){
        if (null == instance){
            synchronized (OSSUtils.class){
                if (null == instance){
                    instance = new OSSUtils(context);
                }
            }
        }
        return instance;
    }

    public OSSUtils(Context context){
        this.context = context;
        initConfig();
    }

    /**
     * 初始化配置参数
     */
    private void initConfig() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(APP_ACCESSKEYID, APP_ACCESSKEYSECRET);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context, APP_ENDPOINT, credentialProvider, conf);
    }

    /**
     * 异步上传文件
     */
    public void uploadFile(String key, String path, final OSSCallBack callBack) {
        if (TextUtils.isEmpty(key)){
            return;
        }
        if (TextUtils.isEmpty(path)){
            return;
        }
        ((Activity)(context)).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtils.showUploadLoading((Activity)(context));
            }
        });
        key = APP_UPLOADOBJECT + File.separator + key + ".png";
        PutObjectRequest put = new PutObjectRequest(APP_BUCKET, key, path);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("application/octet-stream");
        put.setMetadata(meta);

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                if (null != callBack){
                    callBack.progress(request, currentSize, totalSize);
                }
            }
        });

        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, final PutObjectResult result) {
                ((Activity)(context)).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    if (null != callBack){
                        callBack.onSuccess(request, result);
                    }
                    DialogUtils.hideUploadLoading();
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                ((Activity)(context)).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    if (null != callBack){
                        callBack.onFailure();
                    }
                    DialogUtils.hideUploadLoading();
                    }
                });
            }
        });
    }

    /**
     * 回调
     */
    public interface OSSCallBack {

        /**
         * 进度
         */
        void progress(PutObjectRequest request, long currentSize, long totalSize);

        /**
         * 请求成功
         */
        void onSuccess(PutObjectRequest request, PutObjectResult result);

        /**
         * 请求失败
         */
        void onFailure();
    }
}
