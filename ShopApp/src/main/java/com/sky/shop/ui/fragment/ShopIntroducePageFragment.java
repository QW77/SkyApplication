package com.sky.shop.ui.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.contract.ImageContract;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.MyLocationListener;
import com.sky.shop.presenter.ProductIntroducePresenter;
import com.sky.shop.presenter.UploadImagePresenter;
import com.sky.shop.utils.AppDialogUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商铺简介
 * Created by Administrator on 2017/2/30.
 */
public class ShopIntroducePageFragment extends BaseViewFragment<ShopContract.IProductIntroducePresenter>
        implements ShopContract.IProductIntroduceView, ImageContract.IUploadView, MyLocationListener.AddrListener {

    @BindView(R.id.app_image)
    ImageView image;
    @BindView(R.id.app_seller_name)
    EditText sellerName;
    @BindView(R.id.app_collect_num)
    TextView collectNum;
    @BindView(R.id.app_desc)
    EditText desc;
    @BindView(R.id.app_content)
    EditText content;
    @BindView(R.id.app_addr)
    EditText addr;
    @BindView(R.id.app_mobile)
    EditText mobile;
    @BindView(R.id.app_qq)
    EditText qq;
    @BindView(R.id.app_weixin)
    EditText weixin;
    @BindView(R.id.editAndFinish)
    TextView editAndFinish;

    private ImageContract.IUploadPresenter iUploadPresenter;
    private ProductIntroduceOut productIntroduceOut = new ProductIntroduceOut();

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void init() {
        isValid(false);

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sky.shop.image");
        filter.setPriority(Integer.MAX_VALUE);
        getActivity().registerReceiver(myReceiver, filter);
    }

    private void getLocation() {

        //声明LocationClient类
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
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

    @Override
    protected void initViewsAndEvents() {
        onRefresh();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_product_introduce;
    }

    @Override
    protected void onDestoryFragment() {


    }

    @Override
    protected ShopContract.IProductIntroducePresenter presenter() {
        iUploadPresenter = new UploadImagePresenter(context, this);
        return new ProductIntroducePresenter(context, this);
    }

    @Override
    public void showError(String error) {
        super.showError(error);

            T.showShort(context,error);

    }

    @Override
    public void showSuccess(ProductIntroduceOut out) {
        ImageHelper.getInstance().displayDefinedImage(out.getPersonal_pic(), image,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        sellerName.setText(out.getNick_name());
        collectNum.setText(out.getNum_collect() + "");
        desc.setText(out.getShoper_desc());
        content.setText(out.getMain_business_desc());
        addr.setText(out.getAddress());
        mobile.setText(out.getMobile());
        qq.setText(out.getQq());
        weixin.setText(out.getWeixin());
    }

    @Override
    public void responseShopInfo(String msg) {
        editAndFinish.setText("编辑");
        isValid(false);
        T.showShort(context, msg);
        onRefresh();
    }

    @OnClick(R.id.app_image)
    void clickIcon() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.Permission.CAMERA_PERMISSION);
        } else {
            AppDialogUtils.showTakePhotoView(getActivity());
        }
    }

    @Override
    public void getImageUrl(String url) {
        productIntroduceOut.setPersonal_pic(url);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(myReceiver);
    }

    /**
     * 接口广播
     */
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.sky.shop.image".equals(intent.getAction())) {
                String icon = intent.getStringExtra("image");
                L.msg("返回上传图片=>" + icon);
                if (!TextUtils.isEmpty(icon)) {
                    ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(icon)).toString(), image,
                            R.mipmap.app_default_icon, R.mipmap.app_default_icon);
                    iUploadPresenter.uploadFile(icon);
                }
            }
        }

    };

    /**
     * 刷新
     */
    private void onRefresh() {
        getPresenter().requestDescData();
    }

    @OnClick(R.id.editAndFinish)
    void clickEditAndFinish() {
        if ("完成".equals(editAndFinish.getText().toString())) {
            productIntroduceOut.setNick_name(sellerName.getText().toString().trim());
            productIntroduceOut.setShoper_desc(desc.getText().toString().trim());
            productIntroduceOut.setMain_business_desc(content.getText().toString().trim());
            productIntroduceOut.setAddress(addr.getText().toString().trim());
            productIntroduceOut.setMobile(mobile.getText().toString().trim());
            productIntroduceOut.setQq(qq.getText().toString().trim());
            productIntroduceOut.setWeixin(weixin.getText().toString().trim());
            getPresenter().requestShopIntroduce(productIntroduceOut);
        } else {
            editAndFinish.setText("完成");
            isValid(true);
            getLocation();
        }
    }

    /**
     * 失效
     */
    private void isValid(boolean tag) {
        image.setEnabled(tag);
        sellerName.setEnabled(tag);
        desc.setEnabled(tag);
        content.setEnabled(tag);
        addr.setEnabled(tag);
        mobile.setEnabled(tag);
        qq.setEnabled(tag);
        weixin.setEnabled(tag);
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

        if (editAndFinish.getText().equals("完成")) {
            this.addr.setText(addr);
            mLocationClient.stop();
            getPresenter().postlonlat(longitude, latitude);
        }
    }
}