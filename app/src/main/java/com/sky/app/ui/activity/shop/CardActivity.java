package com.sky.app.ui.activity.shop;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.sky.app.R;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.CommentResponse;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.StatusUPdata;
import com.sky.app.contract.SellerContract;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.AllCommentPresenter;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.presenter.shop.CardPresenter;
import com.sky.app.ui.activity.baidumap.BNaviGuideActivity;
import com.sky.app.ui.activity.baidumap.BaiduMapActivity;
import com.sky.app.ui.activity.order.CommentActivity;
import com.sky.app.ui.adapter.AllCommentAdaptor;
//import com.sky.app.utils.ShareUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 个人名片
 */
public class CardActivity extends BaseViewActivity<SellerContract.ICardPresenter>
        implements SellerContract.ICardView, ShopContract.ICollectionView,
        ShopContract.IAllCommentView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_comment_list)
    LuRecyclerView mRecyclerView;

    AllCommentAdaptor allCommentAdaptor;
    List<CommentResponse> commentResponses = new ArrayList<>();
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.publish_comment)
    ImageView publishComment;
    @BindView(R.id.app_call_phone)
    TextView appCallPhone;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private CommentRequest commentRequest = new CommentRequest();
    private String seller_id;
    private ProductIntroduceOut productIntroduceOut;

    @BindView(R.id.comment_type)
    RadioGroup radioGroup;

    private ShopContract.ICollectionPresenter iCollectionPresenter;
    private ShopContract.IAllCommentPresenter iAllCommentPresenter;

    @BindView(R.id.app_collect)
    TextView collect;

    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_user_name)
    TextView userName;
    @BindView(R.id.app_time)
    TextView appTime;
    @BindView(R.id.app_main_sell)
    TextView mainSell;
    @BindView(R.id.shop_collect)
    Button collectBtn;

    @BindView(R.id.all)
    RadioButton allBtn;
    @BindView(R.id.has_pic)
    RadioButton picBtn;

    private BaiduMap mBaiduMap;
    private MapView mMapView;

    LatLng start = null;
    LatLng end = null;
    //
