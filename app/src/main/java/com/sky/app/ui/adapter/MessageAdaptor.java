package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.Message;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;

import java.util.List;

/**
 * 消息列表适配器
 */
public class MessageAdaptor extends BaseRecyclerAdapter<Message> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<Message> list;

    public MessageAdaptor(Context context, List<Message> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_message_center_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, Message item) {
        holder.getTextView(R.id.app_message_title).setText(item.getTitle());
        holder.getTextView(R.id.app_message_txt).setText(item.getContent());
        holder.getTextView(R.id.app_date).setText(item.getCreate_time());
        holder.setClickListener(R.id.app_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
    }
}