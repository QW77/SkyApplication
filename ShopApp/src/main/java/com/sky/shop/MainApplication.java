package com.sky.shop;

import android.content.Context;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.sky.app.library.base.MyApplication;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.utils.ImageHelper;

/**
 * Created by sky on 2017/2/9.
 */

public class MainApplication extends MyApplication {
    private static Context sContext;
    public static Context getContext(){
        return sContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //图片缓存配置
        ImageHelper.getInstance().init(ImageHelper.getInstance().getImageLoaderConfiguration(this,
                Constants.Path.APP_IMAGE_CACHE_PATH).build());

        initAlibaba();

    }

    private void initAlibaba() {
        final String APP_KEY = "24642621";

//必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if(SysUtil.isTCMSServiceProcess(this)){
            return;
        }
//第一个参数是Application Context
//这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if(SysUtil.isMainProcess()){
            YWAPI.init(MainApplication.this, APP_KEY);
        }

    }
}
