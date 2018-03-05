package com.sky.app.library.utils.http;

import android.app.Activity;

import com.sky.app.library.base.MyApplication;
import com.sky.app.library.utils.L;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 请求类
 */
public class SimpleHttpUtils {
    private Activity activity = null;
    private static SimpleHttpUtils singleton = null;
    private OkHttpClient client = null;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private SimpleHttpUtils(Activity activity){
        this.activity = activity;
        initOkHttp();
    }

    /**
     * 初始化OKHttp
     */
    private void initOkHttp() {
        //是否打印日志
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//
//            @Override
//            public void log(String message) {
//                L.msg(message);
//            }
//
//        });
//        logging.setLevel(MyApplication.isDebug ?
//                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        client = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .readTimeout(12, TimeUnit.SECONDS)
//                .addInterceptor(logging)
                .build();
    }

    /**
     * 实例化对象
     * @return
     */
    public static SimpleHttpUtils getInstance(Activity activity){
        if (null == singleton){
            synchronized (SimpleHttpUtils.class){
                if (null == singleton){
                    singleton = new SimpleHttpUtils(activity);
                }
            }
        }
        return singleton;
    }

    /**
     * post请求
     */
    public void post(String url, String json,
                     final HttpUtils.IHttpCallBackListener iHttpCallBackListener){
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != iHttpCallBackListener){
                            iHttpCallBackListener.onFailure(e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != iHttpCallBackListener){
                            try {
                                iHttpCallBackListener.onSuccess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * get请求
     * @param url
     */
    public void get(String url, final HttpUtils.IHttpCallBackListener iHttpCallBackListener){
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != iHttpCallBackListener){
                            iHttpCallBackListener.onFailure(e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != iHttpCallBackListener){
                            try {
                                iHttpCallBackListener.onSuccess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
}
