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
import com.sky.app.bean.GoodShop;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.utils.DistanceLonLat;

import java.util.List;

/**
 * 查找适配器-装饰城,生产厂家,区域
 * Created by hongbang on 2017/4/25.
 */

public class GoodShopMoreAdapter extends BaseAdapter {
    private List<GoodShop.ListBean> acountList;
    private Context context;
    private LayoutInflater mInflater = null;
    private double longitude;//当前经度
    private double latitude;//当前纬度
    public GoodShopMoreAdapter(List<GoodShop.ListBean> acountList, Context context) {
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
            viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(acountList.get(position).getPic_url(), viewHolder.img1,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        viewHolder.name1.setText(acountList.get(position).getNick_name());
        viewHolder.zhuying.setText(acountList.get(position).getMain_business_desc());
        viewHolder.address.setText(acountList.get(position).getAddress());

        //计算当前位置距商户的距离并显示
        if (latitude != 0 && latitude != 0 && acountList.get(position).getLongitude() != 0 && acountList.get(position).getLatitude() != 0) {
            viewHolder.distance.setText(DistanceLonLat.getDistance(longitude, latitude, acountList.get(position).getLongitude(), acountList.get(position).getLatitude()));
        }


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
        TextView distance;

    }

    /**
     * 输入用户当前经纬度
     *
     * @param longitude 当前经度
     * @param latitude  当前纬度
     */
    public void setlonlat(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        notifyDataSetChanged();
        Log.i("LocationListener2","刷新数据:longitude:"+longitude+"    latitude:"+latitude);
    }
}
