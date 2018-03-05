package com.sky.shop.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.shop.R;
import com.sky.shop.adaptor.ListAdaptor;
import com.sky.shop.bean.StringBean;
import com.sky.shop.custom.RecycleViewDivider;

import java.util.List;

/**
 * 列表数据
 * Created by sky on 2017/3/18.
 */

public class ListDialog extends Dialog{
    private Activity activity = null;
    private OnItemClickListener onItemClickListener;

    private RecyclerView recyclerView;
    private ListAdaptor listAdaptor;
    private List<StringBean> stringBeen;

    public ListDialog(@NonNull Activity activity, OnItemClickListener onItemClickListener, List<StringBean> stringBeen) {
        super(activity, R.style.CustomDialog);
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
        this.stringBeen = stringBeen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_layout);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        //初始化
        initView();
        initClick();
    }

    /**
     * 初始化事件
     */
    private void initClick() {
        listAdaptor = new ListAdaptor(activity, stringBeen);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(
                new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(activity, 1),
                        AppUtils.getSystemColor(activity, R.color.sky_color_f2f2f2)));
        listAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                if (null != onItemClickListener){
                    onItemClickListener.item(itemView, pos);
                }
            }
        });
        recyclerView.setAdapter(listAdaptor);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.app_list);
    }

    public interface OnItemClickListener{
        void item(View v, int pos);
    }
}
