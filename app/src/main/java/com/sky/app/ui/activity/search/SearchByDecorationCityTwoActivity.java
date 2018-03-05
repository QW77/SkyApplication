package com.sky.app.ui.activity.search;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.sky.app.R;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SearshDecorationLongAndlatitude;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.banner.LoopViewPagerLayout;
import com.sky.app.library.component.banner.listener.OnBannerItemClickListener;
import com.sky.app.library.component.banner.listener.OnLoadImageViewListener;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.banner.modle.IndicatorLocation;
import com.sky.app.library.component.banner.modle.LoopStyle;
import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.model.MyLocationListener;
import com.sky.app.presenter.SearchByDecorationCityTwoPresenter;
import com.sky.app.ui.activity.publish.PublishDecorationCityHeadlinearActivity;
import com.sky.app.ui.activity.seller.SellerCenterActivity;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.ui.activity.shop.CardActivity;
import com.sky.app.ui.adapter.SearchCustomAdapter;
import com.sky.app.ui.adapter.SearchDecortionTwoAdapter;
import com.sky.app.ui.custom.AutoHeightListView;
import com.sky.app.utils.DistanceLonLat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 根据装饰城查找查找
 */
public class SearchByDecorationCityTwoActivity extends BaseViewActivity<UserContract
        .ISearchByDecorationCityTwoPresenter>
        implements UserContract.ISearchByDecorationCityTwo,
        PullToRefreshBase.OnRefreshListener2<ScrollView>, MyLocationListener.AddrListener {

    @BindView(R.id.app_edit_content)
    EditText editText;
    @BindView(R.id.app_search_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    @BindView(R.id.decoration_listview_left)
    ListView decorationListviewLeft;
    @BindView(R.id.decoration_listview)
    AutoHeightListView listview;
    @BindView(R.id.scrollview1)
    PullToRefreshScrollView scrollview;
    @BindView(R.id.mLoopViewPagerLayout11)
    LoopViewPagerLayout mLoopViewPagerLayout11;
    @BindView(R.id.app_city_1)
    ImageView appCity1;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.app_sh)
    TextSwitcher sh;

    @BindView(R.id.app_city_3)
    ImageView appCity3;
    @BindView(R.id.app_city_4)
    ImageView appCity4;
    @BindView(R.id.zhuangshicheng_zhaodian)
    RelativeLayout zhuangshichengZhaodian;
    @BindView(R.id.zhuangshicheng_pingmiantu)
    RelativeLayout zhuangshichengPingmiantu;
    @BindView(R.id.zhuangshicheng_action)
    RelativeLayout zhuangshichengaction;
    @BindView(R.id.decoration_pingmian)
    LinearLayout decorationPingmian;
    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.navigate)
    LinearLayout navigate;
    @BindView(R.id.app_distance)
    TextView baidudistance;
    private int switcherCount = 0;
    private Handler mHandler = new Handler();
    private String decorateCity = "";
    private int page = 1;
    private int total = -1;
    private String two_dir_id = "";
    private SearchCustomAdapter searchCustomAdapter;
    private UserBeanList userBeanList = new UserBeanList();
    private List<SearchDecorationTwoLeft.ListBean> listLeft;
    private SearchDecortionTwoAdapter searchDecortionTwoAdapter;
    List<ImageView> secondlist = new ArrayList<>();
    private String three_dir_id;
    private String one_dir_id;
    private String decorativeId;
    private ArrayList<SearshDecorationLongAndlatitude> decorationlistlong;
    private String longitude;
    private String latitude;
    private String three_dir_name;
    //----------------------
    private BaiduMap mBaiduMap;
    private MapView mMapView;

    LatLng start = null;
    LatLng end = null;

    /**
     * 百度定位经纬度
     */
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明

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
    private SearshDecorationLongAndlatitude.ListBean bdLocation;
    private double s;
    private final int MULTIPLE = 1000000;//距离换算成Km,需要除这个倍数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_search_by_decoration_city1);
        ButterKnife.bind(this);

        getlonlat();
    }

    private void getlonlat() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        myListener.setAddrListener(this);
        //注册监听函数
        LocationClientOption option = mLocationClient.getLocOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(8000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.start();
    }

    @Override
    protected UserContract.ISearchByDecorationCityTwoPresenter presenter() {
        return new SearchByDecorationCityTwoPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        scrollview.setOnRefreshListener(this);
    }


    @Override
    protected void init() {
        //获取传进来的three_dir_id就是装饰城的编码
        decorateCity = getIntent().getStringExtra("three_dir_id");
        show(decorateCity);
        //导航
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);//注册监听函数
        initLocationClient();
        mLocationClient.start();
        //此处填写商店位置坐标


        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        getPresenter().getData(decorateCity);
        //请求装饰城中的banner
        getPresenter().requestBanner(decorateCity);
        //请求装饰城中的商家头条
        getPresenter().requestDecorationHeadlines(decorateCity);
