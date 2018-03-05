package com.sky.app.library.component.banner.modle;

/**
 * @author Edwin.Wu
 * @version 2016/12/6 17:32
 * @since JDK1.8
 */
public class BannerInfo<T> {
    public T url;
    public String title;
    public String skipUrl;

    public BannerInfo(T url, String title, String skipUrl) {
        this.url = url;
        this.title = title;
        this.skipUrl = skipUrl;
    }
}
