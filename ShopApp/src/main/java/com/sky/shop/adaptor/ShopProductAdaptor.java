package com.sky.shop.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.ImageHelper;
import com.sky.shop.R;
import com.sky.shop.bean.ProductResponse;
import com.sky.shop.bean.ShopProductDetail;
import com.sky.shop.custom.AutoHeightGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺商品主页列表适配器
 */
public class ShopProductAdaptor extends BaseRecyclerAdapter<ShopProductDetail> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<ShopProductDetail> list;
    private ShopChildProductAdaptor shopChildProductAdaptor;
    private OnItemClickListener onItemClickListener;

    public ShopProductAdaptor(Context context, List<ShopProductDetail> list,
                              OnItemClickListener onItemClickListener){
        super(context, list);
        this.mContext = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_shop_center_1_item;
    }

    @Override
    public void bindData(final RecyclerViewHolder holder, final int position, final ShopProductDetail item) {
        holder.setText(R.id.category_name, item.getTwo_dir_name());
        holder.setText(R.id.category_desc, item.getRemark());
        LinearLayout recommend = (LinearLayout) holder.getView(R.id.recommend_product_ll);
        recommend.removeAllViews();
        View view = null;
        int i = 0;
        for (ProductResponse productResponse : item.getRecom_products()){
            view = LayoutInflater.from(mContext).inflate(R.layout.app_recommend_product_item, recommend, false);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener){
                        onItemClickListener.onItemViewClick(v, position, finalI);
                    }
                }
            });
            ImageHelper.getInstance().displayDefinedImage(productResponse.getProduct_image_url(),
                    (ImageView) view.findViewById(R.id.app_image), R.mipmap.app_default_icon, R.mipmap.app_default_icon);
            recommend.addView(view);
            i++;
        }

        List<ProductResponse> productList = new ArrayList<>();
        shopChildProductAdaptor = new ShopChildProductAdaptor(mContext, productList);
        ((AutoHeightGridView)holder.getView(R.id.all_product)).setAdapter(shopChildProductAdaptor);
        ((AutoHeightGridView)holder.getView(R.id.all_product)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if (null != onItemClickListener){
                    onItemClickListener.onItemClick(view, position, pos);
                }
            }
        });
        productList.addAll(item.getAll_products());
        shopChildProductAdaptor.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int parentId, int childId);
        void onItemViewClick(View itemView, int parentId, int childId);
    }
}