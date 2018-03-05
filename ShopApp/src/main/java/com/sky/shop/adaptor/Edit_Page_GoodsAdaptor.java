package com.sky.shop.adaptor;

import android.content.Context;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.ImageHelper;
import com.sky.shop.R;
import com.sky.shop.bean.ProductResponse;
import java.util.List;


/**
 * 所有商品列表适配器
 */
public class Edit_Page_GoodsAdaptor extends BaseRecyclerAdapter<ProductResponse> {
    protected Context mContext;
    private int selectedPos = -1;
    private int selectedPos2 = -1;
    private int flag;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<ProductResponse> list;

    public Edit_Page_GoodsAdaptor(Context context, List<ProductResponse> list, int flag){
        super(context, list);
        this.mContext = context;
        this.list = list;
        this.flag = flag;
    }



    @Override
    public int getItemLayoutId(int viewType) {
        switch (flag){
            case 1:
                return R.layout.activity_edit_page_goods_add;
            case 2:
                return R.layout.activity_edit_page_goods_item;
        }
        return 0;
    }


    @Override
    public void bindData(RecyclerViewHolder holder, int position, ProductResponse item) {
        switch (flag){
            case 1:
                break;
            case 2:
                ImageHelper.getInstance().displayDefinedImage(item.getProduct_image_url(),
                        holder.getImageView(R.id.app_image), R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                holder.setText(R.id.app_money, "¥" +item.getProduct_price_now());
                holder.setText(R.id.app_buy_num, item.getNum_good() + "人付款");
                holder.setText(R.id.app_desc, item.getProduct_name());
                break;
        }
    }
}