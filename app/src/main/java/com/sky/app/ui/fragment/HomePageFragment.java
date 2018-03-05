package com.sky.app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.sky.app.R;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.Goods;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.contract.HomeContract;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.banner.LoopViewPagerLayout;
import com.sky.app.library.component.banner.listener.OnBannerItemClickListener;
import com.sky.app.library.component.banner.listener.OnLoadImageViewListener;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.banner.modle.IndicatorLocation;
import com.sky.app.library.component.banner.modle.LoopStyle;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.HomeActivityPresenter;
import com.sky.app.test.TestActivity;
import com.sky.app.ui.activity.product.ProductDetailActivity;
import com.sky.app.ui.activity.publish.PublishActivity;
import com.sky.app.ui.activity.publish.PublishDetailActivity;
import com.sky.app.ui.activity.publish.PublishDetailHeadlinearActivity;
import com.sky.app.ui.activity.search.GoodShopGridviewAdapter;
import com.sky.app.ui.activity.search.SearchByDecorationCityActivity;
import com.sky.app.ui.activity.search.SearchByFactoryActivity;
import com.sky.app.ui.activity.search.SearchByInputActivity;
import com.sky.app.ui.activity.search.SearchByPlaceNewActivity;
import com.sky.app.ui.activity.search.SearchSecondActivity;
import com.sky.app.ui.activity.search.SearchSecondActivity1234;
import com.sky.app.ui.activity.search.SearchSecondOtherActivity;
import com.sky.app.ui.activity.search.SearchShopActivity;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.ui.adapter.AppHomeCategoryViewPagerAdapter;
import com.sky.app.ui.adapter.AppHomeMyGridViewAdapter;
import com.sky.app.ui.adapter.GoodsGridViewAdapter;
import com.sky.app.ui.custom.AutoHeightGridView;
import com.sky.app.ui.custom.AutoHeightHomeViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 主页
 * Created by Administrator on 2017/2/30.
 */
public class HomePageFragment extends BaseViewFragment<HomeContract.IHomePresenter>
        implements HomeContract.IHomeView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mLoopViewPagerLayout1)
    LoopViewPagerLayout mLoopViewPagerLayout1;
    @BindView(R.id.app_home_linear)
    LinearLayout homelinear;
    @BindView(R.id.app_home_linear_supply)
    LinearLayout homesupply;
    @BindView(R.id.app_home_linear_buy)
    LinearLayout homebuy;
    @BindView(R.id.viewpager)
    AutoHeightHomeViewPager viewPager;
    @BindView(R.id.points)
    LinearLayout group;
    @BindView(R.id.goodshop_gridview)
    AutoHeightGridView goodshopGridView;
    @BindView(R.id.good_gridview)
    AutoHeightGridView goodgridview;
    //  @BindView(R.id.app_home_listview)
//  RecyclerView apphomelistview;

    @BindView(R.id.moreshops)
    TextView moreshop;
    //     private FirstCategoryAdapter firstCategoryAdapter;
    List<FirstCategoryDetail> firstCategoryDetailList = new ArrayList<>();
    Unbinder unbinder;
    @BindView(R.id.gj)
    TextSwitcher gj;
    @BindView(R.id.app_supply)
    TextSwitcher supply;
    @BindView(R.id.app_buy)
    TextSwitcher buy;
//    @BindView(R.id.gj_buy)
//    TextSwitcher gjbuy;
//    @BindView(R.id.gj_supply)
//    TextSwitcher gjsupply;

    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private int mPageSize = 10; //每页显示的最大的数量
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    //private int currentPage;//当前页
    private int switcherCount = 0;
    private Handler mHandler = new Handler();
    private GoodShopGridviewAdapter goodShopGridviewAdapter;
    private String product_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }

        /*第一个banner*/
        mLoopViewPagerLayout1.setLoop_ms(3000);//轮播的速度(毫秒)
        mLoopViewPagerLayout1.setLoop_duration(800);//滑动的速率(毫秒)
        mLoopViewPagerLayout1.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        mLoopViewPagerLayout1.setIndicatorLocation(IndicatorLocation.Right);
        mLoopViewPagerLayout1.initializeData(context);//初始化数据
        mLoopViewPagerLayout1.setOnLoadImageViewListener(new OnLoadImageViewListener() {
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
        mLoopViewPagerLayout1.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
                if (!TextUtils.isEmpty(banner.get(index).skipUrl)) {
                    AppUtils.skipBrowser(context, banner.get(index).skipUrl);
                }
            }
        });
