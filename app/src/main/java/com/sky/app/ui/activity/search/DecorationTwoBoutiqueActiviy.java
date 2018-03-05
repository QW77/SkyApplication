package com.sky.app.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationPingMian;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.presenter.SearchByDecorationCityPresenter;
import com.sky.app.ui.activity.seller.SellerCenterActivity;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.ui.activity.shop.CardActivity;
import com.sky.app.ui.adapter.SearchCustomAdapter;
import com.sky.app.ui.adapter.SearchCustomButiqueAdapter;
import com.sky.app.ui.custom.AutoHeightListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DecorationTwoBoutiqueActiviy extends BaseViewActivity<UserContract
        .ISearchByDecorationCityPresenter>
        implements UserContract.ISearchByDecorationCity,
        PullToRefreshBase.OnRefreshListener2<ScrollView> {

    @BindView(R.id.app_search_toolbar)
    Toolbar appSearchToolbar3;
    @BindView(R.id.app_edit_content)
    EditText appEditContent2;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    @BindView(R.id.boutique_listview)
    ListView boutiqueListview;
//    @BindView(R.id.boutique_scrollview)
//    PullToRefreshScrollView boutiqueScrollview;

    private String decorateCity = "";
    private int page = 1;
    private int total = -1;

    private UserBeanList userBeanList = new UserBeanList();
    private SearchCustomAdapter searchCustomAdapter;
    private DecorationBoutiqueAdapter decorationBoutiqueAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_two_boutique_activiy);
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
//        boutiqueScrollview.setOnRefreshListener(this);
    }


    @Override
    protected void init() {
        appSearchToolbar3.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        String decorateCity = getIntent().getStringExtra("three_dir_id");
        String decorative_id = decorateCity;
        getPresenter().requestBoutique(decorative_id, 1);


//        onPullDownToRefresh(boutiqueScrollview);
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
//        boutiqueScrollview.onRefreshComplete();
//        if ("0".equals(userBeanList.getAll_page() + "")) {
//            total = 3;
//        } else {
//            total = userBeanList.getAll_page();
//        }
//        if (isHaveMore()) {
//            ILoadingLayout endLabels = boutiqueScrollview.getLoadingLayoutProxy(false, true);
//            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
//            endLabels.setRefreshingLabel("正在加载...");// 刷新时
//            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
//        } else {
//            ILoadingLayout endLabels = boutiqueScrollview.getLoadingLayoutProxy(false, true);
//            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
//            endLabels.setRefreshingLabel("已经到底");// 刷新时
//            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
//        }
//
//        if (page == 1) {
//            searchCustomAdapter.refreshData(userBeanList);
//        } else {
//            searchCustomAdapter.appendData(userBeanList);
//        }
    }

    @Override
    public void showDecorationTwoLeft(SearchDecorationTwoLeft list) {

    }


    //展示精品店返回的数据
    @Override
    public void showBoutiquesuccess(final List<DecorationTwoButique.ListBean> butiqueList) {
        hideProgress();
        decorationBoutiqueAdapter = new DecorationBoutiqueAdapter(butiqueList, context);
        boutiqueListview.setAdapter(decorationBoutiqueAdapter);
        boutiqueListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String user_id = butiqueList.get(i).getUser_id();
                String  seller_id=user_id;
                Intent intent = new Intent(context, ShopCenterActivity.class);
                intent.putExtra("seller_id", seller_id);
                startActivity(intent);
            }
        });


    }

    @Override
    public void showPingPic(List<DecorationPingMian.ListBean> pinglist) {

    }


//    private boolean isHaveMore() {
//        if (page >= total) {
//            page = total;
//            return false;
//        }
//        return true;
//    }

//    private void setRefresh() {
//        if (isHaveMore()) {
//            ILoadingLayout endLabels = boutiqueScrollview.getLoadingLayoutProxy(false, true);
//            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
//            endLabels.setRefreshingLabel("正在加载...");// 刷新时
//            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
////            page++;
////            getUser();
//        } else {
//            ILoadingLayout endLabels = boutiqueScrollview.getLoadingLayoutProxy(false, true);
//            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
//            endLabels.setRefreshingLabel("已经到底");// 刷新时
//            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
//            boutiqueScrollview.onRefreshComplete();
//        }
//    }

    @OnClick(R.id.app_search_tv)
    public void searchByInput(TextView view) {
//        onPullDownToRefresh(boutiqueScrollview);
    }


//    private void getUser() {
//        SearchUser searchUser = new SearchUser();
//        searchUser.setNick_name(TextUtils.isEmpty(appEditContent2.getText().toString()) == true ? "" : appEditContent2.getText().toString());//通过用户输入的关键字
//        searchUser.setDecorative_id(decorateCity);
//        searchUser.setTp(1);
//        searchUser.setUser_level_recommend("2");
//        searchUser.setPage(page);
//        getPresenter().requestBoutique(searchUser);

//     getPresenter().getUserData(searchUser);
//    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//        page = 1;
//        getUser();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//        setRefresh();
//        ILoadingLayout endLabels = boutiqueScrollview.getLoadingLayoutProxy(false, true);
//        endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
//        endLabels.setRefreshingLabel("已经到底");// 刷新时
//        endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
//        boutiqueScrollview.onRefreshComplete();
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
