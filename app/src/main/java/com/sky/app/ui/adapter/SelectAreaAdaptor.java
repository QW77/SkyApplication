package com.sky.app.ui.adapter;

import android.content.Context;

import com.sky.app.R;
import com.sky.app.bean.AreaDetail;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;

import java.util.List;

/**
 * 区域选择列表适配器
 */
public class SelectAreaAdaptor extends BaseRecyclerAdapter<AreaDetail> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<AreaDetail> list;

    public SelectAreaAdaptor(Context context, List<AreaDetail> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_select_area_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, AreaDetail item) {
        holder.setText(R.id.app_area, item.getName());
        holder.getTextView(R.id.app_area).setTag(item.getId());
    }
}