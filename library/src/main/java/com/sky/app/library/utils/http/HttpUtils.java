package com.sky.app.library.utils.http;

import android.content.Context;

import com.sky.app.library.base.MyApplication;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.bean.Result;
import com.sky.app.library.utils.L;

import java.util.concurrent.TimeUnit;

//import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 请求类
 */
public class HttpUtils {
    private static HttpUtils singleton = null;
    private Retrofit retrofit = null;
    private Context context = null;

    private HttpUtils(Context context) {
        this.context = context;
    }

    /**
     * 实例化对象
     *
     * @return
     */
    public static HttpUtils getInstance(Context context) {
        if (null == singleton) {
            synchronized (HttpUtils.class) {
                if (null == singleton) {
                    singleton = new HttpUtils(context);
                }
            }
        }
        return singleton;
    }

    /**
     * 初始化请求
     */
    public HttpUtils init(String baseUrl){
        //是否打印日志
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
//                new HttpLoggingInterceptor.Logger() {
//
//            @Override
//            public void log(String message) {
//                L.msg(message);
//            }
//
//        }
//        );
//        logging.setLevel(MyApplication.isDebug ?
//                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        //设置超时时间和日志打印
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .connectTimeout(1000, TimeUnit.SECONDS)
//                .writeTimeout(1000, TimeUnit.SECONDS)
//                .readTimeout(1000, TimeUnit.SECONDS)
//                .build();
//                .addInterceptor(logging);


        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//json转换
                .client(httpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();


        return this;
    }
    /**
     * 创建api
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createApi(Class<T> clazz) {
        //创建订阅
        return this.retrofit.create(clazz);
    }

    /**
     * 执行请求
     *
     * @param observable
     * @param listener   回调对象
     */
    public Subscription exec(Observable observable, IHttpCallBackListener listener) {
        return observable.subscribeOn(Schedulers.io())
                .map(new HttpResultFunc())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber(listener));
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     */
    private class HttpResultFunc<T> implements Func1<Result, T> {

        @Override
        public T call(Result result) {
            if (result.getCode() != Constants.HttpStatus.HTTP_OK_STATUS) {
                throw new DefinedException(result.getMsg());
            }
            return (T) result.getData();
        }
    }

    /**
     * 回调接口
     */
    public interface IHttpCallBackListener {

        /**
         * 成功加载
         *
         * @param success
         */
        void onSuccess(String success);

        /**
         * 失败加载
         *
         * @param error
         */
        void onFailure(String error);
    }
}