//        //请求装饰城的经纬度
        getPresenter().requestLatitudeAndLong(decorateCity);
        onPullDownToRefresh(scrollview);

        /*第一个banner*/
        mLoopViewPagerLayout11.setLoop_ms(3000);//轮播的速度(毫秒)
        mLoopViewPagerLayout11.setLoop_duration(800);//滑动的速率(毫秒)
        mLoopViewPagerLayout11.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        mLoopViewPagerLayout11.setIndicatorLocation(IndicatorLocation.Right);
        mLoopViewPagerLayout11.initializeData(context);//初始化数据
        mLoopViewPagerLayout11.setOnLoadImageViewListener(new OnLoadImageViewListener() {
            @Override
            public void onLoadImageView(ImageView imageView, Object parameter) {
                ImageHelper.getInstance().displayDefinedImage(String.valueOf(parameter), imageView,
                        R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        mLoopViewPagerLayout11.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
                if (!TextUtils.isEmpty(banner.get(index).skipUrl)) {
                    AppUtils.skipBrowser(context, banner.get(index).skipUrl);
                }
            }
        });

        //获取装饰城中的二级分类
        one_dir_id = getIntent().getStringExtra("one_dir_id");
        three_dir_id = getIntent().getStringExtra("three_dir_id");

        getPresenter().getSearchTwo(decorateCity);
        initDecortion();
        onPullDownToRefresh(scrollview);
    }

    private void initDecortion() {
        userBeanList.setList(new ArrayList<UserBeanDetail>());
        searchCustomAdapter = new SearchCustomAdapter(this.userBeanList, this);

        listview.setAdapter(searchCustomAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (SearchByDecorationCityTwoActivity.this.userBeanList.getList().get(position).getSeller_type()) {
                    case "店铺":
                        Intent j = new Intent(context, ShopCenterActivity.class);
                        j.putExtra("seller_id", SearchByDecorationCityTwoActivity.this.userBeanList.getList().get(position).getUser_id());
                        startActivity(j);
                        break;
                    case "个人主页":
                        Intent i = new Intent(context, SellerCenterActivity.class);
                        i.putExtra("seller_id", SearchByDecorationCityTwoActivity.this.userBeanList.getList().get(position).getUser_id());
                        startActivity(i);
                        break;
                    case "名片":
                        Intent k = new Intent(context, CardActivity.class);
                        k.putExtra("seller_id", SearchByDecorationCityTwoActivity.this.userBeanList.getList().get(position).getUser_id());
                        startActivity(k);
                        break;
                }
            }
        });
        getUser();
    }

    @Override
    protected void onDestoryActivity() {
        mLoopViewPagerLayout11.stopLoop();
        mLocationClient.stop();
//        citytoutiao.stop();
//        citytoutiao1.stop();
    }


    @Override
    public void showBannerSuccess(List<BannerInfo> list) {

        mLoopViewPagerLayout11.setLoopData((ArrayList<BannerInfo>) list);

    }

    @Override
    public void success(DecorationCityList categoryList) {
    }


    @Override
    public void userDataSuccess(UserBeanList userBeanList) {
        scrollview.onRefreshComplete();
        if ("0".equals(userBeanList.getAll_page() + "")) {
            total = 3;
        } else {
            total = userBeanList.getAll_page();
        }

        if (isHaveMore()) {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("正在加载...");// 刷新时
            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
        } else {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("已经到底");// 刷新时
            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
        }

        if (page == 1) {
            searchCustomAdapter.refreshData(userBeanList);
        } else {
            searchCustomAdapter.appendData(userBeanList);
        }

    }

    //展示装饰城中的二级分类
    @Override
    public void showDecorationTwoLeft(SearchDecorationTwoLeft list) {
        listLeft = new ArrayList<>();
        for (int i = 0; i < list.getList().size(); i++) {
            listLeft.add(list.getList().get(i));
        }
        searchDecortionTwoAdapter = new SearchDecortionTwoAdapter(listLeft, context);
        decorationListviewLeft.setAdapter(searchDecortionTwoAdapter);
        itemOnclick();
    }

    //展示精品馆里面的数据
    @Override
    public void showBoutiquesuccess(List<DecorationTwoButique.ListBean> butiqueList) {


    }

    //展示装饰城中的商家头条
    @Override
    public void showDecorationHeadSuccess(final DecorationHeadlinearDetai[] headlinears) {
        final List<DecorationHeadlinearDetai> list = new ArrayList<>();
        list.addAll(Arrays.asList(headlinears));

        String[] string = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            string[i] = list.get(i).getNoticeTitle();
//            decorativeId = list.get(i).getDecorativeId();
        }
        sh.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(context);
                return tv;
            }
        });
        sh.setInAnimation(context, R.anim.enter);
        sh.setOutAnimation(context, R.anim.out);
        sh.setText(string[0] + "\n" + string[1]);
        if (sh != null)
            startCity(string);
    }

    private void startCity(final String[] strings) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sh == null)
                    return;
                String strA = strings[switcherCount % strings.length];
                String strB = switcherCount % strings.length + 1 < strings.length ? strings[switcherCount % strings.length + 1] : strings[0];
                switcherCount += 2;
                sh.setText(strA + "\n" + strB);
                startCity(strings);
            }
        }, 1500);
    }

    /**
     * 展示装饰城的经纬度
     *
     * @param longAndlatitude
     */
    @Override
    public void showDecorationLongAndLatitude(SearshDecorationLongAndlatitude longAndlatitude) {
        Log.i("xmy123 装饰城经纬度", "  开始计算经纬度:");
        decorationlistlong = new ArrayList<>();
        for (int i = 0; i < longAndlatitude.getList().size(); i++) {
            longitude = longAndlatitude.getList().get(i).getLongitude();
            latitude = longAndlatitude.getList().get(i).getLatitude();
            three_dir_name = longAndlatitude.getList().get(i).getThree_dir_name();
        }

        Log.i("xmy123 装饰城经纬度", longitude + "  纬度:" + latitude);

        addressTv.setText(three_dir_name);
        String s1 = String.valueOf(this.s);
        baidudistance.setText(s1);
    }

    void show(String decorateCity) {
        switch (decorateCity) {
            case "ZSC10000101001":
                end = new LatLng(31.938381, 118.666361);
                break;
            case "ZSC1000010101":
                end = new LatLng(32.04919, 118.736369);
                break;
            case "ZSC10000101013":
                end = new LatLng(31.9343, 118.669411);
                break;
            case "ZSC1000010103":
                end = new LatLng(32.093339, 118.776031);
                break;
            case "ZSC1000010104":
                end = new LatLng(32.093589, 118.76725);
                break;
            case "ZSC1000010105":
                end = new LatLng(32.051539, 118.785519);
                break;
            case "ZSC1000010106":
                end = new LatLng(32.286483, 118.781975);
                break;
            case "ZSC1000010107":
                end = new LatLng(32.136631, 118.731631);
                break;
            case "ZSC10000101100":
                end = new LatLng(32.000331, 118.756678);
                break;
            case "ZSC10000101101":
                end = new LatLng(32.012889, 118.761919);
                break;
            case "ZSC10000101102":
                end = new LatLng(32.332381, 118.848492);
                break;
            case "ZSC10000101103":
                end = new LatLng(32.00339, 118.758069);
                break;
            case "ZSC10000101104":
                end = new LatLng(32.9145, 118.651639);
                break;
            case "ZSC10000101105":
                end = new LatLng(32.094839, 118.764339);
                break;
            case "ZSC1000010111":
                end = new LatLng(32.019889, 118.82375);
                break;
            case "ZSC1000010112":
                end = new LatLng(32.019139, 118.8205);
                break;
        }
    }


    //        @OnClick({R.id.navigate})
