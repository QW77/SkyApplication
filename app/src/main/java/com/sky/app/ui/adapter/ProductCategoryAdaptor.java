package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.ProductCategory;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;

import java.util.List;

/**
 * 商品分类列表适配器
 */
public class ProductCategoryAdaptor extends BaseRecyclerAdapter<ProductCategory> {
    protected Context mContext;
    private int selectedPos = -1;
    private int selectedPos2 = -1;
    private int flag;

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
        switch (flag){
            case 1:
                return R.layout.app_product_category_item;
            case 2:
                return R.layout.app_product_category_1_item;
        }
        return 0;
    }

    @Override
    public void bindData(final RecyclerViewHolder holder, final int position, final ProductCategory item) {
        switch (flag){
            case 1:
                holder.getTextView(R.id.app_category_name).setText(item.getOne_dir_name());
                holder.getTextView(R.id.app_category_name).setSelected(selectedPos == position ? true : false);
                holder.getView(R.id.vertical_line).setVisibility(selectedPos == position ? View.GONE : View.VISIBLE);
                holder.itemView.setSelected(selectedPos == position ? true : false);
                holder.getImageView(R.id.app_arrow).setImageResource(selectedPos == position ?
                        R.mipmap.app_selected_right_arrow_icon : R.mipmap.app_unselected_right_arrow_icon);
                if (null == item.getTwo_dirs() || item.getTwo_dirs().isEmpty()){
                    holder.getImageView(R.id.app_arrow).setVisibility(View.INVISIBLE);
                }else{
                    holder.getImageView(R.id.app_arrow).setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                holder.getTextView(R.id.app_category_name).setText(item.getTwo_dir_name());
                holder.getTextView(R.id.app_category_name).setSelected(selectedPos2 == position ? true : false);
                break;
        }
    }

    /**
     * 设置选中pos
     * @param pos
     */
    public void setSelectedPos(int pos){
        this.selectedPos = pos;
    }

    /**
     * 设置选中pos
     * @param pos
     */
    public void setSelectedPos2(int pos){
        this.selectedPos2 = pos;
    }
}