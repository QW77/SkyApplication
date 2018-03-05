package com.sky.app.ui.activity.publish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.DecorationCityHeadlinearDetai;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.CircleImageView;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.PublishDetailPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布信息详情
 */
public class PublishDetailActivity extends BaseViewActivity<PublishContract.IPublishDetailPresenter>
        implements PublishContract.IPublishDetailView {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;
    @BindView(R.id.photo)
    CircleImageView circleImageView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.title)
    TextView titleName;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.app_content)
    TextView content;
    SupplyDetail supplyDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_home_publish_detail);
    }

    @Override
    protected PublishContract.IPublishDetailPresenter presenter() {
        return new PublishDetailPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_publish_detail_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        toolbar.inflateMenu(R.menu.app_share_menu);
//        //设置刷新时动画的颜色，可以设置4个
//        if (appswipe != null) {
//            appswipe.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
//            appswipe.setColorSchemeResources(R.color.main_colorPrimary);
//            mSwipeRefreshLayout1.isRefreshing();
//            appswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    appswipe.setRefreshing(false);
//                }
//            });
//        }
//          /*第一个banner*/
//        publishmLoopViewPagerLayout1.setLoop_ms(3000);//轮播的速度(毫秒)
//        publishmLoopViewPagerLayout1.setLoop_duration(800);//滑动的速率(毫秒)
//        publishmLoopViewPagerLayout1.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
//        publishmLoopViewPagerLayout1.setIndicatorLocation(IndicatorLocation.Right);
//        publishmLoopViewPagerLayout1.initializeData(context);//初始化数据
//        publishmLoopViewPagerLayout1.setOnLoadImageViewListener(new OnLoadImageViewListener() {
//            @Override
//            public void onLoadImageView(ImageView imageView, Object parameter) {
//                ImageHelper.getInstance().displayDefinedImage(String.valueOf(parameter), imageView,
//                        R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
//            }
//
//            @Override
//            public ImageView createImageView(Context context) {
//                ImageView imageView = new ImageView(context);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                return imageView;
//            }
//        });
//        publishmLoopViewPagerLayout1.setOnBannerItemClickListener(new OnBannerItemClickListener() {
//            @Override
//            public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
//                if (!TextUtils.isEmpty(banner.get(index).skipUrl)) {
//                    AppUtils.skipBrowser(context, banner.get(index).skipUrl);
//                }
//            }
//        });
    }

    @Override
    public void initViewsAndEvents() {
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
//                        ShareUtils.share(PublishDetailActivity.this, "", "", "");
                        break;
                }
                return false;
            }
        });
        String product_id = getIntent().getStringExtra("product_id");
        if (TextUtils.isEmpty(product_id)) {
            T.showShort(context, "数据异常");
            return;
        }
        ProductIn productIn = new ProductIn();
        productIn.setProduct_id(product_id);
//        //网络请求消息
        getPresenter().requestDetail(productIn);
//        //请求工匠头条的轮播图
//        getPresenter().requestPublishBanner();


    }

    //// TODO: 2018/3/3 分享
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
//        oks.setTitle(supplyDetail.getProduct_name());
//
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(supplyDetail.getProduct_key_words());
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////        oks.setImagePath(Environment.getExternalStorageDirectory().getPath()+"/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(Constants.Url.BASE_URL+"h5_product/un/product_detail?product_id="+supplyDetail.getProduct_id()+"&user_id="+supplyDetail.getUser_id());
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
////        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("51工匠");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(Constants.Url.BASE_URL+"h5_product/un/product_detail?product_id="+supplyDetail.getProduct_id()+"&user_id="+supplyDetail.getUser_id());
//        //网络图片的url：所有平台
//        oks.setImageUrl(supplyDetail.getUser_pic_url());//网络图片rul
//        // Url：仅在QQ空间使用
//        oks.setTitleUrl(Constants.Url.BASE_URL+"h5_product/un/product_detail?product_id="+supplyDetail.getProduct_id()+"&user_id="+supplyDetail.getUser_id());  //网友点进链接后，可以看到分享的详情
////        oks.setTitleUrl("http://api.app.51craftsman.com/cc.jsp?type=decoration&id=180126059586&user_id=170922011253&machine_id=180126059586");  //网友点进链接后，可以看到分享的详情
//        // 启动分享GUI
//        oks.show(this);
//    }

    @Override
    public void showPublishCityBannerSuccess(List<BannerInfo> list) {

    }

    @Override
    public void showPublishCityHeadlinearsSuccess(DecorationCityHeadlinearDetai[] headlinears) {

    }


    //展示工匠头条的轮播图
    @Override
    public void showPublishsuccess(List<BannerInfo> list) {

    }

    @Override
    public void showPublishHeadlinearsSuccess(HeadlinearsDetail[] headlinearsDetail) {

    }

    @Override
    public void responseDetail(SupplyDetail supplyDetail) {
        this.supplyDetail = supplyDetail;
        ImageHelper.getInstance().displayDefinedImage(supplyDetail.getUser_pic_url(), circleImageView,
                R.mipmap.app_person_icon, R.mipmap.app_person_icon);
        userName.setText(supplyDetail.getUser_name());
        titleName.setText(supplyDetail.getProduct_name());
        mobile.setText("电话：" + supplyDetail.getUser_phone());
        content.setText(supplyDetail.getProduct_desc());
    }

    @Override
    public void responseCollect(String msg) {
        T.showShort(context, msg);
    }

    @OnClick(R.id.collect)
    void clickCollect() {
        if (null == supplyDetail) {
            T.showShort(context, "收藏数据错误");
            return;
        }
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("3");
        collectPubIn.setCollect_value(supplyDetail.getProduct_id());
        getPresenter().requestCollect(collectPubIn);
    }

    @OnClick(R.id.call_phone)
    void callPhone() {
        if (TextUtils.isEmpty(supplyDetail.getUser_phone())) {
            T.showShort(context, "没有手机号码");
            return;
        }
        AppUtils.callPhone(context, supplyDetail.getUser_phone());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        showShare();
//        ShareUtils.onActivityResult(PublishDetailActivity.this, requestCode, resultCode, data);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {


    }
}
