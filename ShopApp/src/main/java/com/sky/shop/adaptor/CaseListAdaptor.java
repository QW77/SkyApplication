package com.sky.shop.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.utils.ImageHelper;
import com.sky.shop.R;
import com.sky.shop.bean.Case;

import java.util.List;

/**
 * 案例列表
 */
public class CaseListAdaptor extends BaseAdapter {
    private List<Case> list;
    private Context context;
    private LayoutInflater mInflater = null;

    public CaseListAdaptor(Context context, List<Case> list) {
        this.list = list;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.app_seller_center_item, null);
            viewHolder.app_image = (ImageView) convertView.findViewById(R.id.app_image);
            viewHolder.app_txt = (TextView) convertView.findViewById(R.id.app_txt);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(list.get(position).getProduct_image_url(),
                viewHolder.app_image, R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        viewHolder.app_txt.setText(list.get(position).getProduct_name());
        return convertView;
    }

    /**
     * 视图
     */
    static class ViewHolder {
        public ImageView app_image;
        public TextView app_txt;
    }
}
