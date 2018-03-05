package com.sky.app.ui.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.CommentResponse;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.AllCommentPresenter;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.ui.activity.order.CommentActivity;
import com.sky.app.ui.adapter.AllCommentAdaptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 全部评价
 */
public class CommentListActivity extends BaseViewActivity<ShopContract.IAllCommentPresenter>
        implements ShopContract.IAllCommentView, SwipeRefreshLayout.OnRefreshListener, ShopContract.ICollectionView {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_comment_list)
    LuRecyclerView mRecyclerView;

    @BindView(R.id.all)
    RadioButton allBtn;
    @BindView(R.id.has_pic)
    RadioButton picBtn;
    @BindView(R.id.publish_comment)
    ImageView publishComment;

    AllCommentAdaptor allCommentAdaptor;
    List<CommentResponse> commentResponses = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private CommentRequest commentRequest = new CommentRequest();

    private String id;
    private int is_collect;
    private String mobile;
    private int type;
    private int comment_type;//0：不显示发布评论入口 1：显示发布入口
    private String seller_url;//卖家头像
    @BindView(R.id.comment_type)
    RadioGroup radioGroup;
    private ShopContract.ICollectionPresenter iCollectionPresenter;
    @BindView(R.id.app_collect)
    TextView collect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_all_comment);
    }

    @Override
    protected ShopContract.IAllCommentPresenter presenter() {
        iCollectionPresenter = new CollectPresenter(context, this);
        return new AllCommentPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                if (getPresenter().hasMore()) {
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
        searchCommentNum();
        onRefresh();
    }

    @Override
    protected void init() {
        title.setText(R.string.app_all_comment_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }

        // 0 订单评论 1 商户评价
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", 0);

        mobile = getIntent().getStringExtra("mobile");
        comment_type = getIntent().getIntExtra("comment_type", 0);
        seller_url = getIntent().getStringExtra("seller_url");

        //判断是否显示发布评论的入口
        if (comment_type == 1) {
            publishComment.setVisibility(View.VISIBLE);
        } else {
            publishComment.setVisibility(View.GONE);
        }
        commentRequest.setType(type);
        commentRequest.setValue(id);
        radioGroup.check(R.id.all);
        if (1 == is_collect) {
            collect.setText("已收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_selected_star_icon, 2);
        } else {
            collect.setText("收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_star_icon, 2);
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
    public void responseAllCommentNum(int all) {
        allBtn.setText("全部(" + all + ")");
    }

    @Override
    public void responsePicNum(int pic) {
        picBtn.setText("有图(" + pic + ")");
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
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        refresh();
    }

    /**
     * 搜索评论数量
     */
    private void searchCommentNum() {
        getPresenter().requestAllCommentNum(commentRequest);
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

    //点击收藏
    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
        if ("已收藏".equals(collect.getText().toString())) {
            collect.setText("收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_star_icon, 2);
        } else {
            collect.setText("已收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_selected_star_icon, 2);
        }
    }

    @Override
    public void showCollectError(String error) {
        T.showShort(context, error);
        if ("已收藏".equals(collect.getText().toString())) {
            collect.setText("已收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_selected_star_icon, 2);
        } else {
            collect.setText("收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_star_icon, 2);
        }
    }

    @OnClick(R.id.app_call_phone)
    void call() {
        if (TextUtils.isEmpty(mobile)) {
            T.showShort(context, "找不到号码");
            return;
        }
        AppUtils.callPhone(context, mobile);
    }

    /**
     * 1商品收藏；2商家收藏;3 发布收藏
     */
    @OnClick(R.id.app_collect)
    void clickCollect() {
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("1");
        collectPubIn.setCollect_value(id);
        if ("已收藏".equals(collect.getText().toString())) {
            iCollectionPresenter.cancelCollect(collectPubIn);
        } else {
            iCollectionPresenter.requestCollect(collectPubIn);
        }
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        if (TextUtils.isEmpty(commentRequest.getPic_type())) {
            getPresenter().loadData(commentRequest, 1);
        } else {
            getPresenter().loadData(commentRequest, 2);
        }
    }

    /**
     * 加载数据
     */
    private void loadData() {
        if (TextUtils.isEmpty(commentRequest.getPic_type())) {
            getPresenter().loadMore(commentRequest, 1);
        } else {
            getPresenter().loadMore(commentRequest, 2);
        }
    }

    @OnClick(R.id.publish_comment)
    void publishComment() {
        Intent r = new Intent(context, CommentActivity.class);
        r.putExtra("image", seller_url);
        r.putExtra("type", 1);//0 订单 1 商户
        r.putExtra("about_user_id", id);
        startActivity(r);
    }
}
