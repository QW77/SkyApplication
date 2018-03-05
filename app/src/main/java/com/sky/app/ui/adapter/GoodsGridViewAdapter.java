package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.Goods;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class GoodsGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Goods.ListBean> list;
    private LayoutInflater mInflate = null;

    public GoodsGridViewAdapter(List<Goods.ListBean> list, Context context) {
        this.context = context;
        this.list = list;
        this.mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            //根据自定义的Item布局加载布局
            convertView = mInflate.inflate(R.layout.item_apphome_goods, null);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.goods_picher);
            viewHolder.name = (TextView) convertView.findViewById(R.id.goods_name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.goods_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GoodsGridViewAdapter.ViewHolder) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(list.get(position).getProduct_image_url(), viewHolder.img,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        viewHolder.name.setText(list.get(position).getProduct_name());

        viewHolder.price.setText("¥" + AppUtils.parseDouble(list.get(position).getProduct_price_now()/100));
        return convertView;
    }

    /**
     * 视图
     */
    static class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView price;

    }
}
