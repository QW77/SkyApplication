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
import com.sky.app.bean.Area;
import com.sky.app.bean.AreaList;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.SearchByPlaceActivityPresenter;
import com.sky.app.ui.adapter.SearchCustomAdapter;
import com.sky.app.ui.custom.AutoHeightGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 地区查找
 */
public class SearchByPlaceNewActivity extends BaseViewActivity<UserContract.ISearchByPlacePresenter>
        implements UserContract.ISearchByPlace, PullToRefreshBase.OnRefreshListener2<ScrollView> {


    @BindView(R.id.app_search_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_edit_content)
    EditText editText;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    @BindView(R.id.gridview)
    AutoHeightGridView gridview;
    //    @BindView(R.id.listview)
//    AutoHeightListView listview;
    @BindView(R.id.scrollview)
    PullToRefreshScrollView scrollview;
    private String manufacturer_type_id = "";//工厂
    private String decorateCity = "";//装饰城
    private String type = "";//类型
    private String area_id = "";//地区编号
    private SearchCustomAdapter searchCustomAdapter;
    private int page = 1;
    private int total = -1;
    private UserBeanList userBeanList;
    private ArrayList<String> placeNewName = new ArrayList<>();
    private AreaGridviewAdapter areaGridviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_place_new);
        ButterKnife.bind(this);
    }

    @Override
    protected UserContract.ISearchByPlacePresenter presenter() {
        return new SearchByPlaceActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getPresenter().getData("");
//        getPresenter().getFirstCatogoryData();
        onPullDownToRefresh(scrollview);

    }

    @Override
    public void initViewsAndEvents() {
//        //一级分类
//        firstCategoryAdapter = new FirstCategoryAdapter(context, firstCategoryDetailList);
//        gridView.setAdapter(firstCategoryAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                type = firstCategoryDetailList.get(i).getOne_dir_id();
//                onPullDownToRefresh(scrollview);
//            }
//        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        scrollview.setOnRefreshListener(this);
    }

    private void getUser() {
        SearchUser searchUser = new SearchUser();
        searchUser.setNick_name(TextUtils.isEmpty(editText.getText().toString()) == true ? "" : editText.getText().toString());//通过用户输入的关键字
        searchUser.setDecorative_id(decorateCity);
        searchUser.setTp(2);
        searchUser.setTwo_dir_id(type);
        searchUser.setArea_id(area_id);
        searchUser.setPage(page);
        getPresenter().getUserData(searchUser);
    }

    private boolean isHaveMore() {
        if (page >= total) {
            page = total;
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

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        Log.e("搜索界面请求", "失败了失败了");
    }

    @Override
    protected void onDestoryActivity() {

    }

//    private ArrayList<String> areaName = new ArrayList<>();


    @Override
    public void successDecrationCity(DecorationCityList categoryList) {
//        setDecorationCityCategory(categoryList);

    }

    @Override
    public void success(AreaList areaList) {
//        setAreaCategory(areaList);
        final List<Area> list = areaList.getList();
        for (Area category : list) {
            placeNewName.add(category.getIcon_image_url());
        }
        areaGridviewAdapter = new AreaGridviewAdapter(placeNewName, this);
        gridview.setAdapter(areaGridviewAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                area_id = list.get(i).getTwo_dir_id();
//                decorationCityName.clear();
//                decorationCityName.add(list.get(i).getIcon_image_url());
//                areaGridviewAdapter.notifyDataSetChanged();
//                onPullDownToRefresh(scrollview);
                Intent intent = new Intent(context, SearchByDecorationCityActivity.class);
                intent.putExtra("three_dir_id", area_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void firstCatogoryDataSuccess(List<FirstCategoryDetail> list) {
    }

    @Override
    public void secondCatogoryDataSuccess(CategoryList categoryList) {
        categoryList.toString();
    }

    /**
     * 刷新
     *
     * @param userBeanList
     */
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

    @OnClick(R.id.app_search_tv)
    public void searchByInput() {
        getUser();
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
