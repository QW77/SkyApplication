package com.sky.app.ui.adapter.recycleview;

import android.content.Context;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.AskItem;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class AskListRcAdapterHou extends BaseRecyclerAdapter<AskItem.ListBean> {

    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    private List<AskItem.ListBean> list;

    public AskListRcAdapterHou(Context context, List<AskItem.ListBean> list) {
        super(context,list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_ask_product_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, AskItem.ListBean item) {
        holder.setText(R.id.ask_tv_username,item.getUser_id());
        holder.setText(R.id.ask_tv_data_time,item.getCreate_time());
        holder.setText(R.id.adk_tv_content,item.getEval_comments());
        holder.setText(R.id.ask_pic,item.getPic_url());
        holder.setText(R.id.ask_tv_praise,item.getPraise_num());
        //点击点赞
        holder.getButton(R.id.upAndDown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
        holder.getButton(R.id.ask_tv_reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
    }


    }
