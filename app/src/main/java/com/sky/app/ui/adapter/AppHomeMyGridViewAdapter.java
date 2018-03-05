package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.library.component.CustomImageView;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class AppHomeMyGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<FirstCategoryDetail> lists;//数据源
    private int mIndex; // 页数下标，标示第几页，从0开始
    private int mPargerSize;// 每页显示的最大的数量
    private LayoutInflater mInflater = null;

    public AppHomeMyGridViewAdapter(Context context, List<FirstCategoryDetail> lists, int mIndex, int mPargerSize) {
        this.context = context;
        this.lists = lists;
        this.mIndex = mIndex;
        this.mPargerSize = mPargerSize;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 先判断数据及的大小是否显示满本页lists.size() > (mIndex + 1)*mPagerSize
     * 如果满足，则此页就显示最大数量lists的个数
     * 如果不够显示每页的最大数量，那么剩下几个就显示几个
     */
    @Override
    public int getCount() {
        return lists.size() > (mIndex + 1) * mPargerSize ?
                mPargerSize : (lists.size() - mIndex * mPargerSize);
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position + mIndex * mPargerSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPargerSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();

            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.app_home_type_item, null);
            holder.img = (CustomImageView) convertView.findViewById(R.id.app_home_type_img);
            holder.title = (TextView) convertView.findViewById(R.id.app_home_type_txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
        final int pos = position +mIndex * mPargerSize;//假设mPageSiez
        //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9

        ImageHelper.getInstance().displayDefinedImage(lists.get(pos).getIcon_image_url(), holder.img,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        holder.title.setText(lists.get(pos).getOne_dir_name());
        return convertView;
    }

    static class ViewHolder {
        public CustomImageView img;
        public TextView title;
    }
}
