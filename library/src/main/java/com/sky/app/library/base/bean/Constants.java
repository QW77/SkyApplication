package com.sky.app.library.base.bean;

import android.os.Build;

import com.sky.app.library.utils.AppUtils;

/**
 * Created by sky on 2017/2/13.
 * 常量类
 */

public final class Constants {
    public abstract static class ListStatus{
        /**
         * 刷新
         */
        public final static int REFRESH = 1;

        /**
         * 加载更多
         */
        public final static int LOADMORE = 2;
    }
    /**
     * http状态
     */
    public abstract static class HttpStatus{

        /**
         * 成功
         */
        public final static int HTTP_OK_STATUS = 0;

        /**
         * 失败
         */
        public final static int HTTP_FAIL_STATUS = -1;

        /**
         * 断网
         */
        public final static int HTTP_NONET_STATUS = 400;
    }

    /**
     * 路径
     */
    public abstract static class Path{

        /**
         * IMAGE_CACHE_PATH 图片缓存目录
         */
        public static final String APP_IMAGE_CACHE_PATH = AppUtils.getSDPath()+ "/com.sky.app/images/";

        /**
         * IMAGE_CACHE_PATH 图片缓存目录
         */
        public static final String SHOP_IMAGE_CACHE_PATH = AppUtils.getSDPath()+ "/com.sky.shop/images/";

        /**
         * IMAGE_CACHE_PATH 图片缓存目录
         */
        public static final String DRIVER_IMAGE_CACHE_PATH = AppUtils.getSDPath()+ "/com.sky.driver/images/";

        /**
         * IMAGE_CACHE_PATH 图片缓存目录
         */
        public static final String TRANSPORT_IMAGE_CACHE_PATH = AppUtils.getSDPath()+ "/com.sky.transport/images/";

        /**
         * 默认IMAGE_CACHE_PATH 图片缓存目录
         */
        public static final String IMAGE_CACHE_PATH = AppUtils.getSDPath()+ "/com.sky.default/images/";

        /**
         * ERROR_LOG_PATH 系统崩溃日志
         */
        public static final String ERROR_LOG_PATH = AppUtils.getSDPath() + "/com.sky.app/error_log/";
    }

    /**
     * 接口地址
     */
    public abstract static class Url{

        /**
         * 基础地址
         */
        public static final String BASE_URL = "http://api.app.51craftsman.com/";//正式网络环境

 //       public static final String BASE_URL = "http://192.168.0.110:8080/51gj_server/";//本地测试环境

        /**
         * 发送验证码基础地址
         */
        public static final String BASE_CODE_URL = "http://smspay.api.365pays.cn/";

        /**
         * 图片地址前缀
         */
        public static final String BASE_IMAGE_URL = "http://51gongjiang.oss-cn-shanghai.aliyuncs.com/";
    }

    /**
     * android api 号
     */
    public abstract static class VERSION_CODES{

        /**
         * android 4.0
         */
        public static final int ANDROID_API_14 = Build.VERSION_CODES.ICE_CREAM_SANDWICH;

        /**
         * android 4.0.3
         */
        public static final int ANDROID_API_15 = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;

        /**
         * android 4.1
         */
        public static final int ANDROID_API_16 = Build.VERSION_CODES.JELLY_BEAN;

        /**
         * android 4.2
         */
        public static final int ANDROID_API_17 = Build.VERSION_CODES.JELLY_BEAN_MR1;

        /**
         * android 4.3
         */
        public static final int ANDROID_API_18 = Build.VERSION_CODES.JELLY_BEAN_MR2;

        /**
         * android 4.4
         */
        public static final int ANDROID_API_19 = Build.VERSION_CODES.KITKAT;

        /**
         * android 4.4W
         */
        public static final int ANDROID_API_20 = Build.VERSION_CODES.KITKAT_WATCH;

        /**
         * android 5.0
         */
        public static final int ANDROID_API_21 = Build.VERSION_CODES.LOLLIPOP;

        /**
         * android 5.1
         */
        public static final int ANDROID_API_22 = Build.VERSION_CODES.LOLLIPOP_MR1;

        /**
         * android 6.0
         */
        public static final int ANDROID_API_23 = Build.VERSION_CODES.M;

        /**
         * android 7.0
         */
        public static final int ANDROID_API_24 = Build.VERSION_CODES.N;

        /**
         * android 7.1
         */
        public static final int ANDROID_API_25 = Build.VERSION_CODES.N_MR1;
    }

    /**
     * 网络类型
     */
    public abstract static class Network{

        /**
         * 没有网络
         */
        public static final int NO_NETWORK = 0;

        /**
         * 无限网络
         */
        public static final int NETWORK_WIFI = 1;

        /**
         * 数据网络
         */
        public static final int NETWORK_MOBILE = 2;
    }

    /**
     * 权限
     */
    public abstract static class Permission{
        public static final int CAMERA_PERMISSION = 0;//照相机
        public static final int LOACTION_PERMISSION = 1;//定位
    }

    /**
     * 错误信息
     */
    public abstract static class Error{
        public static final String ERROR_NO_CONTEXT = "toSubscribe method must declaraction activity or frament";
    }
}
