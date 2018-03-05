package com.sky.app.bean;

/**
 * Created by sky on 2017/3/18.
 */
public class AppKey {

    public static class HomePage{
        public final static String APP_TAB_LABEL = "app_tab_label";
        public final static int index = 0;
        public final static int publish = 1;
        public final static int message = 2;
        public final static int mime = 3;
        public final static int openIM = 4;
        public final static int openIMText = 5;

        /*卖家中心 店铺*/
        public final static int message_center = 0;
        public final static int order_manage = 1;
        public final static int shop_decorate = 2;
    }

    public static class XmlName {
        /**
         * 系统配置
         */
        public static final String APP_SETTING = "app_xml_setting";

        /**
         * 统一缓存对象
         */
        public final static String APP_HISTORY_DATA = "app_history_data";

        /**
         * 用户缓存对象
         */
        public final static String APP_CACHE_USER_INFO = "app_cache_user_info";
    }

    public static class KeyName {

        /**
         * 第一次安装标示
         */
        public static final String ISFIRST_INSTALL = "isfirst_install_config";

    }
}
