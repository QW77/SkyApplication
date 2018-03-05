package com.sky.app.ui.activity.baidumap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener;
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.sky.app.R;
import com.sky.app.ui.activity.shop.CardActivity;
import com.sky.app.utils.PermissionUtil;

import java.util.List;

public class BaiduMapActivity1 extends AppCompatActivity implements View.OnClickListener {
    private BaiduMap mBaiduMap;
    private MapView mMapView;
    //    private EditText et_address;
    private Button bt_search;
    private View view1;

    boolean firstLoc = true;
    PoiSearch mPoiSearch;
    LatLng center;


    BaiduNaviManager mNaviManager;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private BitmapDescriptor mCurrentMarker;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private RoutePlanSearch mSearch;
    private final String TAG = "百度";
    private String[] PERMISSIONS_CONTACT = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    private int REQUEST_CONTACTS = 1;
    private WalkNavigateHelper mNaviHelper;
    private WalkNaviLaunchParam param;
    public static String ROUTE_PLAN_NODE = "ROUTE_PLAN_NODE";
    private BikeNaviLaunchParam paramBike;
    private BikeNavigateHelper mNaviBikeHelper;
    String authinfo;
    private EditText et_address;
    private String latitude;
    private String longitude;
    private double a;
    private double b;
    LatLng latLngend;
    LatLng start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mNaviManager= BaiduNaviManager.getInstance();//导航引擎初始化
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu_map1);
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts(mMapView);
        } else {
            init();
        }
    }


    public void showContacts(View v) {
        Log.i(TAG, "Show contacts button pressed. Checking permissions.");

        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Contacts permissions have not been granted.
            Log.i(TAG, "Contact permissions has NOT been granted. Requesting permissions.");
            requestContactsPermissions(v);

        } else {
            // Contact permissions have been granted. Show the contacts fragment.
            Log.i(TAG,
                    "Contact permissions have already been granted. Displaying contact details.");
            init();
        }
    }

    private void requestContactsPermissions(View v) {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)
                ) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            Log.i(TAG,
                    "Displaying contacts permission rationale to provide additional context.");

            // Display a SnackBar with an explanation and a button to trigger the request.
            Snackbar.make(v, "permission_contacts_rationale",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(BaiduMapActivity1.this, PERMISSIONS_CONTACT,
                                            REQUEST_CONTACTS);
                        }
                    })
                    .show();
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CONTACTS) {
            if (PermissionUtil.verifyPermissions(grantResults)) {

                init();

            } else {


            }


        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void init() {
        mMapView = (MapView) findViewById(R.id.view_map);
        mBaiduMap = mMapView.getMap();
        view1 = findViewById(R.id.view);
//_______________________导航____________________
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);//注册监听函数

        mBaiduMap.setMyLocationEnabled(true);//主动开启定位功能
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//普通地图

        initLocationClient();
        mLocationClient.start();

        //poi搜索监听
//        mPoiSearch = PoiSearch.newInstance();
        searchButtonAddress();
        latLngend = new LatLng(a, b);
        if (start != null && latLngend != null) {
            searchBikeLine();
        }

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_openmap_mark);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLngend)
                .icon(bitmap);
        mBaiduMap.addOverlay(option);

        //标记监听
        mBaiduMap.setOnMarkerClickListener(mMarkerClickListener);
        //路线监听
