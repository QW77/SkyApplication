package com.sky.shop.adaptor;

import android.content.Context;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.shop.R;
import com.sky.shop.bean.StringBean;

import java.util.List;

/**
 * 分类信息适配器
 */
public class ListAdaptor extends BaseRecyclerAdapter<StringBean> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<StringBean> list;

    public ListAdaptor(Context context, List<StringBean> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_list_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, StringBean item) {
        holder.getTextView(R.id.app_string).setText(item.getValue());
        holder.getTextView(R.id.app_string).setTag(item.getId());
    }
}