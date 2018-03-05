package com.sky.app.ui.activity.seller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.sky.app.R;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.ProductCategory;
import com.sky.app.bean.ProductCategoryIn;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.ProductResponse;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.bean.Seller;
import com.sky.app.bean.ShopProductDetail;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.model.MyLocationListener;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.presenter.ShopCenterPresenter;
import com.sky.app.ui.activity.openIM.OpenIMLoginActivity;
import com.sky.app.ui.activity.product.ProductIntroduceActivity;
import com.sky.app.ui.adapter.ShopCenterPagerAdapter;
import com.sky.app.ui.custom.CategoryMenuDialog;
import com.sky.app.utils.DistanceLonLat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.sky.app.R.id.app_edit_content;
import static com.sky.app.R.id.app_search_tv;

/**
 * 商铺个人中心
 */
public class ShopCenterActivity extends BaseViewActivity<ShopContract.IShopCenterPresenter>
        implements ShopContract.IShopCenterView, ShopContract.ICollectionView, MyLocationListener.AddrListener {

    @BindView(R.id.app_search_toolbar)
    Toolbar toolbar;
    @BindView(app_edit_content)
    EditText editContent;

    @BindView(R.id.app_shop_list)
    ViewPager viewPager;
    @BindView(R.id.app_category_list)
    TabLayout tabLayout;

    @BindView(R.id.shop_background)
    ImageView shopBackground;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    @BindView(R.id.shop_collect)
    Button shopCollect;
    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.app_distance)
    TextView appDistance;
    @BindView(R.id.navigate)
    LinearLayout navigate;
    @BindView(R.id.shop_category)
    TextView shopCategory;
    @BindView(R.id.shop_introduce)
    TextView shopIntroduce;
    @BindView(R.id.shop_contact)
    TextView shopContact;
    @BindView(R.id.shop_phone)
    TextView shopPhone;



    private ShopCenterPagerAdapter pagerAdapter;
    private ShopContract.ICollectionPresenter iCollectionPresenter;
    private List<ProductCategory> productCategories;
    private SearchProductRequest searchProductRequest = new SearchProductRequest();

    private String seller_id;
    private ProductIntroduceOut productIntroduceOut;
    /**
     * 百度定位经纬度
     */
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
    private String longitude;
    private String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_shop_center);
        ButterKnife.bind(this);
        getlonlat();
    }


    @Override
    protected ShopContract.IShopCenterPresenter presenter() {
        iCollectionPresenter = new CollectPresenter(context, this);
        return new ShopCenterPresenter(context, this);
    }

    @Override
    protected void init() {
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        editContent.setHint(R.string.app_search_shop_string);
        this.seller_id = getIntent().getStringExtra("seller_id");

    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //请求商品分类
        ProductCategoryIn productCategoryIn = new ProductCategoryIn();
        productCategoryIn.setLevel("1");
        productCategoryIn.setUser_id(seller_id);
        getPresenter().requestProductCategory(productCategoryIn);

        //请求店铺信息
        ProductIntroduceIn productIntroduceIn = new ProductIntroduceIn();
        productIntroduceIn.setUser_id(seller_id);
        getPresenter().requestDescData(productIntroduceIn);

    }


    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    private int tag = -1;

    @OnClick(R.id.shop_collect)
    void clickCollect() {
        if (null == productIntroduceOut) {
            T.showShort(context, "数据错误");
            return;
        }
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("2");
        collectPubIn.setCollect_value(seller_id);
        if ("收藏店铺".equals(shopCollect.getText())) {
            tag = 1;
            iCollectionPresenter.requestCollect(collectPubIn);
        } else {
            tag = 2;
            iCollectionPresenter.cancelCollect(collectPubIn);
        }
    }

    @OnClick(R.id.shop_share)
    void cilckShare(){
        showShare();
    }

    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
        if (tag == 1) {
            shopCollect.setText("已收藏");
        } else if (tag == 2) {
            shopCollect.setText("收藏店铺");
        }
    }

    @Override
    public void showCollectError(String error) {
        T.showShort(context, error);
        if (tag == 1) {
            shopCollect.setText("收藏店铺");
        } else if (tag == 2) {
            shopCollect.setText("已收藏");
        }
    }

    @Deprecated
    @Override
    public void showHomeResponse(List<ShopProductDetail> list) {

    }

    @Deprecated
    @Override
    public void getRefreshData(List<ProductResponse> list) {

    }

    @Deprecated
    @Override
    public void getLoadMoreData(List<ProductResponse> list) {

    }

    @Override
    public void responseProductCategory(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
        Log.i("123productCategories",productCategories.toString());
    }

    @Override
    public void responseDescData(ProductIntroduceOut productIntroduceOut) {
        this.productIntroduceOut = productIntroduceOut;

        Log.i("123productIntroduceOut",productIntroduceOut.toString());

        latitude = productIntroduceOut.getLatitude();
        longitude = productIntroduceOut.getLongitude();
        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPersonal_pic(),
                shopBackground, R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
//        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(),
//                shopImg, R.mipmap.app_default_icon, R.mipmap.app_default_icon);

//        appName.setText(productIntroduceOut.getNick_name());
        if (1 == productIntroduceOut.getIs_collect()) {
            shopCollect.setText("已收藏");
        } else {
            shopCollect.setText("收藏店铺");
        }

        Seller seller = new Seller();
        seller.setUser_id(seller_id);
        pagerAdapter = new ShopCenterPagerAdapter(getSupportFragmentManager(), seller, searchProductRequest);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        addressTv.setText(productIntroduceOut.getAddress());

    }

