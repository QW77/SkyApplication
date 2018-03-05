package com.sky.shop.adaptor;

import android.content.Context;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.shop.R;
import com.sky.shop.bean.ProductAttribute;

import java.util.List;

/**
 * 价格属性信息适配器
 */
public class PriceAdaptor extends BaseRecyclerAdapter<ProductAttribute> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<ProductAttribute> list;

    public PriceAdaptor(Context context, List<ProductAttribute> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_add_product_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, ProductAttribute item) {
        holder.getTextView(R.id.app_attr_name).setText(item.getAttr_name());
        holder.getTextView(R.id.app_price).setText(item.getAttr_price_now() + "");
        holder.getTextView(R.id.app_num).setText(item.getInventory_num() + "");
    }
}