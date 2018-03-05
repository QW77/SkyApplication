package com.sky.app.ui.custom;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 自定义webview
 * Created by sky on 2017/5/10.
 */

public class CustomWebView extends WebView {

    public CustomWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setLoadsImagesAutomatically(true);//自动加载图片
        webSettings.setAppCacheEnabled(false);//是否使用应用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
    }
}
