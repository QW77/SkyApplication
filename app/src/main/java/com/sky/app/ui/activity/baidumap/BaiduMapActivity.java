package com.sky.app.ui.activity.baidumap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

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
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener;
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.sky.app.R;
import com.sky.app.utils.PermissionUtil;

import java.util.List;

public class BaiduMapActivity extends AppCompatActivity implements View.OnClickListener {
    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private EditText et_address;
    private Button bt_search;
    private View view1;

    boolean firstLoc = true;
    PoiSearch mPoiSearch;
    LatLng center;
    LatLng start;
    LatLng end;
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
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mNaviManager= BaiduNaviManager.getInstance();//导航引擎初始化
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu_map);
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
                                    .requestPermissions(BaiduMapActivity.this, PERMISSIONS_CONTACT,
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

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);//注册监听函数


        mBaiduMap.setMyLocationEnabled(true);//主动开启定位功能
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//普通地图

        initLocationClient();
        mLocationClient.start();
        //poi搜索监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(mPoiSearchResultListener);
        //标记监听
        mBaiduMap.setOnMarkerClickListener(mMarkerClickListener);
        //路线监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(mRoutePlanResultListener);

    }


    //poi搜索
    OnGetPoiSearchResultListener mPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        //获取POI检索结果
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null) {
                Log.e("poiResult==", "为空！");
                return;
            }
            Log.e("poiResult==", poiResult.toString());
            try {
                Log.e("getAllPoi==", poiResult.getAllPoi().size() + "");//获取推荐的位置信息
                for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                    PoiInfo poiInfo = poiResult.getAllPoi().get(i);
                    Log.e(i + "address==", poiInfo.address);
                    Log.e(i + "address==", poiInfo.location.toString());
                    LatLng location = poiInfo.location;

                    //构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.mipmap.loading_point_medium);

                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(location)
                            .icon(bitmap);

                    //在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);

                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("e==", e.getMessage());
            }


        }

        //获取Place详情页检索结果
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    //marker点击监听
    BaiduMap.OnMarkerClickListener mMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            LatLng location = marker.getPosition();
            end = location;
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
            popWin2();

