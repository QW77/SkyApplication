package com.sky.shop.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sky.app.library.component.CustomImageView;
import com.sky.app.library.utils.ImageHelper;
import com.sky.shop.R;
import com.sky.shop.bean.FirstCategoryDetail;

import java.util.List;

/**
 * 一级分类适配器
 */

public class FirstCategoryAdapter extends BaseAdapter {

    private List<FirstCategoryDetail> list;
    private Context context;
    private LayoutInflater mInflater = null;

    public FirstCategoryAdapter(Context context, List<FirstCategoryDetail> list) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();

            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.app_home_type_item, null);
            holder.img = (CustomImageView) convertView.findViewById(R.id.app_type_img);
            holder.title = (TextView)convertView.findViewById(R.id.app_type_txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(list.get(position).getIcon_image_url(), holder.img,
                R.mipmap.category_default_icon, R.mipmap.category_default_icon);
        holder.title.setText(list.get(position).getOne_dir_name());
        return convertView;
    }

    /**
     * 视图
     */
    static class ViewHolder {
        public CustomImageView img;
        public TextView title;
    }
}
