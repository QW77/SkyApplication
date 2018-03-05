package com.sky.app.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.Category;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 二级界面目录
 * Created by hongbang on 2017/4/25.
 */

public class SecondCategoryGridviewAdapter1234 extends BaseAdapter {

    private List<Category> acountList;

    private Context context;
    private LayoutInflater mInflater = null;


    public SecondCategoryGridviewAdapter1234(List<Category> acountList, Context context) {
        this.acountList = acountList;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return acountList.size();
    }

    @Override
    public Object getItem(int position) {
        return acountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();

            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.item_firstcategory1234, null);
            holder.category_img = (ImageView) convertView.findViewById(R.id.category_img);
            holder.category_name1 = (TextView)convertView.findViewById(R.id.category_name1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }


        holder.category_name1.setText(acountList.get(position).getTwo_dir_name());

        ImageHelper.getInstance().displayDefinedImage(acountList.get(position).getIcon_image_url(), holder. category_img,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);

        return convertView;
    }
    /**
     * 视图
     */
    static class ViewHolder {
        public ImageView category_img;
        public TextView category_name1;
    }
}
