package com.sky.app.ui.activity.product;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.sky.app.R;
import com.sky.app.bean.BaseUser;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.CommentResponse;
import com.sky.app.bean.OrderDetail;
import com.sky.app.bean.ProductDeatilResponse;
import com.sky.app.bean.ProductDetailRequest;
import com.sky.app.bean.SingleOrderIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.CircleImageView;
import com.sky.app.library.component.RatingBar;
import com.sky.app.library.component.banner.LoopViewPagerLayout;
import com.sky.app.library.component.banner.listener.OnLoadImageViewListener;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.banner.modle.IndicatorLocation;
import com.sky.app.library.component.banner.modle.LoopStyle;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.presenter.ProductDetailPresent;
import com.sky.app.ui.activity.openIM.OpenIMLoginActivity;
import com.sky.app.ui.activity.order.ConfirmOrderActivity;
import com.sky.app.ui.activity.order.MyShoppingActivity;
import com.sky.app.ui.activity.seller.CommentListActivity;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.utils.AppDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * 商品详细界面
 */

public class    ProductDetailActivity extends BaseViewActivity<ShopContract.IProductDetailPresenter>
        implements ShopContract.IProductDetailView, ShopContract.ICollectionView {

    @BindView(R.id.detail_banner)
    LoopViewPagerLayout loopViewPagerLayout;
    @BindView(R.id.product_detail_title)
    TextView productDetailTitle;
    @BindView(R.id.product_detail_newprice)
    TextView productDetailNewprice;
    @BindView(R.id.product_detail_orignprice)
    TextView productDetailOrignprice;
    @BindView(R.id.product_detail_collect)
    TextView productDetailCollect;
    @BindView(R.id.product_detail_xiaoliang)
    TextView productDetailXiaoliang;
    @BindView(R.id.product_detail_commentnumber)
    TextView productDetailCommentnumber;
    @BindView(R.id.product_detail_seller_pic)
    ImageView productDetailSellerPic;
    @BindView(R.id.product_detail_seller_name)
    TextView productDetailSellerName;
    @BindView(R.id.product_detail_seller_shoucang)
    TextView productDetailSellerShoucang;
    @BindView(R.id.app_store_activity)
    TextView storeActivity;
    @BindView(R.id.app_star)
    ImageView appStar;
    @BindView(R.id.app_product_detail)
    WebView detail;

    //评论
    @BindView(R.id.app_user_photo_icon)
    CircleImageView circleImageView;
    @BindView(R.id.app_user_name)
    TextView userName;
    @BindView(R.id.app_comment_time)
    TextView commentTime;
    @BindView(R.id.app_comment_txt)
    TextView commentTxt;
    @BindView(R.id.app_skip_all_comment)
    TextView app_skip_all_comment;
    @BindView(R.id.quality_rating_bar)
    RatingBar qualityRatingBar;
    @BindView(R.id.service_rating_bar)
    RatingBar serviceRatingBar;
    @BindView(R.id.speed_rating_bar)
    RatingBar speedRatingBar;
    @BindView(R.id.app_comment_ll)
    LinearLayout commentLL;

    private String product_id;

    private ProductDeatilResponse productDeatilResponse;
    private ShopContract.ICollectionPresenter iCollectionPresenter;
    private int flag = -1;//1：商家收藏 2：商品收藏


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

    }

    @Override
    protected ShopContract.IProductDetailPresenter presenter() {
        iCollectionPresenter = new CollectPresenter(context, this);
        return new ProductDetailPresent(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
        geteProductDetail(product_id);
        getProductComment();
    }

    /**
     * 获取商品评论
     */
    private void getProductComment() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setValue(product_id);
        getPresenter().getProductComment(commentRequest);
    }

    /**
     * 获取商品详情
     *
     * @param product_id
     */
    private void geteProductDetail(String product_id) {
        ProductDetailRequest productDetailRequest = new ProductDetailRequest();
        productDetailRequest.setProduct_id(product_id);
        getPresenter().getProductDetail(productDetailRequest);
    }


    @Override
    protected void init() {
        product_id = getIntent().getStringExtra("product_id");
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

    /**
     *
     * @param productList
     */
    @Override
    public void productDetailSuccess(ProductDeatilResponse productList) {
        this.productDeatilResponse = productList;

        //banner
        List<BannerInfo> bannerInfos = new ArrayList<>();
        if (null != productList.getProduct_image_urls()) {
            for (String s : productList.getProduct_image_urls()) {
                BannerInfo bannerInfo = new BannerInfo(s, "", "");
                bannerInfos.add(bannerInfo);
            }
            loopViewPagerLayout.setLoopData((ArrayList<BannerInfo>) bannerInfos);
        }

        //渲染商品数据
        productDetailTitle.setText(productList.getProduct_name());
        productDetailNewprice.setText("¥" + AppUtils.parseDouble(productList.getProduct_price_now() / 100));
        productDetailOrignprice.setText("原价:" + AppUtils.parseDouble(productList.getProduct_price_old() / 100));
        storeActivity.setText("本店活动：" + (TextUtils.isEmpty(productDeatilResponse.getProduct_key_words())
                ? "" : productDeatilResponse.getProduct_key_words()));

        //销量／评论数
        productDetailCommentnumber.setText(productList.getNum_comment() + "");
        productDetailXiaoliang.setText(productList.getNum_good() + "");

        //卖家头像
        ImageHelper.getInstance().displayDefinedImage(productList.getSeller().getPic_url(),
                productDetailSellerPic, R.mipmap.app_default_icon, R.mipmap.app_default_icon);

        //昵称
        productDetailSellerName.setText(productList.getSeller().getNick_name());

        //收藏商家按钮控制
        if (1 == productList.getSeller().getIs_collect()) {
            productDetailSellerShoucang.setText("已收藏");

        } else {
            productDetailSellerShoucang.setText("收藏店铺");
        }

        //收藏商品按钮控制
        if (1 == productList.getIs_collect()) {
            productDetailCollect.setText("已收藏");
            appStar.setImageResource(R.mipmap.app_product_detail_selected_star_icon);
        } else {
            productDetailCollect.setText("收藏");
            appStar.setImageResource(R.mipmap.app_product_detail_star_icon);
        }

        //加载商品详情
        detail.getSettings().setUseWideViewPort(true);
        detail.getSettings().setLoadWithOverviewMode(true);
        detail.loadDataWithBaseURL(null, productList.getProduct_desc(), "text/html", "UTF-8", null);
    }

    @Override
    public void ProductCommentSuccess(CommentResponse commentResponse) {
        if (null == commentResponse) {
            commentLL.setVisibility(View.GONE);
            return;
        }
        commentLL.setVisibility(View.VISIBLE);
        ImageHelper.getInstance().displayDefinedImage(commentResponse.getComment_content(), circleImageView,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        userName.setText(commentResponse.getNick_name());
        commentTime.setText(commentResponse.getComment_time());
        commentTxt.setText(commentResponse.getComment_content());
        qualityRatingBar.setStar(commentResponse.getQuality());
        serviceRatingBar.setStar(commentResponse.getService());
        speedRatingBar.setStar(commentResponse.getSpeed());
    }

    @Override
    public void showAddResult(String msg) {
        T.showShort(context, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        ShareUtils.onActivityResult(this, requestCode, resultCode, data);
        showShare();

    }

    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
        if (flag == 1) {//收藏商家
            if (tag == 3) {//取消收藏
                productDetailSellerShoucang.setText("收藏店铺");
            } else if (tag == 4) {
                productDetailSellerShoucang.setText("已收藏");
            }
        } else if (flag == 2) {//收藏商品
            if (tag == 1) {
                productDetailCollect.setText("收藏");
                appStar.setImageResource(R.mipmap.app_product_detail_star_icon);
            } else if (tag == 2) {
                productDetailCollect.setText("已收藏");
                appStar.setImageResource(R.mipmap.app_product_detail_selected_star_icon);
            }
        }
    }

    @Override
    public void showCollectError(String error) {
        T.showShort(context, error);
        if (flag == 1) {//收藏商家
            if (tag == 3) {//取消收藏
                productDetailSellerShoucang.setText("已收藏");
            } else if (tag == 4) {
                productDetailSellerShoucang.setText("收藏店铺");
            }
        } else if (flag == 2) {//收藏商品
            if (tag == 1) {
                productDetailCollect.setText("已收藏");
                appStar.setImageResource(R.mipmap.app_product_detail_selected_star_icon);
            } else {
                productDetailCollect.setText("收藏");
                appStar.setImageResource(R.mipmap.app_product_detail_star_icon);
            }
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

    @OnClick(R.id.product_detail_back)
    public void backPress() {
        onBackPressed();
    }

    //购物车
    @OnClick(R.id.product_detail_shopcar)
    public void shopCar() {
        startActivity(new Intent(context, MyShoppingActivity.class));
    }

    //分享
    @OnClick(R.id.product_detail_share)
    public void share() {
        // TODO: 2017/12/6 0006
//        ShareUtils.share(this, "", "", "");
        showShare();
    }


    //选择分类
    @OnClick(R.id.product_detail_choosesize)
    public void chooseSize() {
        skipSelectPage();
    }

    //立即购买
    @OnClick(R.id.product_detail_buynow)
    public void buyNow() {
        skipSelectPage();
    }

    //加入购车
    @OnClick(R.id.product_detail_joincar)
    public void joinCar() {
        skipSelectPage();
    }

    /**
     * 选择商品分类
     */
    private void skipSelectPage() {
        AppDialogUtils.showBuyDialog(this, productDeatilResponse, new ISelectCallBack() {
            @Override
            public void data(int type, int pos, int num) {
                switch (type) {
                    case 1://加入购物车
                        SingleOrderIn singleOrderIn = new SingleOrderIn();
                        singleOrderIn.setProduct_id(product_id);
                        singleOrderIn.setProduct_num(num);
                        singleOrderIn.setAttr_id(productDeatilResponse.getAttrs().get(pos).getAttr_id());
                        getPresenter().addShoppingCar(singleOrderIn);
                        break;
                    case 2://立即购买
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setProduct_id(product_id);
                        orderDetail.setProduct_image_url(productDeatilResponse.getProduct_image_url());
                        orderDetail.setProduct_name(productDeatilResponse.getProduct_name());
                        orderDetail.setAttr_name(productDeatilResponse.getAttrs().get(pos).getAttr_name());
                        orderDetail.setAttr_id(productDeatilResponse.getAttrs().get(pos).getAttr_id());
                        orderDetail.setAttr_price_now(productDeatilResponse.getProduct_price_now());
                        orderDetail.setSeller_name(productDeatilResponse.getSeller().getNick_name());
                        orderDetail.setProduct_num(num);
                        Intent i = new Intent(context, ConfirmOrderActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("detail", orderDetail);
                        i.putExtras(bundle);
                        startActivity(i);
                        break;
                }
                AppDialogUtils.closeBuyDialog();
            }
        });
    }

    //客服
    @OnClick(R.id.product_detail_kefu)
    public void keFu() {
        SharedPreferences sp = getSharedPreferences("seller_id", Context.MODE_PRIVATE);
        sp.edit().putString("seller_id", product_id).commit();
        startActivity(new Intent(this, OpenIMLoginActivity.class));

    }


    /**
     * 跳转全部评价
     */
    @OnClick(R.id.app_skip_all_comment)
    void clickAllComment() {
        Intent i = new Intent(context, CommentListActivity.class);
        i.putExtra("seller_id", productDeatilResponse.getSeller().getUser_id());
        i.putExtra("id", productDeatilResponse.getProduct_id());
        i.putExtra("is_collect", productDeatilResponse.getIs_collect());
        i.putExtra("mobile", productDeatilResponse.getSeller().getMobile());
        i.putExtra("type", 2);
        startActivity(i);
    }

    /**
     * 跳转店铺
     */
    @OnClick({R.id.app_shop, R.id.app_go_shop})
    public void shop() {
        Intent j = new Intent(context, ShopCenterActivity.class);
        j.putExtra("seller_id", productDeatilResponse.getSeller().getUser_id());
        j.putExtra("is_collect", productDeatilResponse.getSeller().getIs_collect());
        j.putExtra("mobile", productDeatilResponse.getSeller().getMobile());
        j.putExtra("nick_name", productDeatilResponse.getSeller().getNick_name());
        j.putExtra("personal_pic", productDeatilResponse.getSeller().getPersonal_pic());
        j.putExtra("pic_url", productDeatilResponse.getSeller().getPic_url());
        startActivity(j);
    }

    /**
     * 收藏商品
     */
    private int tag = -1;

    @OnClick(R.id.product_detail_collect)
    public void collectProduct() {
        flag = 2;
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setCollect_value(product_id);
        collectPubIn.setType("1");
        if ("已收藏".equals(productDetailCollect.getText().toString())) {//收藏过了
            tag = 1;
            iCollectionPresenter.cancelCollect(collectPubIn);
        } else {
            tag = 2;
            iCollectionPresenter.requestCollect(collectPubIn);
        }
    }

    /**
     * 收藏商家
     */
    @OnClick(R.id.product_detail_seller_shoucang)
    void collectSeller() {
        if (null == productDeatilResponse || null == productDeatilResponse.getSeller()) {
            return;
        }
        flag = 1;
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setCollect_value(productDeatilResponse.getSeller().getUser_id());
        collectPubIn.setType("2");
        if ("已收藏".equals(productDetailSellerShoucang.getText().toString())) {//收藏过了
            tag = 3;
            iCollectionPresenter.cancelCollect(collectPubIn);
        } else {
            tag = 4;
            iCollectionPresenter.requestCollect(collectPubIn);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loopViewPagerLayout.startLoop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loopViewPagerLayout.stopLoop();
    }

    /**
     * 选择类型回调
     */
    public interface ISelectCallBack {
        void data(int type, int pos, int num);
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

    //这是分享函数，哪里需要分享调用此函数即可，
    //参数可自行设置
    private void showShare() {
//        ShareSDK
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
//        oks.setNotification(R.drawable.ic_launcher,getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用,// title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle(productDeatilResponse.getProduct_name());

        // text是分享文本，所有平台都需要这个字段
        oks.setText(productDeatilResponse.getProduct_key_words());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(Environment.getExternalStorageDirectory().getPath()+"/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(Constants.Url.BASE_URL+"h5_product/un/product_detail?product_id="+productDeatilResponse.getProduct_id()+"&user_id="+productDeatilResponse.getUser_id());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("51工匠");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(Constants.Url.BASE_URL+"h5_product/un/product_detail?product_id="+productDeatilResponse.getProduct_id()+"&user_id="+productDeatilResponse.getUser_id());
        //网络图片的url：所有平台
        oks.setImageUrl(productDeatilResponse.getProduct_image_url());//网络图片rul
        // Url：仅在QQ空间使用
        oks.setTitleUrl(Constants.Url.BASE_URL+"h5_product/un/product_detail?product_id="+productDeatilResponse.getProduct_id()+"&user_id="+productDeatilResponse.getUser_id());  //网友点进链接后，可以看到分享的详情
//        oks.setTitleUrl("http://api.app.51craftsman.com/cc.jsp?type=decoration&id=180126059586&user_id=170922011253&machine_id=180126059586");  //网友点进链接后，可以看到分享的详情


        // 启动分享GUI
        oks.show(this);
    }
}
