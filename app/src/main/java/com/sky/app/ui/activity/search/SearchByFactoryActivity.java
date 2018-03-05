package com.sky.app.ui.activity.search;

//import com.sky.app.R;
//import com.sky.app.bean.Category;
//import com.sky.app.bean.CategoryList;
//import com.sky.app.bean.SearchUser;
//import com.sky.app.bean.UserBeanList;
//import com.sky.app.contract.UserContract;
//import com.sky.app.library.base.ui.BaseViewActivity;
//import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
//import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
//import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
//import com.sky.app.library.utils.DialogUtils;
//import com.sky.app.presenter.SearchByFactoryActivityPresenter;
//import com.sky.app.ui.adapter.SearchCustomAdapter;
//import com.sky.app.ui.custom.AutoHeightGridView;
//import com.sky.app.ui.custom.AutoHeightListView;
import com.sky.app.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.app.bean.Category;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.presenter.SearchByFactoryActivityPresenter;
import com.sky.app.ui.adapter.SearchCustomAdapter;
import com.sky.app.ui.custom.AutoHeightGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 根据生产厂家查找
 */
public class SearchByFactoryActivity extends BaseViewActivity<UserContract
        .ISearchByFactoryPresenter>
        implements UserContract.ISearchByFactory,
        PullToRefreshBase.OnRefreshListener2<ScrollView>  {
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
    private int page=1;
    private int total=-1;
    private ArrayList<String> factoryName=new ArrayList<>();
    private String factory_id = "";
    private UserBeanList userBeanList;
    private SearchCustomAdapter searchCustomAdapter;
    private AreaGridviewAdapter areaGridviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_factory);
    }

    @Override
    protected UserContract.ISearchByFactoryPresenter presenter() {
        return new SearchByFactoryActivityPresenter(this, this);
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
        getPresenter().getData("");
        onPullDownToRefresh(scrollview);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void success(CategoryList categoryList) {
        final List<Category> list = categoryList.getList();
        for (Category category :list ) {
            factoryName.add(category.getIcon_image_url());
        }
        areaGridviewAdapter = new AreaGridviewAdapter(factoryName,this);
        gridview.setAdapter(areaGridviewAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                factory_id = list.get(i).getOne_dir_id();
                Intent intent = new Intent(context, SearchByFactoryTwoActivity.class);
                intent.putExtra("three_dir_id",factory_id);
                startActivity(intent);
            }
        });

    }

    @Override
    public void userDataSuccess(UserBeanList userBeanList) {
        scrollview.onRefreshComplete();

        if("0".equals(userBeanList.getAll_page()+"")){
            total=3;
        }else{
            total=userBeanList.getAll_page();
        }

        if(isHaveMore()){
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("正在加载...");// 刷新时
            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
        }else{
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("已经到底");// 刷新时
            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
        }

        if(page==1){
            searchCustomAdapter.refreshData(userBeanList);
        }else {
            searchCustomAdapter.appendData(userBeanList);
        }
    }

    @OnClick(R.id.app_search_tv)
    public  void  searchByInput(TextView  view){
        getUser();
    }


    private  boolean  isHaveMore(){
        if(page>=total){
            page=total;
            return false;
        }
        return  true;
    }

    private void setRefresh() {
        if(isHaveMore()){
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("正在加载...");// 刷新时
            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
            page++;
            getUser();
        }else{
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("已经到底");// 刷新时
            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
            scrollview.onRefreshComplete();
        }
    }

    private void getUser() {
        SearchUser searchUser = new SearchUser();
        searchUser.setNick_name(TextUtils.isEmpty(editText.getText().toString())==true?"":editText.getText().toString());//通过用户输入的关键字
        searchUser.setManufacturer_type_id(factory_id);
        searchUser.setPage(page);
        searchUser.setTp(3);
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
