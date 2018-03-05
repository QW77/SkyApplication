package com.sky.shop.ui.activity.shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.MyLocationListener;
import com.sky.shop.presenter.CardPresenter;
import com.sky.shop.presenter.activity.MineShopCenterPresenter;
import com.sky.shop.ui.activity.RecordWebActivity;
import com.sky.shop.ui.activity.user.AccountActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 名片
 */
public class CardActivity extends BaseViewActivity<SellerContract.ICardPresenter>
        implements SellerContract.ICardView, ShopContract.IMineShopCenterView,MyLocationListener.AddrListener {

    @BindView(R.id.shop_status)
    TextView shopStatus;
    private ProductIntroduceOut productIntroduceOut;

    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_user_name)
    TextView userName;
    @BindView(R.id.app_time)
    TextView appTime;
    @BindView(R.id.app_main_sell)
    TextView mainSell;
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.weixin)
    TextView weixin;
    private long exitTime;

    ShopContract.IMineShopCenterPresenter iMineShopCenterPresenter;

    @BindView(R.id.app_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.app_picture)
    ImageView appPicture;
    @BindView(R.id.app_sider_title)
    TextView siderTitle;

    @BindView(R.id.user_address)
    TextView userAddress;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    String status_code = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_card);
        ButterKnife.bind(this);
    }

    @Override
    protected SellerContract.ICardPresenter presenter() {
        iMineShopCenterPresenter = new MineShopCenterPresenter(context, this);
        return new CardPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected void init() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        upDataStatus(status_code);
        getLocation();
    }

    public void upDataStatus(String status_code) {
        switch (status_code) {
            case "0":
                shopStatus.setText("空闲");
                setStatus("0");
                break;
            case "1":
                shopStatus.setText("忙碌");
                setStatus("1");
                break;
        }
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void responseCardInfo(ProductIntroduceOut productIntroduceOut) {
        this.productIntroduceOut = productIntroduceOut;

        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(), appIcon,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        userName.setText(productIntroduceOut.getNick_name());
        appTime.setText("从业时间：" + (TextUtils.isEmpty(productIntroduceOut.getWorktime()) ? "" :
                productIntroduceOut.getWorktime()));
        mainSell.setText("主营业务：" + (TextUtils.isEmpty(productIntroduceOut.getMain_business_desc()) ? "" :
                productIntroduceOut.getMain_business_desc()));

        qq.setText(productIntroduceOut.getQq());
        weixin.setText(productIntroduceOut.getWeixin());
    }

    @Deprecated
    @Override
    public void responseAddCard(String msg) {

    }

    @Override
    public void setStatus(String status_code) {
        getPresenter().setStatus(status_code);
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

    @OnClick(R.id.status_update)
    void upstatus() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this);
        builder.setTitle("请选择工作状态");
        final String[] cities = {"空闲", "忙碌"};
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CardActivity.this, "设置状态为：" + cities[which], Toast.LENGTH_SHORT).show();
                status_code = which + "";
                upDataStatus(status_code);
            }
        });
        builder.show();
    }

    @OnClick(R.id.card_edt)
    void edit() {
        startActivity(new Intent(context, CardEditActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        iMineShopCenterPresenter.queryUserInfo();

        //请求个人主页信息
        getPresenter().requestCardInfo();
    }

    /**
     * 关闭drawer
     */
    private boolean closeDrawer() {
        if (null != drawerLayout && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.showShort(context, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                BaseAppManager.getInstance().AppExit(context);
            }
        }
    }

    @Override
    public void responseUserInfo(UserBean userBean) {
        //初始化数据
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(),
                appPicture, R.mipmap.app_default_photo, R.mipmap.app_default_icon);
        siderTitle.setText(UserBean.getInstance().getUserCache().getNick_name());
    }

    @OnClick(R.id.app_shop_center)
    void clickShopCenter() {
        startActivity(new Intent(context, ShopDataActivity.class));
    }

    @OnClick(R.id.app_account_center)
    void clickAccountCenter() {
        closeDrawer();
        startActivity(new Intent(context, AccountActivity.class));
    }

    @OnClick(R.id.app_browser_record)
    void clickBrowserRecord() {
        closeDrawer();
        Intent i = new Intent(context, RecordWebActivity.class);
        UserBean.getInstance().getCacheUid();
        i.putExtra("url", "http://api.app.51craftsman.com/h5_shop/un/browse_record?user_id=" +
                UserBean.getInstance().getCacheUid());
        startActivity(i);
    }

    @OnClick(R.id.app_safe_center)
    void clickSafeCenter() {
        closeDrawer();
        startActivity(new Intent(context, SafeCenterActivity.class));
    }

    @OnClick(R.id.app_shop_icon)
    void clickLeft() {
        if (!closeDrawer()) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @OnClick(R.id.now_address)
    void clickUpdataAddress(){
        getLocation();
    }

    private void getLocation() {

        //声明LocationClient类
        mLocationClient = new LocationClient(this.getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        myListener.setAddrListener(this);
        LocationClientOption option = mLocationClient.getLocOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，设置定位模式，默认高精度


        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(1000);
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
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        Log.i("LocationListener", "start");
        mLocationClient.start();

    }

    /**
     * 百度地图定位接口回调信息
     *
     * @param addr      定位详细地址
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Override
    public void getAddr(String addr, double longitude, double latitude) {

            userAddress.setText(addr);
            mLocationClient.stop();
            getPresenter().postlonlat(longitude, latitude);
    }
}