//请求一级分类
        getPresenter().requestFirstCategory();
    }


    private void initData() {
        //总的页数向上取整
        totalPage = (firstCategoryDetailList.size() % 10 == 0 ? firstCategoryDetailList.size() / 10 : firstCategoryDetailList.size() / 10 + 1);
        viewPagerList = new ArrayList<>();
        //totalPage是整数,1,2
        for (int x = 0; x < totalPage; x++) {
            ImageView imageView = new ImageView(context);
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_gridview_apphome_category, null);
            viewPagerList.add(inflate);
            if (totalPage == 0) {
                inflate.setVisibility(View.GONE);
            }
            if (totalPage > 1) {
                group.setVisibility(View.VISIBLE);
                if (x == 0) {
                    imageView.setBackgroundResource(R.drawable.select_dot);
                } else {
                    imageView.setBackgroundResource(R.drawable.normal_dot);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
                layoutParams.setMargins(0, 0, 15, 0);
                imageView.setLayoutParams(layoutParams);
                group.addView(imageView);

            } else {
                group.setVisibility(View.GONE);
            }
        }
        //设置ViewPager适配器
        final AppHomeCategoryViewPagerAdapter adapter1 = new AppHomeCategoryViewPagerAdapter(viewPagerList);
        viewPager.setAdapter(adapter1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    View childAt = group.getChildAt(i);
                    if (position == i) {
                        if (childAt != null) {
                            childAt.setBackgroundResource(R.drawable.select_dot);
                        }
                    } else {
                        childAt.setBackgroundResource(R.drawable.normal_dot);
                    }
                    adapter1.notifyDataSetChanged();

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int x = 0; x < totalPage; x++) {
            final List<FirstCategoryDetail> categories1 = new ArrayList<>();

            for (int y = x * 9; y < firstCategoryDetailList.size(); y++) {
                if (categories1.size() == 9) {
                    break;
                }
                categories1.add(firstCategoryDetailList.get(y));
            }
            AutoHeightGridView gridView = (AutoHeightGridView) viewPagerList.get(x).findViewById(R.id.app_type);
            AppHomeMyGridViewAdapter adapter = new AppHomeMyGridViewAdapter(context, firstCategoryDetailList, x, mPageSize);
            gridView.setAdapter(adapter);
            final int finalX = x;
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //==1的时候，表示第二页，i要加上10,表示第10个数据
                    if (finalX == 1) {
                        i += 10;
                    }
                    String one_dir_id = firstCategoryDetailList.get(i).getOne_dir_id();
                    if ("FW500007".equals(one_dir_id)) {//货运
                        Intent p = getActivity().getPackageManager().getLaunchIntentForPackage("com.sky.transport");
                        if (null == p) {
                            T.showShort(context, "没有安装货运app");
                            return;
                        } else {
                            startActivity(p);
                        }
                    } else if ("FW500003".equals(one_dir_id) || "FW500004".equals(one_dir_id) || "FW500006".equals(one_dir_id) || "FW500008".equals(one_dir_id) ||
                            "FW500009".equals(one_dir_id) || "FW500010".equals(one_dir_id) || "FW500011".equals(one_dir_id) ||
                            "FW500013".equals(one_dir_id) || "FW500014".equals(one_dir_id) || "FW500015".equals(one_dir_id) || "FW500016".equals(one_dir_id)
                            || "FW500017".equals(one_dir_id) || "FW500018".equals(one_dir_id)) {
                        Intent intent = new Intent(getActivity(), SearchSecondActivity1234.class);
                        intent.putExtra("one_dir_id", one_dir_id);
                        getActivity().startActivity(intent);
                    } else if ("FW500001".equals(one_dir_id)) {
                        Intent intent = new Intent(getActivity(), SearchSecondOtherActivity.class);
                        intent.putExtra("one_dir_id", one_dir_id);
                        getActivity().startActivity(intent);
                    }else {
                        Intent intent = new Intent(getActivity(), SearchSecondActivity.class);
                        intent.putExtra("one_dir_id", one_dir_id);
                        getActivity().startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    protected void initViewsAndEvents() {
        onRefresh();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_home_page;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected HomeContract.IHomePresenter presenter() {
        return new HomeActivityPresenter(context, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLoopViewPagerLayout1.startLoop();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStop() {
        super.onStop();
        mLoopViewPagerLayout1.stopLoop();
//        mHandler.removeCallbacks(runnable);
        gj.stopNestedScroll();
        buy.stopNestedScroll();
        supply.stopNestedScroll();
//        mHandler.removeCallbacks(postDelayed);//停止线程
//        toutiao.stop();
//        supply.stop();
//        buy.stop();
//        toutiao1.stop();
//        supply1.stop();
//        buy1.stop();
    }

    /**
     * 地区查找
     */
    @OnClick(R.id.area_rel)
    void jumpToArea() {
        getActivity().startActivity(new Intent(getActivity(), SearchByPlaceNewActivity.class));
    }

    /**
     * 生产厂家查找
     */
    @OnClick(R.id.factory_rel)
    void jumpToFactory() {
        getActivity().startActivity(new Intent(getActivity(), SearchByFactoryActivity.class));
    }

    @OnClick(R.id.app_search_tv)
    void jumpToSearch() {
        //获得用户输入的
        getActivity().startActivity(new Intent(getActivity(), SearchByInputActivity.class));
    }
//    @OnClick(R.id.app_search_tv_temp)
//    void jumpToSearch1() {
//        //获得用户输入的
//        getActivity().startActivity(new Intent(getActivity(), SearchByInputActivity.class));
//    }

    @OnClick({R.id.skip_supply, R.id.skip_buy})
    void skipPublishActivity() {
        getActivity().startActivity(new Intent(getActivity(), PublishActivity.class));
    }


    /**
     * 首页轮播图展示
     *
     * @param list
     */
    @Override
    public void showBannerSuccess(List<BannerInfo> list, int flag) {
        mSwipeRefreshLayout.setRefreshing(false);
        switch (flag) {
            case 1:
                mLoopViewPagerLayout1.setLoopData((ArrayList<BannerInfo>) list);
                break;
        }
    }

    @Override
    public void showFirstCategorySuccess(List<FirstCategoryDetail> list) {
        mSwipeRefreshLayout.setRefreshing(false);
        firstCategoryDetailList.clear();
        firstCategoryDetailList.addAll(list);
        initData();
    }

    /**
     * 首页供应信息
     *
     * @param list
     */
    @Override
    public void showSupplySuccess(List<SupplyDetail> list) {

        String[] string = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            string[i] = list.get(i).getProduct_name();
        }
        supply.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(context);
                return tv;
            }
        });
        supply.setInAnimation(getContext(), R.anim.enter);
        supply.setOutAnimation(getContext(), R.anim.out);
        if (supply != null)
            startsupply(string);
    }

    private void startsupply(final String[] strings) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (supply == null)
                    return;
                String strA = strings[switcherCount % strings.length];
                String strB = switcherCount % strings.length + 1 < strings.length ? strings[switcherCount % strings.length + 1] : strings[0];
                switcherCount += 2;
                supply.setText(strA + "\n" + strB);
                startsupply(strings);
            }
        }, 1500);
    }

    /**
     * 首页采购信息
     *
     * @param list
     */
    @Override
    public void showBuySuccess(List<SupplyDetail> list) {
        String[] string = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            string[i] = list.get(i).getProduct_name();
            product_id = list.get(i).getProduct_id();
        }
        buy.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(context);
                return tv;
            }
        });
        buy.setInAnimation(getContext(), R.anim.enter);
        buy.setOutAnimation(getContext(), R.anim.out);
        if (buy != null)
            startbuy(string);

    }

    private void startbuy(final String[] strings) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (buy == null)
                    return;
                String strA = strings[switcherCount % strings.length];
                String strB = switcherCount % strings.length + 1 < strings.length ? strings[switcherCount % strings.length + 1] : strings[0];
                switcherCount += 2;
                buy.setText(strA + "\n" + strB);
                startbuy(strings);
            }
        }, 1500);
    }

    //首页的工匠头条数据的展示
    @Override
    public void showHeadlinearsSuccess(final HeadlinearsDetail[] headlinearsDetail) {
        final List<HeadlinearsDetail> list = new ArrayList<>();
        list.addAll(Arrays.asList(headlinearsDetail));
        String[] string = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            string[i] = list.get(i).getNoticetitle();
        }
        gj.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(context);
                return tv;
            }
        });
        gj.setInAnimation(getContext(), R.anim.enter);
        gj.setOutAnimation(getContext(), R.anim.out);
        gj.setText(string[0] + "\n" + string[1]);
        if (gj != null)
            start(string);

    }

    private void start(final String[] strings) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gj == null)
                    return;
                String strA = strings[switcherCount % strings.length];
                String strB = switcherCount % strings.length + 1 < strings.length ? strings[switcherCount % strings.length + 1] : strings[0];
                switcherCount += 2;
                gj.setText(strA + "\n" + strB);
                start(strings);
            }
        }, 1500);
    }

    @OnClick(R.id.moreshops)
    void moreShops() {
        //获得用户输入的
        getActivity().startActivity(new Intent(getActivity(), SearchShopActivity.class));

    }


    /**
     * 展示品质好店
     */
    @Override
    public void showGoodShop(final List<GoodShop.ListBean> goodList) {
        goodShopGridviewAdapter = new GoodShopGridviewAdapter(goodList, context);
        goodshopGridView.setAdapter(goodShopGridviewAdapter);
        goodshopGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String user_id = goodList.get(i).getUser_id();
                String seller_id = user_id;
                Intent intent = new Intent(getActivity(), ShopCenterActivity.class);
                intent.putExtra("seller_id", seller_id);
                getActivity().startActivity(intent);
            }
        });

    }

    /**
     * 首页精品好货的数据展示
     *
     * @param
     */
    @Override
    public void showGoodsSuccess(final List<Goods.ListBean> goods1ist) {
        GoodsGridViewAdapter goodsGridViewAdapter = new GoodsGridViewAdapter(goods1ist, context);
        goodgridview.setAdapter(goodsGridViewAdapter);
        goodgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String product_id = goods1ist.get(i).getProduct_id();
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product_id", product_id);
                getActivity().startActivity(intent);

            }
        });
    }

    /**
     * 跳转供货详情
     */
    private void skipPublishDetail(String s) {
        Intent intent = new Intent(context, PublishDetailActivity.class);
        intent.putExtra("product_id", s);
        getActivity().startActivity(intent);
    }

