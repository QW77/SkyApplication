package com.sky.app.ui.activity.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.utils.LoadImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 查找适配器-装饰城,生产厂家,区域
 * Created by hongbang on 2017/4/25.
 */

public class AreaGridviewAdapter extends BaseAdapter {
    //    private AreaList areaList;
    private ArrayList<String> acountList;
    private Context context;
    private LayoutInflater mInflater = null;

    public AreaGridviewAdapter(ArrayList<String> acountList, Context context) {
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
        ViewHolder areaHolder;
        if (convertView == null) {
            areaHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_search_catogry, null);
//            areaholder.areaName = (TextView) convertView.findViewById(R.id.area_name);
            areaHolder.areaImg = (ImageView) convertView.findViewById(R.id.area_img);
            convertView.setTag(areaHolder);
        } else {
            areaHolder = (ViewHolder) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(acountList.get(position), areaHolder.areaImg,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);

        return convertView;
    }



    /**
     * 视图
     */
    static class ViewHolder {
        public ImageView areaImg;
//        public TextView areaName;
    }
}
