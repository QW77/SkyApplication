package com.sky.app.ui.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.Case;
import com.sky.app.bean.CaseIn;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.CommentResponse;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.contract.SellerContract;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.CircleImageView;
import com.sky.app.library.component.RatingBar;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.presenter.SellerCenterPresenter;
import com.sky.app.ui.adapter.CaseListAdaptor;
import com.sky.app.ui.custom.FullyGridLayoutManager;
import com.sky.app.ui.custom.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商户个人中心
 */
public class  SellerCenterActivity extends BaseViewActivity<SellerContract.ISellerCenterPresenter>
    implements SellerContract.ISellerCenterView, ShopContract.ICollectionView,
        SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_case_list_rv)
    LuRecyclerView mRecyclerView;
    CaseListAdaptor caseListAdaptor;
    List<Case> cases = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
//
//    @BindView(R.id.shop_background)
//    ImageView shopBg;
    @BindView(R.id.shop_img)
    ImageView shopImg;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.shop_collect)
    Button button;
    @BindView(R.id.app_main_sell)
    TextView mainSell;
    @BindView(R.id.app_design_idea)
    TextView designIdea;
    @BindView(R.id.app_prof)
    TextView appProf;
    @BindView(R.id.app_award)
    TextView appAward;
    @BindView(R.id.app_work)
    TextView appWork;
//    @BindView(R.id.qq)
//    TextView qq;
//    @BindView(R.id.weixin)
//    TextView weixin;

    @BindView(R.id.app_user_photo_icon)
    CircleImageView circleImageView;
    @BindView(R.id.app_user_name)
    TextView userName;
    @BindView(R.id.app_comment_time)
    TextView commentTime;
    @BindView(R.id.app_comment_txt)
    TextView commentTxt;
    @BindView(R.id.quality_rating_bar)
    RatingBar qualityRatingBar;
    @BindView(R.id.service_rating_bar)
    RatingBar serviceRatingBar;
    @BindView(R.id.speed_rating_bar)
    RatingBar speedRatingBar;
    @BindView(R.id.app_comment_ll)
    LinearLayout commentLL;

    @BindView(R.id.app_collect)
    TextView appCollect;

    private String seller_id;
    private ShopContract.ICollectionPresenter iCollectionPresenter;
    private ProductIntroduceOut productIntroduceOut;
    CaseIn caseIn = new CaseIn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_seller_center);
    }

    @Override
    protected SellerContract.ISellerCenterPresenter presenter() {
        iCollectionPresenter = new CollectPresenter(context, this);
        return new SellerCenterPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(context, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppUtils.dip2px(context, 18), false));

        caseListAdaptor = new CaseListAdaptor(context, cases);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(caseListAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getPresenter().hasMore()){
                    getPresenter().loadMore(caseIn);
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent ,android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部加载完", "网络不给力啊，点击再试一次吧");

        onRefresh();
    }

    @Override
    protected void init() {
        seller_id = getIntent().getStringExtra("seller_id");
        caseIn.setUser_id(seller_id);
        title.setText(R.string.app_seller_center_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void showSuccess(ProductIntroduceOut productIntroduceOut) {
        this.productIntroduceOut = productIntroduceOut;
//        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPersonal_pic(), shopBg,
//                R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(), shopImg,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        appName.setText(productIntroduceOut.getNick_name());
        if (productIntroduceOut.getIs_collect() == 1){
            button.setText("已收藏");
            appCollect.setText("已收藏");
            AppUtils.changeTextViewIcon(context, appCollect, R.mipmap.app_product_detail_selected_star_icon, 2);
        }else{
            button.setText("收藏");
            appCollect.setText("收藏");
            AppUtils.changeTextViewIcon(context, appCollect, R.mipmap.app_product_detail_star_icon, 2);
        }
        mainSell.setText(productIntroduceOut.getMain_business_desc());
        designIdea.setText(productIntroduceOut.getDesign_concept());
        appProf.setText(productIntroduceOut.getSchooling_professional());
        appAward.setText(productIntroduceOut.getAwards_honor());
        appWork.setText(productIntroduceOut.getRepresentative_works());
//        qq.setText(productIntroduceOut.getQq());
//        weixin.setText(productIntroduceOut.getWeixin());
    }


    @Override
    public void responseComment(CommentResponse commentResponse) {
        if (null == commentResponse){
            commentLL.setVisibility(View.GONE);
            return;
        }
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
    public void getRefreshData(List<Case> list) {
        caseListAdaptor.add(list);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<Case> list) {
        caseListAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    private int tag = -1;
    @OnClick(R.id.shop_collect)
    void clickCollect(){
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("2");
        collectPubIn.setCollect_value(seller_id);
        if ("已收藏".equals(button.getText().toString())){
            tag = 1;
            iCollectionPresenter.cancelCollect(collectPubIn);
        } else {
            tag = 2;
            iCollectionPresenter.requestCollect(collectPubIn);
        }
    }

    @OnClick(R.id.app_collect)
    void clickCollectShop(){
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("2");
        collectPubIn.setCollect_value(seller_id);
        if ("已收藏".equals(appCollect.getText().toString())){
            tag = 3;
            iCollectionPresenter.cancelCollect(collectPubIn);
        } else {
            tag = 4;
            iCollectionPresenter.requestCollect(collectPubIn);
        }
    }

    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
        switch (tag){
            case 1:
                button.setText("收藏");
                break;
            case 2:
                button.setText("已收藏");
                break;
            case 3:
                appCollect.setText("收藏");
                AppUtils.changeTextViewIcon(context, appCollect, R.mipmap.app_product_detail_star_icon, 2);
                break;
            case 4:
                appCollect.setText("已收藏");
                AppUtils.changeTextViewIcon(context, appCollect, R.mipmap.app_product_detail_selected_star_icon, 2);
                break;
        }
    }

    @Override
    public void showCollectError(String error) {
        T.showShort(context, error);
        switch (tag){
            case 1:
                button.setText("已收藏");
                break;
            case 2:
                button.setText("收藏");
                break;
            case 3:
                appCollect.setText("已收藏");
                AppUtils.changeTextViewIcon(context, appCollect, R.mipmap.app_product_detail_selected_star_icon, 2);
                break;
            case 4:
                appCollect.setText("收藏");
                AppUtils.changeTextViewIcon(context, appCollect, R.mipmap.app_product_detail_star_icon, 2);
                break;
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

    @OnClick(R.id.app_skip_all_comment)
    void clickAllComment(){
        //跳转全部评价
        Intent i = new Intent(context, CommentListActivity.class);
        i.putExtra("id", seller_id);
        i.putExtra("is_collect", productIntroduceOut.getIs_collect());
        i.putExtra("mobile", productIntroduceOut.getMobile());
        i.putExtra("type", 1);//商户
        i.putExtra("comment_type", 1);//显示发布按钮
        i.putExtra("seller_url", productIntroduceOut.getPic_url());
        startActivity(i);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData(caseIn);

        ProductIntroduceIn productIntroduceIn = new ProductIntroduceIn();
        productIntroduceIn.setUser_id(seller_id);
        getPresenter().requestDescData(productIntroduceIn);

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setValue(seller_id);
        getPresenter().requestComment(commentRequest);
    }

    @OnClick(R.id.app_call_phone)
    void call(){
        if (TextUtils.isEmpty(productIntroduceOut.getMobile())){
            T.showShort(context, "没有找到号码");
            return;
        }
        AppUtils.callPhone(context, productIntroduceOut.getMobile());
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
