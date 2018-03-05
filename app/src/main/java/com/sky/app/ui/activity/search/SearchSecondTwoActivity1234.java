package com.sky.app.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.bean.UserBeanList1;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.presenter.SearchSecondPresent;
import com.sky.app.ui.activity.seller.SellerCenterActivity;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.ui.activity.shop.CardActivity;
import com.sky.app.ui.adapter.SearchCustomAdapter;
import com.sky.app.ui.custom.AutoHeightListView;
import com.sky.app.ui.custom.AutoHeightViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchSecondTwoActivity1234 extends BaseViewActivity<UserContract.ISearchSecondPresent>
        implements UserContract.ISearchSecond {

    @BindView(R.id.second_category_viewpager)
    AutoHeightViewPager autoHeightViewPager;
    @BindView(R.id.app_edit_content)
    EditText editText;
    @BindView(R.id.app_search_toolbar)
    Toolbar appSearchToolbar;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    @BindView(R.id.listview)
    AutoHeightListView listview;
    @BindView(R.id.scrollview)
    PullToRefreshScrollView scrollview;
    private int page = 1;
    private int total = -1;
    private String one_dir_id;
    private String two_dir_id = "";
    private SearchCustomAdapter searchCustomAdapter;
    private UserBeanList userBeanList = new UserBeanList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_second_activity1234);
    }

    @Override
    public void success(CategoryList categoryList) {
    }

    @Override
    public void userDataSuccess(final UserBeanList userBeanList) {
        scrollview.onRefreshComplete();
        if ("0".equals(userBeanList.getAll_page() + "")) {
            total = 3;
        } else {
            total = userBeanList.getAll_page();
        }
        if (isHaveMore()) {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("正在加载...");// 刷新时
            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
        } else {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("已经到底");// 刷新时
            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
        }

        if (page == 1) {
            searchCustomAdapter.refreshData(userBeanList);
        } else {
            searchCustomAdapter.appendData(userBeanList);
        }
    }

    @Override
    public void userDataSuccess1(UserBeanList1 userBeanList) {

    }

    @Override
    public void showMoreGoodShop(List<GoodShop.ListBean> goodList) {

    }

    @Override
    protected UserContract.ISearchSecondPresent presenter() {
        return new SearchSecondPresent(this, this);
    }

    @Override
    protected void init() {
        appSearchToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        userBeanList.setList(new ArrayList<UserBeanDetail>());
        searchCustomAdapter = new SearchCustomAdapter(userBeanList, this);
        listview.setAdapter(searchCustomAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (userBeanList.getList().get(position).getSeller_type()) {
                    case "店铺":
                        Intent j = new Intent(context, ShopCenterActivity.class);
                        j.putExtra("seller_id", userBeanList.getList().get(position).getUser_id());
                        startActivity(j);
                        break;
                    case "个人主页":
                        Intent i = new Intent(context, SellerCenterActivity.class);
                        i.putExtra("seller_id", userBeanList.getList().get(position).getUser_id());
                        startActivity(i);
                        break;
                    case "名片":
                        Intent k = new Intent(context, CardActivity.class);
                        k.putExtra("seller_id", userBeanList.getList().get(position).getUser_id());
                        startActivity(k);
                        break;
                }
            }
        });
        one_dir_id = getIntent().getStringExtra("one_dir_id");
        two_dir_id = getIntent().getStringExtra("two_dir_id");
        getUser();
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void initViewsAndEvents() {
        appSearchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        scrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                page = 1;
                getUser();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                setRefresh();
            }
        });
    }



    @OnClick(R.id.app_search_tv)
    public void searchByInput() {
        getUser();
    }

    /**
     * 搜索
     */
    private void getUser() {
        SearchUser searchUser = new SearchUser();
        searchUser.setNick_name(editText.getText().toString().trim());//通过用户输入的关键字
        searchUser.setPage(page);
        searchUser.setTwo_dir_id(two_dir_id);
        searchUser.setOne_dir_id(one_dir_id);
        searchUser.setTp(0);
        getPresenter().getUserData(searchUser);
    }

    private void setRefresh() {
        if (isHaveMore()) {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("正在加载...");// 刷新时
            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
            page++;
            getUser();
        } else {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("已经到底");// 刷新时
            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
            scrollview.onRefreshComplete();
        }
    }

    private boolean isHaveMore() {
        if (page >= total) {
            page = total;
            return false;
        }
        return true;
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
