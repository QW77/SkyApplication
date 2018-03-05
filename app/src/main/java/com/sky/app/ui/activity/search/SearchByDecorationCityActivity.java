package com.sky.app.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationCity;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationPingMian;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.presenter.SearchByDecorationCityPresenter;
import com.sky.app.ui.adapter.SearchCustomAdapter;
import com.sky.app.ui.custom.AutoHeightGridView;
import com.sky.app.ui.custom.AutoHeightListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 根据装饰城查找查找
 */
public class SearchByDecorationCityActivity extends BaseViewActivity<UserContract
        .ISearchByDecorationCityPresenter>
        implements UserContract.ISearchByDecorationCity,
        PullToRefreshBase.OnRefreshListener2<ScrollView> {

    @BindView(R.id.app_edit_content)
    EditText editText;
    @BindView(R.id.app_search_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    @BindView(R.id.gridview)
    AutoHeightGridView gridview;
//    @BindView(R.id.listview)
//    AutoHeightListView listview;
    @BindView(R.id.scrollview)
    PullToRefreshScrollView scrollview;

    private String decorateCity = "";
    private int page = 1;
    private int total = -1;
    private ArrayList<String> decorationCityName = new ArrayList<>();
    private SearchCustomAdapter searchCustomAdapter;
    private AreaGridviewAdapter areaGridviewAdapter;
    private UserBeanList userBeanList = new UserBeanList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_decoration_city);
    }

    @Override
    protected UserContract.ISearchByDecorationCityPresenter presenter() {
        return new SearchByDecorationCityPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        scrollview.setOnRefreshListener(this);
    }



    @Override
    protected void init() {
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        String three_dir_id = getIntent().getStringExtra("three_dir_id");
        getPresenter().getData(three_dir_id);
        onPullDownToRefresh(scrollview);
    }

    @Override
    protected void onDestoryActivity() {

    }


    @Override
    public void showBannerSuccess(List<BannerInfo> list) {

    }

    @Override
    public void success(final DecorationCityList categoryList) {
        final List<DecorationCity> list = categoryList.getList();
        for (DecorationCity category : list) {
            category.getIcon_image_url().trim();
            decorationCityName.add(category.getIcon_image_url());
        }
        areaGridviewAdapter = new AreaGridviewAdapter(decorationCityName, this);
        gridview.setAdapter(areaGridviewAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                decorateCity = list.get(i).getThree_dir_id();
                Intent intent = new Intent();
                intent.setClass(context,SearchByDecorationCityTwoActivity.class);
                intent.putExtra("three_dir_id",decorateCity);
                intent.putExtra("one_dir_id",list.get(i).getOne_dir_id());
                startActivity(intent);
            }
        });
    }


    @Override
    public void userDataSuccess(UserBeanList userBeanList) {
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
    public void showDecorationTwoLeft(SearchDecorationTwoLeft list) {

    }

    @Override
    public void showBoutiquesuccess(List<DecorationTwoButique.ListBean> butiqueList) {

    }

    @Override
    public void showPingPic(List<DecorationPingMian.ListBean> pinglist) {

    }


    private boolean isHaveMore() {
        if (page >= total) {
            page = total;
            Log.e("123456",page+"");
            return false;
        }
        return true;
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

    @OnClick(R.id.app_search_tv)
    public void searchByInput(TextView view) {
        onPullDownToRefresh(scrollview);
    }



    private void getUser() {
        SearchUser searchUser = new SearchUser();
        searchUser.setNick_name(TextUtils.isEmpty(editText.getText().toString()) == true ? "" : editText.getText().toString());//通过用户输入的关键字
        searchUser.setDecorative_id(decorateCity);
        searchUser.setTp(1);
        searchUser.setPage(page);
        getPresenter().getUserData(searchUser);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page = 1;
        getUser();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        setRefresh();
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
