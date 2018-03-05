package com.sky.app.ui.adapter.recycleview;


import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AskItem;
import com.sky.app.library.component.CircleImageView;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alibaba.mobileim.YWChannel.getResources;
import static com.tencent.open.utils.Global.getSharedPreferences;

/**
 * Created by xmy on 2018/1/3.
 */

public class AskListRcAdapter extends RecyclerView.Adapter<AskListRcAdapter.ViewHolder> implements View.OnClickListener {

    List<AskItem.ListBean> list = new ArrayList<>();
    protected Context mContext;

    public OnClickListener onClickListener;
    private String file_url;
    //    private SharedPreferences sp;
//    private String is_collect;

    public AskListRcAdapter(Context mContext) {
        this.mContext = mContext;
        this.list = list;

    }

    public void setList(List<AskItem.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 添加
     *
     * @param item
     */
    public void add(List<AskItem.ListBean> item) {
        this.list.clear();
        this.list.addAll(item);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ask, parent, false);
        ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
//        final ViewHolder holder = new ViewHolder(view);
        //将创建的View注册点击事件
//        if (mClickListener != null) {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
//                }
//
//            });
//        }
//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.itemView.setTag(position);
////        将position保存在itemView的Tag中，以便点击时进行获取
        ImageHelper.getInstance().displayDefinedImage(list.get(position).getPic_url(), holder.iv_head,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        holder.tv_name.setText(list.get(position).getUser_id());
        holder.tv_time.setText(list.get(position).getCreate_time());
        holder.tv_content.setText(list.get(position).getEval_comments());
        holder.tv_num.setText(list.get(position).getPraise_num());
        holder.askcoment.setText(list.get(position).getReplyNum());
        holder.pic.removeAllViews();
        View view = null;

        if (null != list.get(position).getFileList()) {
            for (int i = 0; i < list.get(position).getFileList().size(); i++) {
                view = LayoutInflater.from(mContext).inflate(R.layout.app_recommend_product_item, holder.pic, false);

                ImageHelper.getInstance().displayDefinedImage(list.get(position).getFileList().get(i).getFile_url(),
                        (ImageView) view.findViewById(R.id.app_image), R.mipmap.app_default_icon, R.mipmap.app_default_icon);
                holder.pic.addView(view);
            }

        }
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener) {
                    onClickListener.onClick(v, position);
                }
            }
        });
        holder.tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener) {
                    onClickListener.onClick(v, position);
                }
            }
        });
        holder.tv_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener) {
                    holder.tv_praise.setImageResource(R.mipmap.ask_prise_1);
                    onClickListener.onClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    private OnItemClickListener mOnItemClickListener = null;


    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 按钮点击事件
     *
     * @param listener
     */
    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public interface OnClickListener {
        void onClick(View itemView, int pos);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ask_iv_head)
        CircleImageView iv_head;//头像
        @BindView(R.id.ask_tv_username)
        TextView tv_name;//用户名
        @BindView(R.id.ask_tv_data_time)
        TextView tv_time;//时间
        @BindView(R.id.adk_tv_content)
        TextView tv_content;//评论内容
        @BindView(R.id.ask_tv_comment)
        ImageView tv_comment;//评论
        @BindView(R.id.ask_coment)
        TextView askcoment;
        @BindView(R.id.ask_tv_praise)
        ImageView tv_praise;//点赞
        @BindView(R.id.ask_praise_num)
        TextView tv_num;//点赞数
        @BindView(R.id.ask_pic)
        LinearLayout pic;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