//    void turnToMap() {
//        Intent intent = new Intent(this, BaiduMapActivity1.class);
//        intent.putExtra("longitude", longitude);
//        intent.putExtra("latitude", latitude);
//        startActivity(intent);
//    }
    @OnClick({R.id.navigate})
    void turnToMap() {
        if (longitude != null && latitude != null) {
            Intent i1 = new Intent();
            //TODO: 手机中有百度地图时,打开百度地图,否则给出提示.后期集成百度地图到手机
            try {
                //uri地址，先写纬度，后写jing经度
                i1.setData(Uri.parse("baidumap://map/geocoder?location=" + latitude + "," + longitude));
                startActivity(i1);
            } catch (Exception e) {
                Toast.makeText(this, "需要手机中安装百度地图", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

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
        option.setScanSpan(80000);

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
        mLocationClient.start();
    }

//      2018.02.10 xmy 点击导航，打开第三方百度app。此处舍弃，后期再考虑用户未安装百度地图的情况下，在集成地图
//    //骑行
//    private void searchBikeLine() {
//        paramBike = new BikeNaviLaunchParam().stPt(start).endPt(end).vehicle(1);
//
//        // 获取导航控制类
//        mNaviBikeHelper = BikeNavigateHelper.getInstance();
//        // 设置导航播报模式
////        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
//
//        // 引擎初始化
//        mNaviBikeHelper.initNaviEngine(this, new IBEngineInitListener() {
//            @Override
//            public void engineInitSuccess() {
//                Log.d(TAG, "引擎初始化成功");
//                routeBikePlanWithParam();
//            }
//
//            @Override
//            public void engineInitFail() {
//                Log.d(TAG, "引擎初始化失败");
//            }
//        });
//    }
//
//    /**
//     * 开始算路(骑行)
//     */
//    private void routeBikePlanWithParam() {
//        paramBike = new BikeNaviLaunchParam().stPt(start).endPt(end);
//        mNaviBikeHelper.routePlanWithParams(paramBike, new IBRoutePlanListener() {
//            @Override
//            public void onRoutePlanStart() {
//                Log.d(TAG, "开始算路");
//            }
//
//            @Override
//            public void onRoutePlanSuccess() {
//                Log.d(TAG, "算路成功,跳转至诱导页面");
//                Intent intent = new Intent();
//                intent.setClass(SearchByDecorationCityTwoActivity.this, BNaviGuideActivity.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onRoutePlanFail(BikeRoutePlanError error) {
//                Log.d(TAG, "算路失败");
//            }
//
//        });
//    }

//      百度定位回调.单独写一个类,定义接口传递数据,此处舍弃
//    class MyLocationListener extends BDAbstractLocationListener {
//
//        private LatLng latLng;
//
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            start = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());//当前位置
//            switch (decorateCity) {
//                case "ZSC10000101001":
//                    double distance = DistanceUtil.getDistance(start, new LatLng(31.938381, 118.666361));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance / MULTIPLE));
//                    String s1 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s1);
//                    break;
//                case "ZSC1000010101":
//                    double distance1 = DistanceUtil.getDistance(start, new LatLng(32.04919, 118.736369));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance1 / MULTIPLE));
//                    String s2 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s2);
//                    break;
//                case "ZSC10000101013":
//                    double distance2 = DistanceUtil.getDistance(start, new LatLng(31.9343, 118.669411));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance2 / MULTIPLE));
//                    String s3 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s3);
//                    break;
//                case "ZSC1000010103":
//                    double distance3 = DistanceUtil.getDistance(start, new LatLng(32.093339, 118.776031));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance3 / MULTIPLE));
//                    String s4 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s4);
//                    break;
//                case "ZSC1000010104":
//                    double distance4 = DistanceUtil.getDistance(start, new LatLng(32.093589, 118.767251));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance4 / MULTIPLE));
//                    String s5 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s5);
//                    break;
//                case "ZSC1000010105":
//                    double distance5 = DistanceUtil.getDistance(start, new LatLng(32.051539, 118.785519));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance5 / MULTIPLE));
//                    String s6 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s6);
//                    break;
//                case "ZSC1000010106":
//                    double distance6 = DistanceUtil.getDistance(start, new LatLng(32.286483, 118.781975));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance6 / MULTIPLE));
//                    String s7 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s7);
//                    break;
//                case "ZSC1000010107":
//                    double distance7 = DistanceUtil.getDistance(start, new LatLng(32.136631, 118.731631));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance7 / MULTIPLE));
//                    String s8 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s8);
//                    break;
//                case "ZSC10000101100":
//                    double distance8 = DistanceUtil.getDistance(start, new LatLng(32.000331, 118.756678));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance8 / 1000));
//                    String s9 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s9);
//                    break;
//                case "ZSC10000101101":
//                    double distance9 = DistanceUtil.getDistance(start, new LatLng(32.012889, 118.761919));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance9 / MULTIPLE));
//                    String s10 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s10);
//                    break;
//                case "ZSC10000101102":
//                    double distance10 = DistanceUtil.getDistance(start, new LatLng(32.332381, 118.848492));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance10 / MULTIPLE));
//                    String s11 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s11);
//                    break;
//                case "ZSC10000101103":
//                    double distance11 = DistanceUtil.getDistance(start, new LatLng(32.00339, 118.758069));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance11 / MULTIPLE));
//                    String s12 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s12);
//                    break;
//                case "ZSC10000101104":
//                    double distance12 = DistanceUtil.getDistance(start, new LatLng(32.9145, 118.651639));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance12 / MULTIPLE));
//                    String s13 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s13);
//                    break;
//                case "ZSC10000101105":
//                    double distance13 = DistanceUtil.getDistance(start, new LatLng(32.094839, 118.764339));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance13 / MULTIPLE));
//                    String s14 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s14);
//                    break;
//                case "ZSC1000010111":
//                    double distance14 = DistanceUtil.getDistance(start, new LatLng(32.019889, 118.82375));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance14 / MULTIPLE));
//                    String s15 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s15);
//                    break;
//                case "ZSC1000010112":
//                    double distance15 = DistanceUtil.getDistance(start, new LatLng(32.019139, 118.8205));
//                    s = Double.parseDouble(AppUtils.parseDoubleDistance(distance15 / MULTIPLE));
//                    String s16 = String.valueOf(SearchByDecorationCityTwoActivity.this.s);
//                    baidudistance.setText(s16);
//                    break;
//            }
//        }
//
//        //这个接口主要返回连接网络的类型
//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//        }
//    }

    /********************************************分割线****************************************/



    /**
     * 跳转详情
     */
    @OnClick(R.id.decoration_city_linear)
    void skipCity() {
        Intent intent = new Intent(context, PublishDecorationCityHeadlinearActivity.class);
        intent.putExtra("three_dir_id", decorateCity);
        startActivity(intent);
    }

    private void itemOnclick() {
        decorationListviewLeft.setOnItemClickListener(mLeftListOnItemClick);
        searchDecortionTwoAdapter.notifyDataSetChanged();
    }


    AdapterView.OnItemClickListener mLeftListOnItemClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            searchDecortionTwoAdapter.setSelectItem(arg2);
            searchDecortionTwoAdapter.notifyDataSetInvalidated();
            searchDecortionTwoAdapter.notifyDataSetChanged();
            searchCustomAdapter.notifyDataSetChanged();
            two_dir_id = listLeft.get(arg2).getTwo_dir_id();
            page = 1;
            getUser();
        }
    };

    private boolean isHaveMore() {
        if (page >= total) {
            page = total;

            return false;
        }
        return true;
    }

    private void setRefresh() {
        if (isHaveMore()) {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("正在加载...");// 刷新时
            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
            page++;

            getUser();
        } else {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("已经到底");// 刷新时
            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
            scrollview.onRefreshComplete();
        }
    }

    @OnClick(R.id.app_search_tv)
    public void searchByInput(TextView view) {
        onPullDownToRefresh(scrollview);
    }


    private void getUser() {
        SearchShopUser searchShopUser = new SearchShopUser();
        three_dir_id = getIntent().getStringExtra("three_dir_id");
        searchShopUser.setDecorative_id(three_dir_id);
        searchShopUser.setTwo_dir_id(two_dir_id);
        searchShopUser.setOne_dir_id(one_dir_id);
        searchShopUser.setTp(1);
        searchShopUser.setPage(page);
        getPresenter().getUserData(searchShopUser);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page = 1;
        getUser();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        setRefresh();
    }

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(this);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }

    //装饰城中的活动
    public void Action(View view) {
        Intent intent = new Intent(SearchByDecorationCityTwoActivity.this, DecorationTwoShopActivity.class);
        intent.putExtra("three_dir_id", decorateCity);
        startActivity(intent);
    }

    //装饰城中的平面图
    public void plane(View view) {
        Intent intent = new Intent(SearchByDecorationCityTwoActivity.this, DecorationTwoPingActiviy.class);
        intent.putExtra("three_dir_id", decorateCity);
        startActivity(intent);
    }

    //装饰城中的精品馆
    public void boutique(View view) {
        Intent intent = new Intent(SearchByDecorationCityTwoActivity.this, DecorationTwoBoutiqueActiviy.class);
        intent.putExtra("three_dir_id", decorateCity);
        startActivity(intent);
    }

    /**
     * 自己定义的接口,回调百度定位的经纬度
     *
     * @param addr      回调的相信地址
     * @param longitude 回调的经度
     * @param latitude  回调的纬度
     */
    @Override
    public void getAddr(String addr, double longitude, double latitude) {
        //传入适配器,更改与商户的距离
        searchCustomAdapter.setlonlat(longitude, latitude);
        Log.i("LocationListener2", "获得结果:longitude:" + longitude + "    latitude:" + latitude);

        //设置当前距离装饰城距离
        baidudistance.setText(DistanceLonLat.getDistance(longitude, latitude, Double.parseDouble(this.longitude), Double.parseDouble(this.latitude)));

    }
}
