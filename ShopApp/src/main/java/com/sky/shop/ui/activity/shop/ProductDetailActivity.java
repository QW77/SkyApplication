package com.sky.shop.ui.activity.shop;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.banner.LoopViewPagerLayout;
import com.sky.app.library.component.banner.listener.OnLoadImageViewListener;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.banner.modle.IndicatorLocation;
import com.sky.app.library.component.banner.modle.LoopStyle;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.SelectCategoryAdaptor;
import com.sky.shop.bean.ProductDeatilResponse;
import com.sky.shop.bean.ProductDetailRequest;
import com.sky.shop.contract.ProductContract;
import com.sky.shop.presenter.ProductDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品详情
 */
public class ProductDetailActivity extends BaseViewActivity<ProductContract.IProDetailPresenter>
        implements ProductContract.IProDetailView {

    @BindView(R.id.detail_banner)
    LoopViewPagerLayout loopViewPagerLayout;
    @BindView(R.id.app_product_category_list)
    RecyclerView recyclerView;
    @BindView(R.id.product_detail_title)
    TextView productDetailTitle;
    @BindView(R.id.product_detail_newprice)
    TextView productDetailNewprice;
    @BindView(R.id.product_detail_orignprice)
    TextView productDetailOrignprice;
    @BindView(R.id.product_detail_xiaoliang)
    TextView productDetailXiaoliang;
    @BindView(R.id.product_detail_commentnumber)
    TextView productDetailCommentnumber;
    @BindView(R.id.app_store_activity)
    TextView storeActivity;
    @BindView(R.id.app_product_detail)
    WebView detail;

    private SelectCategoryAdaptor selectCategoryAdaptor;
    private String product_id;
    WebSettings settings;
    /**
     * 用来控制字体大小
     */
    int fontSize = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_product_detail);
    }

    @Override
    protected ProductContract.IProDetailPresenter presenter() {
        return new ProductDetailPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void init() {
        product_id = getIntent().getStringExtra("product_id");
        ProductDetailRequest productDetailRequest = new ProductDetailRequest();
        productDetailRequest.setProduct_id(product_id);
        getPresenter().getProductDetail(productDetailRequest);

        loopViewPagerLayout.setLoop_ms(3000);//轮播的速度(毫秒)
        loopViewPagerLayout.setLoop_duration(800);//滑动的速率(毫秒)
        loopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        loopViewPagerLayout.setIndicatorLocation(IndicatorLocation.Center);
        loopViewPagerLayout.initializeData(context);//初始化数据
        loopViewPagerLayout.setOnLoadImageViewListener(new OnLoadImageViewListener() {
            @Override
            public void onLoadImageView(ImageView imageView, Object parameter) {
                ImageHelper.getInstance().displayDefinedImage(String.valueOf(parameter), imageView,
                        R.mipmap.app_default_icon, R.mipmap.app_default_icon);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });
        productDetailOrignprice.getPaint()
                .setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰
    }

    @Override
    public void productDetailSuccess(ProductDeatilResponse productDeatilResponse) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        selectCategoryAdaptor = new SelectCategoryAdaptor(this, productDeatilResponse.getAttrs());
        recyclerView.setAdapter(selectCategoryAdaptor);

        //banner
        List<BannerInfo> bannerInfos = new ArrayList<>();
        if (null != productDeatilResponse.getProduct_image_urls()) {
            for (String s : productDeatilResponse.getProduct_image_urls()) {
                BannerInfo bannerInfo = new BannerInfo(s, "", "");
                bannerInfos.add(bannerInfo);
            }
            loopViewPagerLayout.setLoopData((ArrayList<BannerInfo>) bannerInfos);
        }

        //渲染商品数据
        productDetailTitle.setText(productDeatilResponse.getProduct_name());
        productDetailNewprice.setText("¥" + AppUtils.parseDouble(productDeatilResponse.getProduct_price_now() / 100));
        productDetailOrignprice.setText("原价:" + AppUtils.parseDouble(productDeatilResponse.getProduct_price_old() / 100));
        storeActivity.setText("本店活动：" + (TextUtils.isEmpty(productDeatilResponse.getProduct_key_words())
                ? "" : productDeatilResponse.getProduct_key_words()));

        //销量／评论数
        productDetailCommentnumber.setText(productDeatilResponse.getNum_comment() + "");
        productDetailXiaoliang.setText(productDeatilResponse.getNum_good() + "");

        //加载商品详情
        detail.loadDataWithBaseURL(null, productDeatilResponse.getProduct_desc(), "text/html", "UTF-8", null);
        //WebView 自适应屏幕
        settings = detail.getSettings();
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        int fontSize = (int) getResources().getDimension(R.dimen.login_detect_progress_width);
//        Log.i(TAG, "initView: fontSize = " + fontSize);
        settings.setDefaultFontSize(fontSize);
//        settings.setTextSize(WebSettings.TextSize.SMALLEST);
//        settings.setTextSize(WebSettings.TextSize.SMALLER);
//        settings.setTextSize(WebSettings.TextSize.NORMAL);
//        settings.setTextSize(WebSettings.TextSize.LARGER);
//        settings.setTextSize(WebSettings.TextSize.LARGEST);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @OnClick(R.id.product_detail_back)
    public void backPress() {
        onBackPressed();
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
}
