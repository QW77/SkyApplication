package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;

import java.util.List;

/**
 * 发布列表适配器
 */
public class PublishAdaptor extends BaseRecyclerAdapter<SupplyDetail> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<SupplyDetail> list;

    public PublishAdaptor(Context context, List<SupplyDetail> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_home_publish_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, SupplyDetail item) {
        holder.setText(R.id.app_title_tv, item.getProduct_name());
        holder.setText(R.id.app_time, item.getCreate_time());
        holder.setText(R.id.app_desc, item.getProduct_desc());
        holder.setClickListener(R.id.app_collect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
        if (item.getIs_collect() == 1){//收藏过
            AppUtils.changeTextViewIcon(mContext, holder.getTextView(R.id.app_collect),
                    R.mipmap.app_collect_orange_icon, 1);
            holder.getTextView(R.id.app_collect).setText("已收藏");
//            holder.getTextView(R.id.app_collect).setEnabled(false);
        }else{
            AppUtils.changeTextViewIcon(mContext, holder.getTextView(R.id.app_collect),
                    R.mipmap.app_collect_grey_icon, 1);
            holder.getTextView(R.id.app_collect).setText("收藏");
//            holder.getTextView(R.id.app_collect).setEnabled(true);
        }
    }
}