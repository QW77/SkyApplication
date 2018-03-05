package com.sky.app.library.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.sky.app.library.utils.SPUtil;

/**
 * Created by sky on 2017/2/9.
 * 父application
 */
public class MyApplication extends Application{

    /**
     * 日志开关
     */
    public static boolean isDebug = false;

    @Override
    public void onCreate() {
        super.onCreate();

        //错误监控
//        CrashHandler.getInstance().init(this);

        //缓存
        SPUtil.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public Application getApp(){
        return (Application) getApplicationContext();
    }


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