//            searchWalkLine();
//            searchBikeLine();
//            routeplanToNavi();
            return false;
        }
    };


    PopupWindow popupWindow2;

    private void popWin2() {
        popupWindow2 = new PopupWindow(this);
        View view = View.inflate(this, R.layout.choose_popwindow, null);

        Button bt_default = (Button) view.findViewById(R.id.bt_walk);
        Button bt_define_pic = (Button) view.findViewById(R.id.bt_bike);
        bt_default.setOnClickListener(this);
        bt_define_pic.setOnClickListener(this);


        popupWindow2.setContentView(view);
        popupWindow2.setHeight(mMapView.getHeight());
        popupWindow2.setWidth(mMapView.getWidth());
//        Log.e("大小","高"+view0.getHeight()+"，宽"+view0.getWidth());
        popupWindow2.setOutsideTouchable(false);
        popupWindow2.setFocusable(true);
//        popupWindow2.showAtLocation(view0,Gravity.CENTER,0,0);
        popupWindow2.showAsDropDown(view1);
    }

    //骑行
    private void searchBikeLine() {
        paramBike = new BikeNaviLaunchParam().stPt(start).endPt(end).vehicle(1);
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
        paramBike = new BikeNaviLaunchParam().stPt(start).endPt(end);
        mNaviBikeHelper.routePlanWithParams(paramBike, new IBRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                Log.d(TAG, "开始算路");
            }

            @Override
            public void onRoutePlanSuccess() {
                Log.d(TAG, "算路成功,跳转至诱导页面");
                Intent intent = new Intent();
                intent.setClass(BaiduMapActivity.this, BNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError error) {
                Log.d(TAG, "算路失败");
            }

        });
    }


    //行走路线
    private void searchWalkLine() {
        PlanNode stNode = PlanNode.withLocation(start);
        PlanNode enNode = PlanNode.withLocation(end);
//        BNRoutePlanNode sNode=new BNRoutePlanNode()

//        mSearch.walkingSearch(new WalkingRoutePlanOption().from(stNode).to(enNode));
        mSearch.drivingSearch(new DrivingRoutePlanOption().from(stNode).to(enNode));
        param = new WalkNaviLaunchParam().stPt(start).endPt(end);

        // 获取导航控制类
        mNaviHelper = WalkNavigateHelper.getInstance();
        // 引擎初始化
        mNaviHelper.initNaviEngine(this, new IWEngineInitListener() {
            @Override
            public void engineInitSuccess() {
                Log.d(TAG, "引擎初始化成功");
                routePlanWithParam();
            }

            @Override
            public void engineInitFail() {
                Log.d(TAG, "引擎初始化失败");
            }
        });

    }

    /**
     * /**
     * 开始算路(行走)
     */
    private void routePlanWithParam() {
        mNaviHelper.routePlanWithParams(param, new IWRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                Log.d(TAG, "开始算路");
            }

            @Override
            public void onRoutePlanSuccess() {
                Log.d(TAG, "算路成功,跳转至诱导页面");
                Intent intent = new Intent();
                intent.setClass(BaiduMapActivity.this, WNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(WalkRoutePlanError error) {
                Log.d(TAG, "算路失败");
            }

        });
    }

    //路线监听
    OnGetRoutePlanResultListener mRoutePlanResultListener = new OnGetRoutePlanResultListener() {
        //行走路线结果
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            try {
                List<WalkingRouteLine> walkingRouteLineList = walkingRouteResult.getRouteLines();
                for (int i = 0; i < walkingRouteLineList.size(); i++) {
                    WalkingRouteLine line = walkingRouteLineList.get(i);
                    Log.e("walkline==", line.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };

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
    //驾车引擎初始化
//    private void initNavi() {
//
//        Log.e("appfoldername",getPackageName());
//        Log.e("appfoldername",Environment.getDownloadCacheDirectory().toString());
//        BaiduNaviManager.getInstance().init(this, Environment.getExternalStorageDirectory().toString(), getPackageName(),
//                new BaiduNaviManager.NaviInitListener() {
//                    @Override
//                    public void onAuthResult(int status, String msg) {
//                        if (0 == status) {
//                            authinfo = "key校验成功!";
//                        } else {
//                            authinfo = "key校验失败, " + msg;
//                        }
//                        BaiduMapActivity.this.runOnUiThread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                Toast.makeText(BaiduMapActivity.this, authinfo, Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                    @Override
//                    public void initSuccess() {
//                        Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void initStart() {
//                        Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void initFailed() {
//                        Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
//                    }
//                }, null /*mTTSCallback*/);
//    }
//    //驾车
//    private void routeplanToNavi() {
//        BNRoutePlanNode sNode = null;
//        BNRoutePlanNode eNode = null;
//
//        BNRoutePlanNode.CoordinateType coType = BNRoutePlanNode.CoordinateType.BD09LL;
//        sNode = new BNRoutePlanNode(116.30784537597782,
//                40.057009624099436, "百度大厦", null, coType);
//        eNode = new BNRoutePlanNode(116.40386525193937,
//                39.915160800132085, "北京天安门", null, coType);
//
//        if (sNode != null && eNode != null) {
//            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
//            list.add(sNode);
//            list.add(eNode);
//            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
//        }
//    }

//    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {
//
//        private BNRoutePlanNode mBNRoutePlanNode = null;
//
//        public DemoRoutePlanListener(BNRoutePlanNode node) {
//            mBNRoutePlanNode = node;
//        }
//
//        @Override
//        public void onJumpToNavigator() {
//            Intent intent = new Intent(BaiduMapActivity.this, BNDemoGuideActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//
//        @Override
//        public void onRoutePlanFailed() {
//            // TODO Auto-generated method stub
//
//        }
//    }

    //定位当前位置
    private void getLocation(BDLocation location) {
        // 开启定位图层


        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;   //默认为 LocationMode.NORMAL 普通态
//        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;  //定位罗盘态

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.loading_point_medium);
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                searchButtonAddress();
                break;
            case R.id.bt_walk:
                popupWindow2.dismiss();
                searchWalkLine();
                break;
            case R.id.bt_bike:
                popupWindow2.dismiss();
                searchBikeLine();
                break;
        }
    }





    //获取位置信息，并定位
    private void searchButtonAddress() {
        //        北纬N34°20′42.01″ 东经E108°54′49.77″
//        E118°39′58.90″","latitude":"N31°56′18.17″

        double[] location =getLocation(31,56,18.17,118,39,58.90);

//        latitude = getIntent().getStringExtra("latitude");
//        longitude = getIntent().getStringExtra("longitude");

        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
          converter.from(CoordinateConverter.CoordType.BD09LL);
        // sourceLatLng待转换坐标
        LatLng sourceLatLng=new LatLng(location[0],location[1]);
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();

        Log.e("--------","lan="+desLatLng.latitude+"=longlan="+desLatLng.longitude);


//        String address = et_address.getText().toString().trim();
//        if (address.equals("")) {
//            return;
//        }
//        mPoiSearch.searchInCity((new PoiCitySearchOption())
//                .city("南京")
//                .keyword(address)
//                .pageNum(10));
        //点击搜索让键盘隐藏
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

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
        mPoiSearch.destroy();
    }

    /**
     * 位置坐标转换
     *
     * @param latitude1  纬度_度
     * @param latitude2  纬度_分
     * @param latitude3  纬度_秒
     * @param longitude1 经度_度
     * @param longitude2 经度_分
     * @param longitude3 经度_秒
     * @return location[0]为纬度, location[1]为经度
     */
    public double[] getLocation(double latitude1, double latitude2, double latitude3, double longitude1, double longitude2, double longitude3) {
        double[] location = new double[2];
        location[0] = latitude1 + latitude2 / 60 + latitude3 / 3600;
        location[1] = longitude1 + longitude2 / 60 + longitude3 / 3600;
        return location;
    }

}