//    /**
//     * 跳转工匠头条详情
//     */
//    private void skipPublishDetail1(String s) {
//        Intent intent = new Intent(context, PublishDetailHeadlinearActivity.class);
//        intent.putExtra("product_id", s);
//        getActivity().startActivity(intent);
//

    /**
     * 跳转工匠头条详情
     */
    @OnClick(R.id.app_home_linear)
    void skipPublishDetail1() {
        Intent intent = new Intent(context, PublishDetailHeadlinearActivity.class);
        getActivity().startActivity(intent);
    }

//    /**
//     * 跳转供货详情
//     */
//    @OnClick({R.id.app_home_linear_buy,R.id.app_home_linear_supply})
//    void skipPublish() {
//        Intent intent = new Intent(context, PublishDetailActivity.class);
//        intent.putExtra("product_id",product_id);
//        getActivity().startActivity(intent);
//    }

    /**
     * 装饰城
     */
    @OnClick(R.id.zhuangshicheng_rel)
    void jumpToDecorationCity() {
        getActivity().startActivity(new Intent(getActivity(), SearchByDecorationCityActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 网络请求
     */
    @Override
    public void onRefresh() {
        //第一banner请求
        getPresenter().requestBanner();
        //获取采购信息
        getPresenter().requestBuy();
        //获取供应信息
        getPresenter().requestSupply();
        //获取工匠头条
        getPresenter().requestHeadlines();
        //获取品质好店
        getPresenter().requestGoodShop(1);
        //获取精品好货
        getPresenter().requestGoods(1);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}