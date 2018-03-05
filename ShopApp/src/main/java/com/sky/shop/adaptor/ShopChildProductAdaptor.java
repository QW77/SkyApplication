package com.sky.shop.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.shop.R;
import com.sky.shop.bean.ProductResponse;

import java.util.List;

/**
 * 店铺商品首页适配器
 */

public class ShopChildProductAdaptor extends BaseAdapter {

    private List<ProductResponse> list;
    private Context context;
    private LayoutInflater mInflater = null;

    public ShopChildProductAdaptor(Context context, List<ProductResponse> list) {
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
            convertView = mInflater.inflate(R.layout.app_shop_center_all_product_item, null);
            holder.app_image = (ImageView) convertView.findViewById(R.id.app_image);
            holder.app_money = (TextView)convertView.findViewById(R.id.app_money);
            holder.app_buy_num = (TextView)convertView.findViewById(R.id.app_buy_num);
            holder.app_desc = (TextView)convertView.findViewById(R.id.app_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(list.get(position).getProduct_image_url(),
                holder.app_image, R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        holder.app_money.setText("¥" + AppUtils.parseDouble(list.get(position).getProduct_price_now()/100));
        holder.app_buy_num.setText(list.get(position).getNum_good() + "人付款");
        holder.app_desc.setText(list.get(position).getProduct_name());
        return convertView;
    }

    /**
     * 视图
     */
    static class ViewHolder {
        public ImageView app_image;
        public TextView app_money;
        public TextView app_buy_num;
        public TextView app_desc;
    }
}
