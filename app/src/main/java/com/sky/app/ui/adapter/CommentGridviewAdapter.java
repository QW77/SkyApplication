package com.sky.app.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sky.app.R;
import com.sky.app.library.utils.ImageHelper;

import java.io.File;
import java.util.List;

/**
 * Created by hongbang on 2017/4/25.
 */

public class CommentGridviewAdapter extends BaseAdapter {
    //    private AreaList areaList;
    private List<String> list;

    private Context context;

    public CommentGridviewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View pic = View.inflate(context, R.layout.activity_comment_item, null);
        ImageView tinjia_image = (ImageView) pic.findViewById(R.id.tinjia_image);
        if (getCount() - 1 == 0) {
            tinjia_image.setBackgroundResource(R.mipmap.xiao_jiahao);//最后一个显示加号图片
        } else {
            if (list.size() - 1 >= position) {
                ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(list.get(position))).toString(), tinjia_image,
                        R.mipmap.app_default_icon, R.mipmap.app_default_icon);
                tinjia_image.setBackgroundResource(R.mipmap.xiao_jiahao);//最后一个显示加号图片
            }
            if (position == 5) {
                tinjia_image.setVisibility(pic.GONE);
            }
        }
        return pic;
    }
}
