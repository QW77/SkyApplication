package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;

import java.util.List;

/**
 * 我的发布列表适配器
 */
public class MyPublishAdaptor extends BaseRecyclerAdapter<SupplyDetail> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<SupplyDetail> list;

    public MyPublishAdaptor(Context context, List<SupplyDetail> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_mine_my_publish_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, SupplyDetail item) {
        holder.setText(R.id.app_title_tv, item.getProduct_name());
        holder.setText(R.id.app_time, item.getCreate_time());
        holder.setText(R.id.app_desc, item.getProduct_desc());
        holder.setClickListener(R.id.del, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
        holder.setClickListener(R.id.edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
    }
}