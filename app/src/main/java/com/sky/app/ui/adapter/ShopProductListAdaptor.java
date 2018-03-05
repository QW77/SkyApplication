package com.sky.app.ui.adapter;

import android.content.Context;

import com.sky.app.R;
import com.sky.app.bean.ProductResponse;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 商品列表适配器
 */
public class ShopProductListAdaptor extends BaseRecyclerAdapter<ProductResponse> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<ProductResponse> list;

    public ShopProductListAdaptor(Context context, List<ProductResponse> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_shop_center_all_product_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, ProductResponse item) {
        ImageHelper.getInstance().displayDefinedImage(item.getProduct_image_url(),
                holder.getImageView(R.id.app_image), R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        holder.setText(R.id.app_money, "¥" + AppUtils.parseDouble(item.getProduct_price_now()/100));
        holder.setText(R.id.app_buy_num, item.getNum_good() + "人付款");
        holder.setText(R.id.app_desc, item.getProduct_name());
    }
}