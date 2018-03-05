package com.sky.app.ui.activity.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SearshDecorationLongAndlatitude;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.presenter.SearchByDecorationCityTwoPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DecorationTwoShopActivity extends BaseViewActivity<UserContract
        .ISearchByDecorationCityTwoPresenter>
        implements UserContract.ISearchByDecorationCityTwo,
        PullToRefreshBase.OnRefreshListener2<ScrollView> {


    @BindView(R.id.app_search_toolbar)
    Toolbar appSearchToolbar;
    @BindView(R.id.app_edit_content)
    EditText appEditContent;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    private String decorateCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_two_shop);
        ButterKnife.bind(this);


    }

    @Override
    protected UserContract.ISearchByDecorationCityTwoPresenter presenter() {
        return new SearchByDecorationCityTwoPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
        appSearchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void init() {
        appSearchToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        //获取传进来的three_dir_id就是装饰城的编码

        String decorative_id = decorateCity;
        //请求装饰城中的活动
        getPresenter().requestZSCAction(decorative_id);

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
    public void userDataSuccess(UserBeanList categoryList) {

    }

    @Override
    public void showDecorationTwoLeft(SearchDecorationTwoLeft list) {

    }

    @Override
    public void showBoutiquesuccess(List<DecorationTwoButique.ListBean> butiqueList) {

    }

    @Override
    public void showDecorationHeadSuccess(DecorationHeadlinearDetai[] headlinears) {

    }

    @Override
    public void showDecorationLongAndLatitude(SearshDecorationLongAndlatitude longAndlatitude) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }
}
