package com.sky.shop.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sky.shop.R;
import com.sky.shop.bean.Category;
import java.util.List;

/**
 * Created by hongbang on 2017/4/25.
 */

public class RegisterAreaGridviewAdapter extends BaseAdapter {
    private List<Category> acountList;

    private Context context;

    public RegisterAreaGridviewAdapter(List<Category> acountList, Context context) {
        this.acountList = acountList;
        this.context = context;
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

        View pic = View.inflate(context, R.layout.item_search_catogry, null);
        TextView areaName = (TextView) pic.findViewById(R.id.area_name);
        areaName.setText(acountList.get(position).getOne_dir_name());
        return pic;
    }



}
