package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.MyCollect;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;

import java.util.List;

/**
 * 发布适配器
 */
public class MyCollectPubAdaptor extends BaseRecyclerAdapter<MyCollect> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    private List<MyCollect> list;

    public MyCollectPubAdaptor(Context context, List<MyCollect> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_my_collect_pub_item;
    }

    @Override
    public void bindData(final RecyclerViewHolder holder, final int position, final MyCollect item) {
        holder.setText(R.id.app_title_tv, item.getProduct_name());
        holder.setText(R.id.app_time, item.getCreate_time());
        holder.setText(R.id.app_desc, item.getProduct_name());
        holder.setClickListener(R.id.del, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
    }
}