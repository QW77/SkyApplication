package com.sky.shop.adaptor;

import android.content.Context;
import android.view.View;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.shop.R;
import com.sky.shop.bean.ProductCategory;

import java.util.List;

/**
 * 商品分类列表适配器
 */
public class ProductCategoryAdaptor extends BaseRecyclerAdapter<ProductCategory> {
    protected Context mContext;

    int flag = -1;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<ProductCategory> list;

    public ProductCategoryAdaptor(Context context, List<ProductCategory> list, int flag){
        super(context, list);
        this.mContext = context;
        this.list = list;
        this.flag = flag;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_shop_category_item;
    }

    @Override
    public void bindData(final RecyclerViewHolder holder, final int position, final ProductCategory item) {
        switch (flag){
            case 1:
                holder.getTextView(R.id.app_content).setText(item.getOne_dir_name());
                break;
            case 2:
                holder.getTextView(R.id.app_content).setText(item.getTwo_dir_name());
                break;
        }
        holder.getView(R.id.list_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener) {
                    onClickListener.onClick(v, position);
                }
            }
        });
        holder.getTextView(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener) {
                    onClickListener.onClick(v, position);
                }
            }
        });

        holder.getTextView(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener) {
                    onClickListener.onClick(v, position);
                }
            }
        });

    }
}