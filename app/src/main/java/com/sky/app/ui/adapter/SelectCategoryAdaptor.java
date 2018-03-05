package com.sky.app.ui.adapter;

import android.content.Context;

import com.sky.app.R;
import com.sky.app.bean.ProductAttribute;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;

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
        holder.getTextView(R.id.product_detail_size_name).setSelected(selectedPos == position ? true : false);
        holder.getTextView(R.id.product_detail_size_price).setTextColor(selectedPos == position ?
                AppUtils.parseColor("#d50d0d") : AppUtils.parseColor("#b3b3b3"));
    }

    /**
     * 设置选中pos
     * @param pos
     */
    public void setSelectedPos(int pos){
        this.selectedPos = pos;
    }
}