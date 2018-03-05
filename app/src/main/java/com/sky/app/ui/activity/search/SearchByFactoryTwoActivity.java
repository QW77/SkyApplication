package com.sky.app.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.Category;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.presenter.SearchByFactoryActivityPresenter;
import com.sky.app.ui.activity.seller.SellerCenterActivity;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.ui.activity.shop.CardActivity;
import com.sky.app.ui.adapter.SearchCustomAdapter;
import com.sky.app.ui.custom.AutoHeightGridView;
import com.sky.app.ui.custom.AutoHeightListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 根据生产厂家查找
 */
public class SearchByFactoryTwoActivity extends BaseViewActivity<UserContract
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
    @BindView(R.id.listview)
    AutoHeightListView listview;
    @BindView(R.id.scrollview)
    PullToRefreshScrollView scrollview;
    private int page=1;
    private int total=-1;
    private ArrayList<String> factoryName=new ArrayList<>();
    private String factory_id = "";
    private UserBeanList userBeanList = new UserBeanList();
    private SearchCustomAdapter searchCustomAdapter;

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
        factory_id = getIntent().getStringExtra("three_dir_id");
        getPresenter().getData(factory_id);
        onPullDownToRefresh(scrollview);

    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void success(CategoryList categoryList) {
        userBeanList.setList(new ArrayList<UserBeanDetail>());
        searchCustomAdapter = new SearchCustomAdapter(this.userBeanList, this);

        listview.setAdapter(searchCustomAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (userBeanList.getList().get(position).getSeller_type()){
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
        onPullDownToRefresh(scrollview);
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
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(this);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page=1;
        getUser();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        setRefresh();

    }
}
