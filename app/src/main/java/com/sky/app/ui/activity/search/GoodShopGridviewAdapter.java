package com.sky.app.ui.activity.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.GoodShop;
import com.sky.app.library.component.CustomImageView;
import com.sky.app.library.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;



public class GoodShopGridviewAdapter extends BaseAdapter {
    private List<GoodShop.ListBean> acountList;
    private Context context;
    private LayoutInflater mInflater = null;

    public GoodShopGridviewAdapter(List<GoodShop.ListBean> acountList, Context context) {
        this.acountList = acountList;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 6;
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
            convertView = mInflater.inflate(R.layout.item_apphome_goodshop, null);
            viewHolder.img1 = (ImageView) convertView.findViewById(R.id.goodshop_img);
//            viewHolder.name = (TextView) convertView.findViewById(R.id.goodshop_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(acountList.get(position).getAdvertist_pic_url(), viewHolder.img1,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
//        viewHolder.name.setText(acountList.get(position).getNick_name());
        return convertView;
    }


    /**
     * 视图
     */
    static class ViewHolder {
        public ImageView img1;
        public TextView name;
    }
}
