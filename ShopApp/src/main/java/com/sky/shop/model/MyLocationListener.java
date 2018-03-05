package com.sky.shop.model;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

/**
 * Created by Administrator on 2018/2/9 0009.
 */

public class MyLocationListener extends BDAbstractLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取经纬度相关（常用）的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取地址相关的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

        String addr = location.getAddrStr();    //获取详细地址信息
        String country = location.getCountry();    //获取国家
        String province = location.getProvince();    //获取省份
        String city = location.getCity();    //获取城市
        String district = location.getDistrict();    //获取区县
        String street = location.getStreet();    //获取街道信息

        Log.i("LocationListener1", "location:" + addr + "  city:" + city + "  district:" + district + "  street:" + street);

        double latitude = location.getLatitude();    //获取纬度信息
        double longitude = location.getLongitude();    //获取经度信息
        float radius = location.getRadius();    //获取定位精度，默认值为0.0f

        String coorType = location.getCoorType();
        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

        int errorCode = location.getLocType();
        //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

        if (listener != null) {
            listener.getAddr(addr,longitude,latitude);
        }

        Log.i("LocationListener2", "latitude:" + latitude + "  longitude:" + longitude + "  radius:" + radius + "  coorType:" + coorType + " errorCode:" + errorCode);
    }

    public interface AddrListener {
        void getAddr(String addr, double longitude, double latitude);
    }

    private AddrListener listener;

    public void setAddrListener(AddrListener addrListener) {
        listener = addrListener;
    }

}
