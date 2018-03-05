package com.sky.app.utils;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;

import java.text.NumberFormat;

/**
 * Created by Administrator on 2018/2/10 0010.
 */

public class DistanceLonLat {
    private static final double EARTH_RADIUS = 6378137.0;

    /**
     * 计算两经纬度距离
     * @param longitude1  第一个经度
     * @param latitude1     第一个纬度
     * @param longitude2    第二个经度
     * @param latitude2     第二个纬度
     * @return   根据数值大小,返回m或者km(带单位)
     */
    public static String getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;  //返回的是s,单位是米
        Log.i("xmy123 半径" ,"距离:"+s);
        if (s < 1000) {
            return s +" m";
        } else {
            //保留两位小数
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            return nf.format(s / 1000) + " Km";
        }
    }


    /**
     * 计算两点之间距离
     * @return 返回带单位, km或者m
     * @paramstart  第一个经纬度
     * @paramend    第二个经纬度
     */
    public String getDistance(LatLng start, LatLng end){
        //自己实现距离算法：
        /**
         * 计算两点之间距离
         * @param start
         * @param end
         * @return String  多少m ,  多少km
         */

        double lat1 = (Math.PI/180)*start.latitude;
        double lat2 = (Math.PI/180)*end.latitude;

        double lon1 = (Math.PI/180)*start.longitude;
        double lon2 = (Math.PI/180)*end.longitude;

//       double Lat1r = (Math.PI/180)*(gp1.getLatitudeE6()/1E6);
//       double Lat2r = (Math.PI/180)*(gp2.getLatitudeE6()/1E6);
//       double Lon1r = (Math.PI/180)*(gp1.getLongitudeE6()/1E6);
//       double Lon2r = (Math.PI/180)*(gp2.getLongitudeE6()/1E6);

        //地球半径
        double R = 6371.004;

        //两点间距离 m，如果想要米的话，结果*1000就可以了
        double dis =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;
        NumberFormat nFormat = NumberFormat.getNumberInstance();  //数字格式化对象
        if(dis < 1){               //当小于1千米的时候用,用米做单位保留一位小数

            nFormat.setMaximumFractionDigits(1);    //已可以设置为0，这样跟百度地图APP中计算的一样
            dis *= 1000;

            return nFormat.format(dis)+" m";
        }else{
            nFormat.setMaximumFractionDigits(2);
            return nFormat.format(dis)+" km";
        }


    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