//        mSearch = RoutePlanSearch.newInstance();
//        mSearch.setOnGetRoutePlanResultListener(mRoutePlanResultListener);

    }


    //marker点击监听
    BaiduMap.OnMarkerClickListener mMarkerClickListener = new BaiduMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            LatLng location = marker.getPosition();
            latLngend = location;
            mBaiduMap.clear();
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_openmap_mark);
            marker.setIcon(bitmapDescriptor);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(location)
                    .icon(bitmapDescriptor);

            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
            searchBikeLine();

            return false;
        }
    };

    //骑行
    private void searchBikeLine() {
        paramBike = new BikeNaviLaunchParam().stPt(start).endPt(latLngend).vehicle(1);
        // 获取导航控制类
        mNaviBikeHelper = BikeNavigateHelper.getInstance();
        // 引擎初始化
        mNaviBikeHelper.initNaviEngine(this, new IBEngineInitListener() {
            @Override
            public void engineInitSuccess() {
                Log.d(TAG, "引擎初始化成功");
                routeBikePlanWithParam();
            }

            @Override
            public void engineInitFail() {
                Log.d(TAG, "引擎初始化失败");
            }
        });
    }

    /**
     * 开始算路(骑行)
     */
    private void routeBikePlanWithParam() {
        paramBike = new BikeNaviLaunchParam().stPt(start).endPt(latLngend);
        mNaviBikeHelper.routePlanWithParams(paramBike, new IBRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                Log.d(TAG, "开始算路");
            }

            @Override
            public void onRoutePlanSuccess() {
                Log.d(TAG, "算路成功,跳转至诱导页面");
                Intent intent = new Intent();
                intent.setClass(BaiduMapActivity1.this, BNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError error) {
                Log.d(TAG, "算路失败");
            }

        });
    }

    //路线监听
//    OnGetRoutePlanResultListener mRoutePlanResultListener = new OnGetRoutePlanResultListener() {
//        //行走路线结果
//        @Override
//        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
//            try {
//                List<WalkingRouteLine> walkingRouteLineList = walkingRouteResult.getRouteLines();
//                for (int i = 0; i < walkingRouteLineList.size(); i++) {
//                    WalkingRouteLine line = walkingRouteLineList.get(i);
//                    Log.e("walkline==", line.toString());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        @Override
//        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
//
//        }
//
//        @Override
//        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
//
//        }
//
//        @Override
//        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
//
//        }
//
//        @Override
//        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
//
//        }
//
//        @Override
//        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
//
//        }
//    };

    //设置配置
    private void initLocationClient() {
        //设置定位图标
        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;//跟随

        //创建option实例
        //option有很多默认设置，可以按需变更
        LocationClientOption option = new LocationClientOption();

        //设置定位模式，默认高精度
        //有高精度，低功耗，仅设备等模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //设置返回的定位结果坐标系
        //默认gcj02,另有BD09，bd09ll更加准确
        option.setCoorType("bd09ll");

        //设置定位间隔，默认为0，即只定位1次
        //此处设置定位请求的间隔大于等于1000ms
        option.setScanSpan(1000);

        //设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        //设置是否使用gps，默认false,
        option.setOpenGps(true);

        //设置是否当GPS有效时，是否按照1次/s的频率输出GPS结果，默认false
        //option.setLocationNotify(true);

        //设置是否需要位置语义化结果，默认false
        //设置为true后，可以在BDLocation.getLocationDescribe里得到类似于“在北京天安门附近”的结果
        option.setIsNeedLocationDescribe(true);

        //设置是否需要POI(Point of Interest，信息点)，默认false，同样可以在BDLocation.getPoiList里得到
        //option.setIsNeedLocationPoiList(true);

        //定位SDK内部是一个SERVICE，并放到了独立进程
        //此处设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(true);

        //设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);

        //设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(true);

        //设置返回结果包含手机方向
        option.setNeedDeviceDirect(true);

        //为LocationClient配置option
        mLocationClient.setLocOption(option);
    }

    //定位当前位置
    private void getLocation(BDLocation location) {
        // 开启定位图层

        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;   //默认为 LocationMode.NORMAL 普通态
//        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;  //定位罗盘态

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.loading_point_medium);
//                .fromResource(R.mipmap.icon_openmap_focuse_mark);
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);
    }

    @Override
    public void onClick(View v) {
    }

    //获取位置信息，并定位
    private void searchButtonAddress() {
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        a = Double.parseDouble(latitude);
        b = Double.parseDouble(longitude);
        Log.e("4444", a + "--------" + b);
    }


    class MyLocationListener extends BDAbstractLocationListener {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (firstLoc) {
                firstLoc = false;
                getLocation(bdLocation);
                start = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());

            }
        }

        //这个接口主要返回连接网络的类型
        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
//        mPoiSearch.destroy();
    }
}
