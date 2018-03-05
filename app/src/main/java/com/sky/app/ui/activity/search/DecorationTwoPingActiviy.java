package com.sky.app.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationPingMian;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.presenter.SearchByDecorationCityPresenter;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.ui.adapter.SearchCustomAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DecorationTwoPingActiviy extends BaseViewActivity<UserContract
        .ISearchByDecorationCityPresenter>
        implements UserContract.ISearchByDecorationCity,
        PullToRefreshBase.OnRefreshListener2<ScrollView> {

    @BindView(R.id.app_search_toolbar)
    Toolbar appSearchToolbar3;
    @BindView(R.id.app_edit_content)
    EditText appEditContent2;
    @BindView(R.id.ping_listview)
    ImageView ping;
//    @BindView(R.id.ping_listview)
//    ListView ping;


    private String decorateCity = "";
    private int page = 1;
    private int total = -1;

    private UserBeanList userBeanList = new UserBeanList();
    private SearchCustomAdapter searchCustomAdapter;

    private DecorationPingAdapter decorationPingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_two_ping_mian);
    }

    @Override
    protected UserContract.ISearchByDecorationCityPresenter presenter() {
        return new SearchByDecorationCityPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
        appSearchToolbar3.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    protected void init() {
        appSearchToolbar3.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        String decorateCity = getIntent().getStringExtra("three_dir_id");
        getPresenter().requestZSCPic(decorateCity);

    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void showBannerSuccess(List<BannerInfo> list) {

    }

    @Override
    public void success(DecorationCityList categoryList) {

    }


    @Override
    public void userDataSuccess(UserBeanList userBeanList) {
    }

    @Override
    public void showDecorationTwoLeft(SearchDecorationTwoLeft list) {

    }


    //展示精品店返回的数据
    @Override
    public void showBoutiquesuccess(final List<DecorationTwoButique.ListBean> butiqueList) {

    }

    @Override
    public void showPingPic(final List<DecorationPingMian.ListBean> pinglist) {
//        decorationPingAdapter = new DecorationPingAdapter(pinglist, context);
//        ping.setAdapter(decorationPingAdapter);
//        decorationPingAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.app_search_tv)
    public void searchByInput(TextView view) {
//        onPullDownToRefresh(boutiqueScrollview);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

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