//    @Override
//    public void setLonglat(UserGPS userGPS) {
//        latitude = userGPS.getLatitude();
//        longitude = userGPS.getLongitude();
//        addressTv.setText(productIntroduceOut.getAddress());
//        appDistance.setText(DistanceLonLat.getDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), Double.parseDouble(userGPS.getLongitude()), Double.parseDouble(userGPS.getLatitude())));

//    }

    @OnClick(R.id.shop_category)
    void clickCategory() {
        if (null == productCategories || productCategories.isEmpty()) {
            T.showShort(context, "还没有商品分类");
            return;
        }
        new CategoryMenuDialog(context, productCategories, new CategoryMenuDialog.CallBack() {
            @Override
            public void data(String one, String two) {
                searchProductRequest.setOne_dir_id(one);
                searchProductRequest.setTwo_dir_id(two);
                searchProductRequest.setProduct_name("");
                sendData(searchProductRequest);
            }
        }).show();
    }

    @OnClick(R.id.shop_introduce)
    void clickIntroduce() {
        Intent i = new Intent(context, ProductIntroduceActivity.class);
        i.putExtra("seller_id", seller_id);
        startActivity(i);
    }

    @OnClick(R.id.shop_contact)
//联系商家
    void clickContact() {
//        getLogin();
        SharedPreferences sp = getSharedPreferences("seller_id", Context.MODE_PRIVATE);
        sp.edit().putString("seller_id", seller_id).commit();
        Intent intent = new Intent(context, OpenIMLoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.shop_phone)
    void call() {
        if (null == productIntroduceOut || TextUtils.isEmpty(productIntroduceOut.getMobile())) {
            T.showShort(context, "找不到号码");
            return;
        }
        AppUtils.callPhone(context, productIntroduceOut.getMobile());
    }

    @OnClick(app_search_tv)
    void clickSearch() {
        searchProductRequest.setProduct_name(editContent.getText().toString().trim());
        searchProductRequest.setTwo_dir_id("");
        searchProductRequest.setOne_dir_id("");
        sendData(searchProductRequest);
    }

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

    /**
     * 发送广播数据
     */
    private void sendData(SearchProductRequest searchProductRequest) {
        Intent intent = new Intent();
        intent.setAction("com.sky.app.refresh");
        intent.putExtra("product_name", searchProductRequest.getProduct_name());
        intent.putExtra("one_dir_id", searchProductRequest.getOne_dir_id());
        intent.putExtra("two_dir_id", searchProductRequest.getTwo_dir_id());
        sendBroadcast(intent);
        tabLayout.getTabAt(1).select();
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
//        searchCustomAdapter.setlonlat(longitude, latitude);
//        Log.i("LocationListener2", "获得结果:longitude:" + longitude + "    latitude:" + latitude);

        //设置当前距离装饰城距离
        appDistance.setText(DistanceLonLat.getDistance(longitude, latitude, Double.parseDouble(this.longitude), Double.parseDouble(this.latitude)));

    }

    //这是分享函数，哪里需要分享调用此函数即可，
    //参数可自行设置
    // TODO: 2018/3/5 share
    private void showShare() {
//        ShareSDK
        OnekeyShare oks = new OnekeyShare();
//        关闭sso授权
        oks.disableSSOWhenAuthorize();
//         title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用,// title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle(productIntroduceOut.getNick_name());
//         text是分享文本，所有平台都需要这个字段
        oks.setText(productIntroduceOut.getMain_business_desc());
//         url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(Constants.Url.BASE_URL+"h5_product/un/get_user_home_products?user_id="+productIntroduceOut.getUser_id());
//         site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("51工匠");
//         siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(Constants.Url.BASE_URL+"h5_product/un/get_user_home_products?user_id="+productIntroduceOut.getUser_id());
//        网络图片的url：所有平台
        oks.setImageUrl(productIntroduceOut.getPic_url());//网络图片rul
//         Url：仅在QQ空间使用
        oks.setTitleUrl(Constants.Url.BASE_URL+"h5_product/un/get_user_home_products?user_id="+productIntroduceOut.getUser_id());  //网友点进链接后，可以看到分享的详情
//         启动分享GUI
        oks.show(this);
    }
}
