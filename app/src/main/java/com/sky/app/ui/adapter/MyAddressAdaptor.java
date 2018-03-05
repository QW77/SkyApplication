package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.sky.app.R;
import com.sky.app.bean.AddressDetail;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;

import java.util.List;

/**
 * 我的收货地址列表适配器
 */
public class MyAddressAdaptor extends BaseRecyclerAdapter<AddressDetail> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<AddressDetail> list;

    public MyAddressAdaptor(Context context, List<AddressDetail> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_mine_my_address_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, AddressDetail item) {
        holder.setText(R.id.app_user_name, item.getContact_name());
        holder.setText(R.id.app_mobile, item.getContact_mobile());
        holder.setText(R.id.app_detail, item.getContact_address());

        CheckBox checkBox = (CheckBox)holder.getView(R.id.app_check);
        if (1 == item.getIs_default()){//默认收货地址
            checkBox.setChecked(true);
            checkBox.setTextColor(AppUtils.getSystemColor(mContext, R.color.main_colorPrimary));
        }else{
            checkBox.setChecked(false);
            checkBox.setTextColor(AppUtils.getSystemColor(mContext, R.color.sky_color_303030));
        }

        holder.setClickListener(R.id.app_check, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
        holder.setClickListener(R.id.app_del, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
        holder.setClickListener(R.id.app_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
    }
}