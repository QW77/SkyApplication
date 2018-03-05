package com.sky.app.ui.activity.ask;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class Swipelayout {


    private boolean mRefreshing = false;//是否正在下拉刷新
    private boolean mLoadingData = false;//是否正在加载数据

    public void setRefreshing(boolean refreshing) {
        mRefreshing = refreshing;
    }
}
