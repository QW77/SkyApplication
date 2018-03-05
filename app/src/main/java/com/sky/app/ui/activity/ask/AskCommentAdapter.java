package com.sky.app.ui.activity.ask;

import android.content.Context;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
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

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 全部评价适配器
 */
public class AskCommentAdapter extends BaseRecyclerAdapter<AskSecondComent.ListBean> {
    protected Context mContext;


    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<AskSecondComent.ListBean> list;
    private String eval_comments;
    private String s;
    private String txt;

    public AskCommentAdapter(Context context, List<AskSecondComent.ListBean> list) {
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.ask_all_comment_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, AskSecondComent.ListBean item) {

        ImageHelper.getInstance().displayDefinedImage(item.getPic_url(), holder.getImageView(R.id.app_user_photo_icon),
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);

        holder.getTextView(R.id.app_user_name).setText(item.getUser_id());
        holder.getTextView(R.id.app_comment_time).setText(item.getCreate_time());
        holder.getTextView(R.id.app_comment_txt).setText(item.getEval_comments());

        LinearLayout recommend = (LinearLayout) holder.getView(R.id.comment_img);
        recommend.removeAllViews();
        View view = null;
        if (null != item.getFileList()) {
            for (int i = 0; i < item.getFileList().size(); i++) {
                view = LayoutInflater.from(mContext).inflate(R.layout.app_recommend_product_item, recommend, false);
                ImageHelper.getInstance().displayDefinedImage(item.getFileList().get(i).getFile_url(),
                        (ImageView) view.findViewById(R.id.app_image), R.mipmap.app_default_icon, R.mipmap.app_default_icon);
                recommend.addView(view);
            }

        }
    }


}

