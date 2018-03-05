package com.sky.shop.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sky.shop.R;

import java.util.ArrayList;

/**
 * Created by hongbang on 2017/4/25.
 */

public class AreaGridviewAdapter extends BaseAdapter {
//    private AreaList areaList;
    private ArrayList<String> acountList;

    private Context context;

    public AreaGridviewAdapter(ArrayList<String> acountList, Context context) {
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
        areaName.setText(acountList.get(position));
        return pic;
    }



}
