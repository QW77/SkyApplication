package com.sky.app.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.sky.app.R;
import com.sky.app.bean.Category;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.bean.UserBeanList1;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.model.MyLocationListener;
import com.sky.app.presenter.SearchSecondPresent;
import com.sky.app.ui.activity.seller.SellerCenterActivity;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.ui.activity.shop.CardActivity;
import com.sky.app.ui.adapter.SearchCustomAdapter;
import com.sky.app.ui.adapter.SeconCategoryViewPagerAdapter;
import com.sky.app.ui.adapter.SecondCategoryGridviewAdapter1234;
import com.sky.app.ui.custom.AutoHeightGridView;
import com.sky.app.ui.custom.AutoHeightListView;
import com.sky.app.ui.custom.AutoHeightViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchSecondActivity1234 extends BaseViewActivity<UserContract.ISearchSecondPresent>
        implements UserContract.ISearchSecond, MyLocationListener.AddrListener {

    //    @BindView(R.id.dot_container)
//    LinearLayout dotContainer;
    @BindView(R.id.second_category_viewpager)
    AutoHeightViewPager autoHeightViewPager;
    @BindView(R.id.app_edit_content)
    EditText editText;
    @BindView(R.id.app_search_toolbar)
    Toolbar appSearchToolbar;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    @BindView(R.id.listview)
    AutoHeightListView listview;
    @BindView(R.id.scrollview)
    PullToRefreshScrollView scrollview;
    private int page = 1;
    private int total = -1;
    private String one_dir_id;
    private SearchCustomAdapter searchCustomAdapter;
    private UserBeanList userBeanList = new UserBeanList();
    /**
     * 百度定位经纬度
     */
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_second_activity1234);
        getlonlat();
    }

    @Override
    public void success(CategoryList categoryList) {
        //填充分类
        setFirstCategory(categoryList);
    }

    @Override
    public void userDataSuccess(final UserBeanList userBeanList) {
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

    @Override
    public void userDataSuccess1(UserBeanList1 userBeanList) {

    }


    @Override
    public void showMoreGoodShop(List<GoodShop.ListBean> goodList) {

    }

    @Override
    protected UserContract.ISearchSecondPresent presenter() {
        return new SearchSecondPresent(this, this);
    }

    @Override
    protected void init() {
        appSearchToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        one_dir_id = getIntent().getStringExtra("one_dir_id");
        Category category = new Category();
        category.setOne_dir_id(one_dir_id);
        getPresenter().getData(category);

        userBeanList.setList(new ArrayList<UserBeanDetail>());
        searchCustomAdapter = new SearchCustomAdapter(userBeanList, this);
        listview.setAdapter(searchCustomAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (userBeanList.getList().get(position).getSeller_type()) {
                    case "店铺":
                        Intent j = new Intent(context, ShopCenterActivity.class);
                        j.putExtra("seller_id", userBeanList.getList().get(position).getUser_id());
                        startActivity(j);
                        break;
                    case "个人主页":
                        Intent i = new Intent(context, SellerCenterActivity.class);
                        i.putExtra("seller_id", userBeanList.getList().get(position).getUser_id());
                        startActivity(i);
                        break;
                    case "名片":
                        Intent k = new Intent(context, CardActivity.class);
                        k.putExtra("seller_id", userBeanList.getList().get(position).getUser_id());
                        startActivity(k);
                        break;
                }
            }
        });
        getUser();
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
        scrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                page = 1;
                getUser();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                setRefresh();
            }
        });
    }

    private void setFirstCategory(CategoryList categoryList) {
        setListview(categoryList);
    }

    private void setListview(CategoryList categoryList) {
        List<View> mViewList = new ArrayList<>();
        for (int x = 0; x < page; x++) {
            View inflate = getLayoutInflater().from(context).inflate(R.layout.item_gridview_second_category1234, null);
            mViewList.add(inflate);
        }
        autoHeightViewPager.setAdapter(new SeconCategoryViewPagerAdapter(context, mViewList));
        autoHeightViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int x = 0; x < page; x++) {
            final List<Category> categories1 = new ArrayList<>();
            for (int y = 0; y < categoryList.getList().size(); y++) {
                if (categoryList.getList().size() != 1)
                    categories1.add(categoryList.getList().get(y));
            }

            AutoHeightGridView view = (AutoHeightGridView) mViewList.get(x).findViewById(R.id.gridview);
            view.setAdapter(new SecondCategoryGridviewAdapter1234(categories1, context));
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Intent intent = new Intent(SearchSecondActivity1234.this, SearchSecondTwoActivity1234.class);
                    intent.putExtra("one_dir_id", one_dir_id);
                    intent.putExtra("two_dir_id", categories1.get(position).getTwo_dir_id());
                    startActivity(intent);
                }
            });

        }
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
        searchUser.setTwo_dir_id("");
        searchUser.setOne_dir_id(one_dir_id);
        searchUser.setTp(0);
        getPresenter().getUserData(searchUser);
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

    private boolean isHaveMore() {
        if (page >= total) {
            page = total;
            return false;
        }
        return true;
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
        searchCustomAdapter.setlonlat(longitude, latitude);
        Log.i("LocationListener2", "获得结果:longitude:" + longitude + "    latitude:" + latitude);

        //设置当前距离装饰城距离
//        baidudistance.setText(DistanceLonLat.getDistance(longitude, latitude, Double.parseDouble(this.longitude), Double.parseDouble(this.latitude)));

    }
}
