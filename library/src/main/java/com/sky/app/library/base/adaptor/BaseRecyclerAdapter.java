package com.sky.app.library.base.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用适配器
 * @param <T>
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected final List<T> mData;
    protected final Context mContext;
    protected LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    public OnClickListener onClickListener;

    public BaseRecyclerAdapter(Context ctx, List<T> list) {
        this.mContext = ctx;
        this.mInflater = LayoutInflater.from(ctx);
        this.mData = (list != null) ? list : new ArrayList<T>();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(mContext,
                mInflater.inflate(getItemLayoutId(viewType), parent, false));
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }

            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return true;
                }

            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindData(holder, position, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }

    /**
     * 添加
     * @param pos
     * @param item
     */
    public void add(int pos, T item) {
        this.mData.add(pos, item);
        notifyItemInserted(pos);
    }

    /**
     * 添加
     * @param item
     */
    public void add(List<T> item) {
        this.mData.clear();
        this.mData.addAll(item);
        notifyDataSetChanged();
    }

    /**
     * 添加所有
     * @param item
     */
    public void addAll(List<T> item) {
        this.mData.addAll(item);
        notifyDataSetChanged();
    }

    /**
     * 删除
     * @param pos
     */
    public void delete(int pos) {
        this.mData.remove(pos);
        notifyItemRemoved(pos);
    }

    /**
     * 替换某一行的数据
     * @param position
     * @param data
     */
    public void replace(int position, T data) {
        this.mData.remove(position);
        this.mData.add(position == 0 ? position : position - 1, data);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clear() {
        this.mData.clear();
        notifyDataSetChanged();
    }

    public abstract int getItemLayoutId(int viewType);

    public abstract void bindData(RecyclerViewHolder holder, int position, T item);

    /**
     * 设置点击事件
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    /**
     * 长按事件
     * @param listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    /**
     * 按钮点击事件
     * @param listener
     */
    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int pos);
    }

    public interface OnClickListener {
        void onClick(View itemView, int pos);
    }
}