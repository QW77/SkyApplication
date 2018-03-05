package com.sky.app.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.sky.app.R;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.bean.UserBeanList1;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.model.MyLocationListener;
import com.sky.app.presenter.SearchSecondPresent;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.utils.DistanceLonLat;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchShopActivity extends BaseViewActivity<UserContract.ISearchSecondPresent>
        implements UserContract.ISearchSecond,MyLocationListener.AddrListener {


    @BindView(R.id.app_edit_content)
    EditText editText;
    @BindView(R.id.app_search_toolbar)
    Toolbar appSearchToolbar;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    @BindView(R.id.shop_listview_right)
    ListView listview;
    private String ids = "";
    private GoodShopMoreAdapter goodShopMoreAdapter;
    private int page = 1;
    /**
     * 百度定位经纬度
     */
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
    private BitmapDescriptor mCurrentMarker;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_shop_activity);
        getPresenter().getUserDataMore(1);
        getlonlat();
    }

    @Override
    public void success(CategoryList categoryList) {
    }

    @Override
    public void userDataSuccess(final UserBeanList userBeanList) {

    }

    @Override
    public void userDataSuccess1(UserBeanList1 userBeanList) {

    }

    //展示更多好店
    @Override
    public void showMoreGoodShop(final List<GoodShop.ListBean> goodList) {
        hideProgress();
        goodShopMoreAdapter = new GoodShopMoreAdapter(goodList, context);
        listview.setAdapter(goodShopMoreAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String user_id = goodList.get(i).getUser_id();
                String seller_id = user_id;
                Intent intent = new Intent(context, ShopCenterActivity.class);
                intent.putExtra("seller_id", seller_id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected UserContract.ISearchSecondPresent presenter() {
        return new SearchSecondPresent(this, this);
    }

    @Override
    protected void init() {
        appSearchToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

    }

    @Override
    protected void onDestoryActivity() {
    }

    @Override
    public void initViewsAndEvents() {
        appSearchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @OnClick(R.id.app_search_tv)
    public void searchByInput() {
        getUser();
    }

    /**
     * 搜索
     */
    private void getUser() {
        SearchUser searchUser = new SearchUser();
        searchUser.setNick_name(editText.getText().toString().trim());//通过用户输入的关键字
        searchUser.setPage(page);
        searchUser.setTwo_dir_id(ids);
        searchUser.setTp(0);
        getPresenter().getUserData(searchUser);
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
        goodShopMoreAdapter.setlonlat(longitude, latitude);
        Log.i("LocationListener2", "获得结果:longitude:" + longitude + "    latitude:" + latitude);

        //设置当前距离装饰城距离
//        baidudistance.setText(DistanceLonLat.getDistance(longitude, latitude, Double.parseDouble(this.longitude), Double.parseDouble(this.latitude)));

    }
}
