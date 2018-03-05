package com.sky.app.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.sky.app.R;
import com.sky.app.bean.MyCollect;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 我的购物车列表适配器
 */
public class MyCollectProAdaptor extends BaseRecyclerAdapter<MyCollect> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<MyCollect> list;

    public MyCollectProAdaptor(Context context, List<MyCollect> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_good;
    }

    @Override
    public void bindData(final RecyclerViewHolder holder, final int position, final MyCollect item) {
        ImageHelper.getInstance().displayDefinedImage(item.getProduct_image_url(),
                holder.getImageView(R.id.app_image), R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        holder.setText(R.id.app_price, AppUtils.parseDouble(item.getProduct_price_now()/100));
        holder.setText(R.id.app_person, item.getNum_good() + "人付款");
        holder.setText(R.id.app_name, item.getProduct_name());
        holder.setText(R.id.app_sell_name, TextUtils.isEmpty(item.getSeller_name()) ? "" : item.getSeller_name());
        holder.setText(R.id.app_comment, item.getNum_comment() + "");
    }
}