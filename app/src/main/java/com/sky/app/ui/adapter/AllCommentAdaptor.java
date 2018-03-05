package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sky.app.R;
import com.sky.app.bean.CommentResponse;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.component.RatingBar;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 全部评价适配器
 */
public class AllCommentAdaptor extends BaseRecyclerAdapter<CommentResponse> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<CommentResponse> list;

    public AllCommentAdaptor(Context context, List<CommentResponse> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_all_comment_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, CommentResponse item) {
        ImageHelper.getInstance().displayDefinedImage(item.getPic_url(), holder.getImageView(R.id.app_user_photo_icon),
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        holder.getTextView(R.id.app_user_name).setText(item.getNick_name());
        holder.getTextView(R.id.app_comment_time).setText(item.getComment_time());
        holder.getTextView(R.id.app_comment_txt).setText(item.getComment_content());
        ((RatingBar)(holder.getView(R.id.quality_rating_bar))).setStar(item.getQuality());
        ((RatingBar)(holder.getView(R.id.service_rating_bar))).setStar(item.getService());
        ((RatingBar)(holder.getView(R.id.speed_rating_bar))).setStar(item.getSpeed());

        LinearLayout recommend = (LinearLayout) holder.getView(R.id.comment_img);
        recommend.removeAllViews();
        View view = null;
        if (null != item.getPics()){
            for (String pic : item.getPics()){
                view = LayoutInflater.from(mContext).inflate(R.layout.app_recommend_product_item, recommend, false);
                ImageHelper.getInstance().displayDefinedImage(pic,(ImageView) view.findViewById(R.id.app_image), R.mipmap.app_default_icon, R.mipmap.app_default_icon);
                recommend.addView(view);
            }
        }
    }
}