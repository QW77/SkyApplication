package com.sky.app.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.L;
import com.sky.app.utils.DistanceLonLat;

/**
 * 通过区域查找,用户的
 * Created by hongbang on 2017/4/25.
 */

public class SearchCustomAdapter extends BaseAdapter {

    private UserBeanList userBeanList;
    private Context context;
    private double longitude;//当前经度
    private double latitude;//当前纬度

    public SearchCustomAdapter(UserBeanList userBeanList, Context context) {
        this.userBeanList = userBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userBeanList.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return userBeanList.getList().get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserBeanDetail userBean = userBeanList.getList().get(position);
        UserHodler userHodler = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_search_by_space_bottom, null);
            userHodler = new UserHodler();
            userHodler.userHead = (ImageView) convertView.findViewById(R.id.imageView);
            userHodler.userName = (TextView) convertView.findViewById(R.id.name);
            userHodler.zhuYing = (TextView) convertView.findViewById(R.id.zhuying);
            userHodler.address = (TextView) convertView.findViewById(R.id.address);
//            userHodler.starNumber = (TextView) convertView.findViewById(R.id.starnumber);
//            userHodler.app_recommend_icon = (ImageView) convertView.findViewById(R.id.app_recommend_icon);
            userHodler.distance = (TextView) convertView.findViewById(R.id.distance);
            convertView.setTag(userHodler);
        } else {
            userHodler = (UserHodler) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(), userHodler.userHead,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);

        userHodler.userName.setText(userBean.getNick_name());
        userHodler.zhuYing.setText(userBean.getMain_business_desc());
        userHodler.address.setText(userBean.getAddress());

        //计算当前位置距商户的距离并显示
        if (latitude != 0 && latitude != 0 && userBean.getLongitude() != 0 && userBean.getLatitude() != 0) {
            userHodler.distance.setText(DistanceLonLat.getDistance(longitude, latitude, userBean.getLongitude(), userBean.getLatitude()));
        }

//        double aDouble = Double.valueOf(userBean.getNum_collect());
//        int aDouble1 = (int) aDouble;
//        userHodler.starNumber.setText(aDouble1 + "");
//
//        if (0 == userBean.getOther_flag()) {
//            userHodler.app_recommend_icon.setVisibility(View.GONE);
//        } else {
//            userHodler.app_recommend_icon.setVisibility(View.VISIBLE);
//        }
        return convertView;

    }

    class UserHodler {

        ImageView userHead;
        TextView userName;
        TextView zhuYing;
        TextView address;
//        TextView starNumber;
//        ImageView app_recommend_icon;
        TextView distance;
    }

    //刷新适配器,追加
    public void appendData(UserBeanList userBeanList) {
        this.userBeanList.getList().addAll(userBeanList.getList());
        L.msg("数据==>" + this.userBeanList.getList().size());
        notifyDataSetChanged();
    }

    //刷新适配器,重新填充
    public void refreshData(UserBeanList userBeanList) {
        this.userBeanList.getList().clear();
        this.userBeanList.getList().addAll(userBeanList.getList());
        notifyDataSetChanged();
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