//    @BindView(R.id.qq)
//    TextView qq;
//    @BindView(R.id.weixin)
//    TextView weixin;
    //    BaiduNaviManager mNaviManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.app_card);
        ButterKnife.bind(this);
    }

    @Override
    protected SellerContract.ICardPresenter presenter() {
        iCollectionPresenter = new CollectPresenter(context, this);
        iAllCommentPresenter = new AllCommentPresenter(context, this);
        return new CardPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.app_share:
//                        showShare();

                        break;
                }
                return false;
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 1),
                AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        allCommentAdaptor = new AllCommentAdaptor(context, commentResponses);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(allCommentAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (iAllCommentPresenter.hasMore()) {
                    loadData();
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent, android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部加载完", "网络不给力啊，点击再试一次吧");

        onRefresh();

        //请求个人主页信息
        ProductIntroduceIn productIntroduceIn = new ProductIntroduceIn();
        productIntroduceIn.setUser_id(seller_id);
        getPresenter().requestCardInfo(productIntroduceIn);

        //查询评论数量
        searchCommentNum();
    }

    @Override
    protected void init() {
        //导航
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);//注册监听函数
        initLocationClient();
        mLocationClient.start();
        //此处填写商店位置坐标
        end = new LatLng(31.938381, 118.666361);


        title.setText(R.string.app_card_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        toolbar.inflateMenu(R.menu.app_share_menu);

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        seller_id = getIntent().getStringExtra("seller_id");
        commentRequest.setType(1);//商户编号
        commentRequest.setValue(seller_id);
        radioGroup.check(R.id.all);
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
                intent.setClass(CardActivity.this, BNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError error) {
                Log.d(TAG, "算路失败");
            }

        });
    }
    class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            start = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());//当前位


        }

        //这个接口主要返回连接网络的类型
        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }


    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
        switch (tag) {
            case 1:
                collectBtn.setText("收藏");
                break;
            case 2:
                collectBtn.setText("已收藏");
                break;
            case 3:
                collect.setText("收藏");
                AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_star_icon, 2);
                break;
            case 4:
                collect.setText("已收藏");
                AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_selected_star_icon, 2);
                break;
        }
    }

    @Override
    public void showCollectError(String error) {
        T.showShort(context, error);
        switch (tag) {
            case 1:
                collectBtn.setText("已收藏");
                break;
            case 2:
                collectBtn.setText("收藏");
                break;
            case 3:
                collect.setText("已收藏");
                AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_selected_star_icon, 2);
                break;
            case 4:
                collect.setText("收藏");
                AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_star_icon, 2);
                break;
        }
    }

    @Override
    public void getRefreshData(List<CommentResponse> list, int tag, int num) {
        switch (tag) {
            case 1:
                allBtn.setText("全部(" + num + ")");
                break;
            case 2:
                picBtn.setText("有图(" + num + ")");
                break;
        }

        allCommentAdaptor.add(list);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<CommentResponse> list, int tag, int num) {
        switch (tag) {
            case 1:
                allBtn.setText("全部(" + num + ")");
                break;
            case 2:
                picBtn.setText("有图(" + num + ")");
                break;
        }

        allCommentAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        refresh();
    }

    @OnClick({R.id.all, R.id.has_pic})
    void clickSelectPic(RadioButton radioButton) {
        switch (radioButton.getId()) {
            case R.id.all:
                commentRequest.setPic_type("");
                onRefresh();
                break;
            case R.id.has_pic:
                commentRequest.setPic_type("1");
                onRefresh();
                break;
        }
    }

    @OnClick({R.id.navigate})
    void turnToMap() {
//        if (BaiduNaviManager.isNaviInited()) {
//            routeplanToNavi(BNRoutePlanNode.CoordinateType.BD09LL);
//        }
//        Intent intent=new Intent(this, BaiduMapActivity.class);
//        startActivity(intent);
        if (start != null && end != null) {
            searchBikeLine();
        }
    }


    @OnClick(R.id.app_call_phone)
    void call() {
        if (null == productIntroduceOut || TextUtils.isEmpty(productIntroduceOut.getMobile())) {
            T.showShort(context, "找不到号码");
            return;
        }
        AppUtils.callPhone(context, productIntroduceOut.getMobile());
    }

    private int tag = -1;

    @OnClick(R.id.app_collect)
    void clickCollect() {
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("2");
        collectPubIn.setCollect_value(seller_id);
        if ("已收藏".equals(collect.getText().toString())) {
            iCollectionPresenter.cancelCollect(collectPubIn);
            tag = 3;
        } else {
            tag = 4;
            iCollectionPresenter.requestCollect(collectPubIn);
        }
    }

    @OnClick(R.id.shop_collect)
    void collect() {
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("2");
        collectPubIn.setCollect_value(seller_id);
        if ("已收藏".equals(collectBtn.getText().toString())) {
            tag = 1;
            iCollectionPresenter.cancelCollect(collectPubIn);
        } else {
            tag = 2;
            iCollectionPresenter.requestCollect(collectPubIn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        ShareUtils.onActivityResult(this, requestCode, resultCode, data);
//        showShare();

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

        if (1 == productIntroduceOut.getIs_collect()) {
            collect.setText("已收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_selected_star_icon, 2);
            collectBtn.setText("已收藏");
        } else {
            collect.setText("收藏");
            collectBtn.setText("收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_star_icon, 2);
        }
        getPresenter().updataStatus(productIntroduceOut.getUser_id());

//        qq.setText(productIntroduceOut.getQq());
//        weixin.setText(productIntroduceOut.getWeixin());
    }

    @Override
    public void updataStatusSuccess(StatusUPdata statusUPdata) {
        switch (statusUPdata.getStatus_code()) {
            case "0":
                textView7.setText("空闲");
                textView7.setTextColor(this.getResources().getColor(R.color.green));
                break;
            case "1":
                textView7.setText("忙碌");
                textView7.setTextColor(this.getResources().getColor(R.color.red));
                break;
        }

    }

    /**
     * 刷新数据
     */
    private void refresh() {
        if (TextUtils.isEmpty(commentRequest.getPic_type())) {
            iAllCommentPresenter.loadData(commentRequest, 1);
        } else {
            iAllCommentPresenter.loadData(commentRequest, 2);
        }
    }

    /**
     * 加载数据
     */
    private void loadData() {
        if (TextUtils.isEmpty(commentRequest.getPic_type())) {
            iAllCommentPresenter.loadMore(commentRequest, 1);
        } else {
            iAllCommentPresenter.loadMore(commentRequest, 2);
        }
    }

    /**
     * 搜索评论数量
     */
    private void searchCommentNum() {
        iAllCommentPresenter.requestAllCommentNum(commentRequest);
    }

    @Override
    public void responseAllCommentNum(int all) {
        allBtn.setText("全部(" + all + ")");
    }

    @Override
    public void responsePicNum(int pic) {
        picBtn.setText("有图(" + pic + ")");
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

    @OnClick(R.id.publish_comment)
    void skipComment() {
        Intent r = new Intent(context, CommentActivity.class);
        r.putExtra("image", productIntroduceOut.getPic_url());
        r.putExtra("type", 1);//0 订单 1 商户
        r.putExtra("about_user_id", seller_id);
        startActivity(r);
    }

//    //这是分享函数，哪里需要分享调用此函数即可，
//    //参数可自行设置
//    private void showShare() {
////        ShareSDK
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
////        oks.setNotification(R.drawable.ic_launcher,getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用,// title标题：微信、QQ（新浪微博不需要标题）
//        oks.setTitle(productDeatilResponse.getProduct_name());
//
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(productDeatilResponse.getProduct_key_words());
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////        oks.setImagePath(Environment.getExternalStorageDirectory().getPath()+"/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(Constants.Url.BASE_URL+"h5_product/un/product_detail?product_id="+productDeatilResponse.getProduct_id()+"&user_id="+productDeatilResponse.getUser_id());
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
////        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("51工匠");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(Constants.Url.BASE_URL+"h5_product/un/product_detail?product_id="+productDeatilResponse.getProduct_id()+"&user_id="+productDeatilResponse.getUser_id());
//        //网络图片的url：所有平台
//        oks.setImageUrl(productDeatilResponse.getProduct_image_url());//网络图片rul
//        // Url：仅在QQ空间使用
//        oks.setTitleUrl(Constants.Url.BASE_URL+"h5_product/un/product_detail?product_id="+productDeatilResponse.getProduct_id()+"&user_id="+productDeatilResponse.getUser_id());  //网友点进链接后，可以看到分享的详情
////        oks.setTitleUrl("http://api.app.51craftsman.com/cc.jsp?type=decoration&id=180126059586&user_id=170922011253&machine_id=180126059586");  //网友点进链接后，可以看到分享的详情
//        // 启动分享GUI
//        oks.show(this);
//    }
}
