package com.sky.app.ui.adapter;

import android.content.Context;

import com.sky.app.R;
import com.sky.app.bean.Case;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 案例列表适配器
 */
public class CaseListAdaptor extends BaseRecyclerAdapter<Case> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<Case> list;

    public CaseListAdaptor(Context context, List<Case> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_seller_center_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Case item) {
        holder.setText(R.id.app_txt, item.getProduct_name());
        ImageHelper.getInstance().displayDefinedImage(item.getProduct_image_url(),
                holder.getImageView(R.id.app_image), R.mipmap.app_default_icon, R.mipmap.app_default_icon);
    }
}