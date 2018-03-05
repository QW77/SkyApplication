package com.sky.app.ui.activity.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.GoodShop;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 查找适配器-装饰城,生产厂家,区域
 * Created by hongbang on 2017/4/25.
 */

public class DecorationBoutiqueAdapter extends BaseAdapter {
    private List<DecorationTwoButique.ListBean> acountList;
    private Context context;
    private LayoutInflater mInflater = null;

    public DecorationBoutiqueAdapter(List<DecorationTwoButique.ListBean> acountList, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_search_by_space_bottom, null);
            viewHolder.img1 = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.name1 = (TextView) convertView.findViewById(R.id.name);
            viewHolder.zhuying = (TextView) convertView.findViewById(R.id.zhuying);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(acountList.get(position).getPic_url(), viewHolder.img1,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        viewHolder.name1.setText(acountList.get(position).getNick_name());
        viewHolder.zhuying.setText(acountList.get(position).getMain_business_desc());
        viewHolder.address.setText(acountList.get(position).getAddress());
        return convertView;
    }


    /**
     * 视图
     */
    static class ViewHolder {
        public ImageView img1;
        public TextView name1;
        public TextView zhuying;
        public TextView address;
    }
}
