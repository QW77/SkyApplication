package com.sky.app.library.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * 设置高度
 * Created by sky on 2017/4/15.
 */

public class GridViewHeight {

    /**
     * 设置
     * @param context
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(Context context, GridView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + AppUtils.dip2px(context, 45);
        listView.setLayoutParams(params);
    }
}
