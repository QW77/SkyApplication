package com.sky.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.bean.SupplyFilter;
import com.sky.app.contract.PublishContract;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.DashlineItemDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.presenter.PublishPresenter;
import com.sky.app.ui.activity.publish.PublishActivity;
import com.sky.app.ui.activity.publish.PublishDetailActivity;
import com.sky.app.ui.adapter.PublishAdaptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 发布主页
 * Created by Administrator on 2017/2/30.
 */
public class OrderManageFragment extends BaseViewFragment<PublishContract.IPublishPresenter>
        implements PublishContract.IPublishView, SwipeRefreshLayout.OnRefreshListener, ShopContract.ICollectionView {

    @BindView(R.id.app_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_publish_list)
    LuRecyclerView mRecyclerView;

    PublishAdaptor publishAdaptor;

    List<SupplyDetail> supplyDetailArrayList = new ArrayList<>();
    SupplyFilter supplyFilter = new SupplyFilter();

    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;

    /*删选*/
    @BindView(R.id.app_bg_layout)
    View bg;
    @BindView(R.id.app_time_filter_layout)
    LinearLayout filterLayout;
    @BindView(R.id.app_begin_time)
    TextView beginTime;
    @BindView(R.id.app_end_time)
    TextView endTime;
    @BindView(R.id.app_confirm_tv)
    TextView confirm;
    Animation inAnim, outAnim, maskOut;
    @BindView(R.id.app_buy_tv)
    TextView buyTv;
    @BindView(R.id.app_supply_tv)
    TextView supplyTv;
    @BindView(R.id.app_edit_content)
    EditText searchContent;

    private ShopContract.ICollectionPresenter iCollectionPresenter;

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
        initAnim();
        bg.setVisibility(View.GONE);
        filterLayout.setVisibility(View.GONE);
    }

    /**
     * 初始化窗口动画
     */
    public void initAnim(){
        inAnim = AnimationUtils.loadAnimation(context, R.anim.app_dropdown_in);
        outAnim = AnimationUtils.loadAnimation(context, R.anim.app_dropdown_out);
        maskOut = AnimationUtils.loadAnimation(context, R.anim.app_dropdown_mask_out);
    }

    @Override
    protected void initViewsAndEvents() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new DashlineItemDivider(context,
                        AppUtils.dip2px(context, 12), AppUtils.dip2px(context, 12)));

        publishAdaptor = new PublishAdaptor(context, supplyDetailArrayList);
        publishAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int pos) {
                Intent i = new Intent(context, PublishDetailActivity.class);
                i.putExtra("product_id", supplyDetailArrayList.get(pos).getProduct_id());
                getActivity().startActivity(i);
            }

        });
        publishAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
                CollectPubIn collectPubIn = new CollectPubIn();
                collectPubIn.setCollect_value(supplyDetailArrayList.get(pos).getProduct_id());
                collectPubIn.setType("3");
                iCollectionPresenter.requestCollect(collectPubIn);
            }
        });
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(publishAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getPresenter().hasMore()){
                    getPresenter().loadMore(supplyFilter);
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent ,android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部加载完", "网络不给力啊，点击再试一次吧");

        //采购商
        buy();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_home_publish;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected PublishContract.IPublishPresenter presenter() {
        iCollectionPresenter = new CollectPresenter(context, this);
        return new PublishPresenter(context, this);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData(supplyFilter);
    }

    @Override
    public void getRefreshData(List<SupplyDetail> list) {
        publishAdaptor.add(list);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<SupplyDetail> list) {
        publishAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void showCollectError(String error) {
        T.showShort(context, error);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.app_search_tv)
    void search(){
        supplyFilter.setProduct_name(searchContent.getText().toString());
        onRefresh();
    }

    @OnClick(R.id.app_buy_tv)
    void buy(){
        supplyFilter.setProduct_type("0");
        buyTv.setTextColor(AppUtils.getSystemColor(context, R.color.main_colorPrimary));
        supplyTv.setTextColor(AppUtils.getSystemColor(context, R.color.sky_color_5d5d5d));
        onRefresh();
    }
    @OnClick(R.id.app_supply_tv)
    void supply(){
        supplyFilter.setProduct_type("1");
        supplyTv.setTextColor(AppUtils.getSystemColor(context, R.color.main_colorPrimary));
        buyTv.setTextColor(AppUtils.getSystemColor(context, R.color.sky_color_5d5d5d));
        onRefresh();
    }
    boolean isShow = false;
    @OnClick(R.id.app_time_tv)
    void time(){
        if (isShow) return;
        bg.clearAnimation();
        bg.setVisibility(View.VISIBLE);
        filterLayout.clearAnimation();
        filterLayout.startAnimation(inAnim);
        filterLayout.setVisibility(View.VISIBLE);
        this.isShow = true;
    }
    @OnClick(R.id.app_bg_layout)
    void bgLayout(){
        filterLayout.clearAnimation();
        filterLayout.startAnimation(outAnim);
        filterLayout.setVisibility(View.GONE);

        bg.clearAnimation();
        bg.startAnimation(maskOut);
        bg.setVisibility(View.GONE);
        this.isShow = false;
    }

    @OnClick(R.id.app_start_layout)
    void clickStartLayout(){
        DialogUtils.showDateDialog(context, new DialogUtils.IDatePickerDialog() {
            @Override
            public void showDate(String year, String month, String day) {
                beginTime.setText(year + "-" + month + "-" + day);
            }
        });
    }
    @OnClick(R.id.app_end_layout)
    void clickEndLayout(){
        DialogUtils.showDateDialog(context, new DialogUtils.IDatePickerDialog() {
            @Override
            public void showDate(String year, String month, String day) {
                endTime.setText(year + "-" + month + "-" + day);
            }
        });
    }
    @OnClick(R.id.app_confirm_tv)
    void clickConfirm(){
        bgLayout();
        supplyFilter.setStart_time(beginTime.getText().toString());
        supplyFilter.setEnd_time(endTime.getText().toString());
        onRefresh();
    }

    @OnClick(R.id.skipPublish)
    void skipPublish(){
        getActivity().startActivity(new Intent(context, PublishActivity.class));
    }
}