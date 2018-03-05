package com.sky.app.ui.adapter;

import android.content.Context;

import com.sky.app.R;
import com.sky.app.bean.ShopCarDetail;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 确认订单列表列表适配器
 */
public class MyConfirmOrderAdaptor extends BaseRecyclerAdapter<ShopCarDetail> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<ShopCarDetail> list;

    public MyConfirmOrderAdaptor(Context context, List<ShopCarDetail> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_muti_confirm_order_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, ShopCarDetail item) {
        ImageHelper.getInstance().displayDefinedImage(item.getProduct_image_url(), holder.getImageView(R.id.app_img),
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        holder.setText(R.id.app_product_title, item.getProduct_name());
        holder.setText(R.id.app_attr_name, item.getAttr_name());
        holder.setText(R.id.app_price, "¥" + AppUtils.parseDouble(item.getAttr_price_now()/100));
        holder.setText(R.id.app_num, "x" + item.getProduct_num());
    }
}