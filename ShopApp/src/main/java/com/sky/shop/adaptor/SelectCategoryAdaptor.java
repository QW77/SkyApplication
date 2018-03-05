package com.sky.shop.adaptor;

import android.content.Context;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.shop.R;
import com.sky.shop.bean.ProductAttribute;

import java.util.List;

/**
 * 商品选择
 */
public class SelectCategoryAdaptor extends BaseRecyclerAdapter<ProductAttribute> {
    protected Context mContext;
    private int selectedPos = -1;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<ProductAttribute> list;

    public SelectCategoryAdaptor(Context context, List<ProductAttribute> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_select_product_category_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, ProductAttribute item) {
        holder.getTextView(R.id.product_detail_size_name).setText(item.getAttr_name());
        holder.getTextView(R.id.product_detail_size_price).setText("¥" +
                AppUtils.parseDouble(item.getAttr_price_now()/100));
        holder.getTextView(R.id.app_remain).setText("库存" + item.getInventory_num() + "件");
    }
}