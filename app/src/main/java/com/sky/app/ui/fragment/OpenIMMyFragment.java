package com.sky.app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.sky.app.ui.activity.messageandnotice.SessionListFragment;
import com.sky.app.ui.activity.publish.PublishDetailActivity;
import com.sky.app.ui.adapter.MessageOpenAdapter;
import com.sky.app.ui.adapter.PublishAdaptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 消息界面
 * Created by Administrator on 2017/2/30.
 *
 * 
 *
 */
public class OpenIMMyFragment extends BaseViewFragment<PublishContract.IPublishPresenter>
        implements PublishContract.IPublishView, SwipeRefreshLayout.OnRefreshListener, ShopContract.ICollectionView {

    //标记
    final static public int INVALID = -1;
    final static public int SESSION = 0;
    final static public int CONTACT = 1;
//    @BindView(R.id.open_swipe_refresh_layout)
//    SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.open_list)
//    LuRecyclerView mRecyclerView;
    //消息
    @BindView(R.id.session_tab)
    RelativeLayout sessionTab;
    @BindView(R.id.tv_session)
    TextView tvSession;
    @BindView(R.id.iv_session_point)
    ImageView ivSessionPoint;
    //通知
    @BindView(R.id.contact_tab)
    RelativeLayout contactTab;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.iv_contact_point)
    ImageView ivContactPoint;

    //    @BindView(R.id.function_show)
//    ImageView functionShow;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.fl_container)
    LinearLayout flContainer;
    Unbinder unbinder;


    private ShopContract.ICollectionPresenter iCollectionPresenter;
    private MessageOpenAdapter messageOpenAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected void init(){
    }


    @Override
    protected void initViewsAndEvents() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.addItemDecoration(
//                new DashlineItemDivider(context,
//                        AppUtils.dip2px(context, 12), AppUtils.dip2px(context, 12)));
//        messageOpenAdapter = new MessageOpenAdapter(getFragmentManager(), context);
//        viewpager.setAdapter(messageOpenAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        sessionTab.setBackground(getResources().getDrawable(R.drawable.shape_tab_left_check));
                        tvSession.setTextColor(getResources().getColor(R.color.main_colorPrimary));
                        contactTab.setBackground(getResources().getDrawable(R.drawable.shape_tab_right_uncheck));
                        tvContact.setTextColor(getResources().getColor(R.color.aliwx_bg_color_white));
                        break;
                    case 1:
                        sessionTab.setBackground(getResources().getDrawable(R.drawable.shape_tab_left_uncheck));
                        tvSession.setTextColor(getResources().getColor(R.color.aliwx_bg_color_white));
                        contactTab.setBackground(getResources().getDrawable(R.drawable.shape_tab_right_check));
                        tvContact.setTextColor(getResources().getColor(R.color.main_colorPrimary));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//       publishAdaptor = new PublishAdaptor(context, supplyDetailArrayList);
//        publishAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(View itemView, int pos) {
//                Intent i = new Intent(context, PublishDetailActivity.class);
//                i.putExtra("product_id", supplyDetailArrayList.get(pos).getProduct_id());
//                getActivity().startActivity(i);
//            }
//
//        });

//        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(publishAdaptor);
//        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
//        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                if (getPresenter().hasMore()){
//                    getPresenter().loadMore(supplyFilter);
//                } else {
//                    mRecyclerView.setNoMore(true);
//                }
//            }
//        });

        //设置底部加载颜色
//        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent ,android.R.color.white);
//        //设置底部加载文字提示
//        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部加载完", "网络不给力啊，点击再试一次吧");
//
//        //采购商
//        buy();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my_open;
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
//        mSwipeRefreshLayout.setRefreshing(true);
//        mRecyclerView.setRefreshing(true);
//        getPresenter().loadData(supplyFilter);
    }

    @Override
    public void getRefreshData(List<SupplyDetail> list) {
//        publishAdaptor.add(list);
//        if (mSwipeRefreshLayout.isRefreshing()) {
//            mSwipeRefreshLayout.setRefreshing(false);
//        }
//        mRecyclerView.refreshComplete(20);
//        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<SupplyDetail> list) {
//        publishAdaptor.addAll(list);
//        mSwipeRefreshLayout.setRefreshing(false);
//        mRecyclerView.refreshComplete(20);
//        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
//        onRefresh();
    }

    @Override
    public void showCollectError(String error) {
        T.showShort(context, error);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(getActivity());
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.session_tab, R.id.contact_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.session_tab:
//                tvContact.setTextColor(getResources().getColor(R.color.white));
//                viewpager.setCurrentItem(IMTab.RECENT_CONTACTS.tabIndex);
                sessionTab.setBackground(getResources().getDrawable(R.drawable.shape_tab_left_check));
                tvSession.setTextColor(getResources().getColor(R.color.main_colorPrimary));
                contactTab.setBackground(getResources().getDrawable(R.drawable.shape_tab_right_uncheck));
                tvContact.setTextColor(getResources().getColor(R.color.aliwx_bg_color_white));

                break;
            case R.id.contact_tab:
//                sessionTab.setBackground(getResources().getDrawable(R.drawable.shape_tab_left_uncheck));
//                tvSession.setTextColor(getResources().getColor(R.color.white));
//                contactTab.setBackground(getResources().getDrawable(R.drawable.shape_tab_right_check));
//                tvContact.setTextColor(getResources().getColor(R.color.colorPrimary));
//                viewpager.setCurrentItem(IMTab.CONTACT.tabIndex);

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}