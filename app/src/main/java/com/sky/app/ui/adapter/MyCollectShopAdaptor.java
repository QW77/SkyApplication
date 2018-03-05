package com.sky.app.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.sky.app.R;
import com.sky.app.bean.MyCollect;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 我的收藏商户列表适配器
 */
public class MyCollectShopAdaptor extends BaseRecyclerAdapter<MyCollect> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<MyCollect> list;

    public MyCollectShopAdaptor(Context context, List<MyCollect> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_my_collect_shop_item;
    }

    @Override
    public void bindData(final RecyclerViewHolder holder, final int position, final MyCollect item) {
        ImageHelper.getInstance().displayDefinedImage(item.getPic_url(),
                holder.getImageView(R.id.imageView), R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        holder.getTextView(R.id.name).setText(TextUtils.isEmpty(item.getNick_name()) ? "" : item.getNick_name());
        holder.getTextView(R.id.zhuying).setText(TextUtils.isEmpty(item.getMain_business_desc()) ? "" : item.getMain_business_desc());
        holder.getTextView(R.id.address).setText(item.getProvince_name() + item.getCity_name() + item.getArea_name() + item.getAddress());
        holder.getTextView(R.id.starnumber).setText(item.getNum_collect() + "");
    }
}