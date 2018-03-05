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
public class CommentAskActivity extends BaseViewActivity<ShopContract.IAllCommentPresenter>
        implements ShopContract.IAllCommentView, SwipeRefreshLayout.OnRefreshListener
     {

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

    @BindView(R.id.comment_type)
    RadioGroup radioGroup;
    private ShopContract.ICollectionPresenter iCollectionPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_ask_comment);
    }

    @Override
    protected ShopContract.IAllCommentPresenter presenter() {

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
//        T.showShort(context, error);
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

}
