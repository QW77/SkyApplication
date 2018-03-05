package com.sky.app.ui.activity.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationPingMian;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 查找适配器-装饰城,生产厂家,区域
 * Created by hongbang on 2017/4/25.
 */

public class DecorationPingAdapter extends BaseAdapter {
    private List<DecorationPingMian.ListBean> acountList;
    private Context context;
    private LayoutInflater mInflater ;

    public DecorationPingAdapter(List<DecorationPingMian.ListBean> acountList, Context context) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_decoration_ping, null);
            viewHolder.img1 = (ImageView) convertView.findViewById(R.id.ping_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(acountList.get(position).getAddress_pic_url(), viewHolder.img1,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);

        return convertView;
    }


    /**
     * 视图
     */
    static class ViewHolder {
        public ImageView img1;

    }
}
