package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sky.app.R;
import com.sky.app.bean.Area;
import com.sky.app.bean.AreaList;
import com.sky.app.ui.activity.search.AreaGridviewAdapter;
import com.sky.app.ui.custom.AutoHeightGridView;

import java.util.ArrayList;

/**
 * 通过区域查找
 * Created by hongbang on 2017/4/25.
 */

public class SearchByAreaAdapter extends BaseAdapter {

    private ArrayList<Area> areaList;
    private ArrayList<String> areaName = new ArrayList<>();
    private AreaList area;
    private Context context;

    public SearchByAreaAdapter(AreaList area, Context context) {
        this.area = area;
        areaList= (ArrayList<Area>) area.getList();
        this.context = context;
    }

    @Override
    public int getCount() {
        return areaList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return areaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(position==0){
            View pic = View.inflate(context, R.layout.item_search_by_place_head, null);
            AutoHeightGridView gridView = (AutoHeightGridView) pic.findViewById(R.id.gridview);
            for (Area area : areaList) {
            areaName.add(area.getTwo_dir_name());
        }
            gridView.setAdapter(new AreaGridviewAdapter(areaName,context));
            return pic;


        }else{
            View pic = View.inflate(context, R.layout.item_search_by_space_bottom, null);
//            ImageView areaName = (ImageView) pic.findViewById(R.id.area_img);
//            areaName.setText(areaList.get(position).getTwo_dir_name());
            return pic;

        }


    }



}
