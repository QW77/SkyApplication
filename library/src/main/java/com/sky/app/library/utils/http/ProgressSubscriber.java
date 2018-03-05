package com.sky.app.library.utils.http;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sky.app.library.base.MyApplication;
import com.sky.app.library.utils.L;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.AbstractList;
import java.util.AbstractMap;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 回调基本处理
 * @param <T>
 */
public class ProgressSubscriber<T> extends Subscriber<T> {

    private HttpUtils.IHttpCallBackListener mIHttpCallBackListener = null;

    /**
     * 构造参数
     */
    public ProgressSubscriber(HttpUtils.IHttpCallBackListener mIHttpCallBackListener) {
        this.mIHttpCallBackListener = mIHttpCallBackListener;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 完成
     */
    @Override
    public void onCompleted() {
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        String msg;
        if (e instanceof DefinedException) {
            msg = e.getMessage();
        } else if (e instanceof SocketTimeoutException) {
            msg = "网络请求超时，请检查网络设置或稍后再试";
        } else if (e instanceof ConnectException) {
            msg = "网络不可达，请检查网络设置或稍后再试";
        } else if (e instanceof UnknownHostException) {
            msg = "网络不可达，请检查网络设置或稍后再试";
        } else if (e instanceof InternalError) {
            msg = "网络不可达，请检查网络设置或稍后再试";
        } else if (e instanceof HttpException) {
            msg = "网络不可达，请检查网络设置或稍后再试";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            msg = "解析错误";
        } else {
            if (MyApplication.isDebug) {
                msg = "error:" + e.getMessage();
            } else {
                msg = "网络不可达，请检查网络设置或稍后再试";
            }
        }
        if (MyApplication.isDebug) {
            L.msg("堆栈错误信息：" + L.getStackTrace(e));
        }
        if (null != mIHttpCallBackListener){
            mIHttpCallBackListener.onFailure(msg);
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (null != mIHttpCallBackListener) {
            if (null == t){
                mIHttpCallBackListener.onSuccess("");
            }else if (t instanceof AbstractList || t instanceof AbstractMap) {
                mIHttpCallBackListener.onSuccess(new Gson().toJson(t));
            } else {
                mIHttpCallBackListener.onFailure(t == null ? "" : t.toString());
            }
        }
    }
}