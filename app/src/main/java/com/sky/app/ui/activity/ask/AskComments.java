package com.sky.app.ui.activity.ask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AskFilter;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.CircleImageView;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.presenter.askpresenter.AskAllCommentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tencent.open.utils.Global.getSharedPreferences;

/**
 * 问一问的评论界面第一个界面
 */
public class AskComments extends BaseViewActivity<ShopContract.IAskAllComentsPresenter>
        implements ShopContract.IAskAllCommentView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.publish_title_tou)
    TextView myaskhead;
    @BindView(R.id.publish_normal_toolbar)
    Toolbar myasktooblar;
    @BindView(R.id.ask_comment_rc)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.ask_coment_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.ask_second_comment)
    TextView askSecondComment;
    @BindView(R.id.ask_second_prise)
    LinearLayout appprise;

    @BindView(R.id.second_head)
    CircleImageView head;
    @BindView(R.id.second_coment)
    TextView coment;
    @BindView(R.id.ask_all)
    TextView all;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.tv_ask_comment)
    TextView tvAskComment;
    AskCommentAdapter allCommentAdaptor;
    List<AskSecondComent.ListBean> commentResponses = new ArrayList<>();
    AskFilter askFilter = new AskFilter();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;

    private String eval_id;
    private String id;
    private String user_id;
    private String eval_comments;
    private String eval_url;
    private String cacheUid;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_comments);
        ButterKnife.bind(this);
        sp = getSharedPreferences("is_collect", Context.MODE_PRIVATE);
//        sp.edit().putString("is_collect","点赞").commit();
        String is_collect = sp.getString("is_collect", null);
        if (is_collect.equals("点赞")) {
            Drawable drawable = getResources().getDrawable(R.mipmap.app_ask_6);
            ivComment.setImageDrawable(drawable);
            tvAskComment.setText("点赞");
            tvAskComment.setTextColor(getResources().getColor(R.color.sky_color_black));
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.ask_prise_1);
            ivComment.setImageDrawable(drawable);
            tvAskComment.setText("已点赞");
            tvAskComment.setTextColor(getResources().getColor(R.color.dark_blue));
        }
    }

    @Override
    protected ShopContract.IAskAllComentsPresenter presenter() {

        return new AskAllCommentPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        myasktooblar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 1),
                AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));

        allCommentAdaptor = new AskCommentAdapter(context, commentResponses);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(allCommentAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
//        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                if (getPresenter().hasMore()) {
//                    getPresenter().loadMore("0", eval_id, id, askFilter);
//                } else {
//                    mRecyclerView.setNoMore(true);
//                }
//            }
//        });

        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent, android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部加载完", "网络不给力啊，点击再试一次吧");


    }

    @Override
    protected void init() {
        eval_id = getIntent().getStringExtra("eval_id");
        id = getIntent().getStringExtra("id");
        user_id = getIntent().getStringExtra("user_id");
        eval_comments = getIntent().getStringExtra("eval_comments");
        eval_url = getIntent().getStringExtra("eval_url");


        myaskhead.setText(user_id);
        coment.setText(eval_comments);
        ImageHelper.getInstance().displayDefinedImage(eval_url, head, R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        myasktooblar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
//        if (1 == is_collect) {
//            appprise.setText("已点赞");
//            AppUtils.changeTextViewIcon(context, appprise, R.mipmap.ask_prise_1, 2);
//        } else {
//            appprise.setText("点赞");
//            AppUtils.changeTextViewIcon(context, appprise, R.mipmap.app_ask_6, 2);
//        }
//        getPresenter().AskrequestComment("0", eval_id, id);

        getPresenter().loadData("0", eval_id, id, this.askFilter);


    }

    @Override
    protected void onDestoryActivity() {

    }


    @OnClick(R.id.ask_second_comment)
    void askComment() {
        Intent r = new Intent(context, AskCommentActivity.class);
        r.putExtra("id", id);
        r.putExtra("eval_id", eval_id);
        startActivity(r);
    }

    //点赞的事件
    @OnClick(R.id.ask_second_prise)
    void askSecondPrise() {
        cacheUid = UserBean.getInstance().getCacheUid();
        String is_collect = sp.getString("is_collect", null);
        if (is_collect.equals("点赞")) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ask_prise_1);
            ivComment.setImageDrawable(drawable);
            tvAskComment.setText("已点赞");
            tvAskComment.setTextColor(getResources().getColor(R.color.dark_blue));
            getPresenter().requestAskCancelPrise(id, cacheUid);
            sp.edit().putString("is_collect","已点赞").commit();
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.app_ask_6);
            ivComment.setImageDrawable(drawable);
            tvAskComment.setText("点赞");
            tvAskComment.setTextColor(getResources().getColor(R.color.sky_color_black));
            //取消点赞数
            getPresenter().requestAskCancelPrise(id, cacheUid);
            sp.edit().putString("is_collect","点赞").commit();
        }

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData("0", eval_id, id, askFilter);
    }

    @Override
    public void getRefreshData1(List<AskSecondComent.ListBean> list, int num) {
        all.setText("评论(" + num + ")");
        allCommentAdaptor.add(list);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData1(List<AskSecondComent.ListBean> list) {
        allCommentAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void showAskComent(List<AskSecondComent.ListBean> list) {

//        allCommentAdaptor.addAll(list);
//        mSwipeRefreshLayout.setRefreshing(false);
//        mRecyclerView.refreshComplete(20);
//        mLuRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showMyAsk(List<MyAsk.ListBean> list) {

    }

    @Override
    public void showMyAnser(List<MyAnser.ListBean> list) {

    }


    @Override
    public void showCollectView(String msg) {
//        T.showShort(context, msg);
    }

}
