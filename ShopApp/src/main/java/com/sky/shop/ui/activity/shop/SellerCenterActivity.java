package com.sky.shop.ui.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.CaseListAdaptor;
import com.sky.shop.bean.Case;
import com.sky.shop.bean.CaseIn;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.custom.AutoHeightGridView;
import com.sky.shop.presenter.SellerCenterPresenter;
import com.sky.shop.presenter.activity.MineShopCenterPresenter;
import com.sky.shop.ui.activity.RecordWebActivity;
import com.sky.shop.ui.activity.user.AccountActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商户主页
 */
public class SellerCenterActivity extends BaseViewActivity<SellerContract.ISellerCenterPresenter>
    implements SellerContract.ISellerCenterView, SwipeRefreshLayout.OnRefreshListener,
        ShopContract.IMineShopCenterView{

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_case_list_rv)
    AutoHeightGridView gridView;
    CaseListAdaptor caseListAdaptor;
    List<Case> cases = new ArrayList<>();

    @BindView(R.id.shop_background)
    ImageView shopBg;
    @BindView(R.id.shop_img)
    ImageView shopImg;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.app_main_sell)
    TextView mainSell;
    @BindView(R.id.app_design_idea)
    TextView designIdea;
    @BindView(R.id.app_prof)
    TextView appProf;
    @BindView(R.id.app_award)
    TextView appAward;
    @BindView(R.id.app_work)
    TextView appWork;
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.weixin)
    TextView weixin;

    @BindView(R.id.app_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.app_picture)
    ImageView appPicture;
    @BindView(R.id.app_sider_title)
    TextView siderTitle;

    private ProductIntroduceOut productIntroduceOut;
    private CaseIn caseIn = new CaseIn();

    ShopContract.IMineShopCenterPresenter iMineShopCenterPresenter;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_seller_center);
    }

    @Override
    protected SellerContract.ISellerCenterPresenter presenter() {
        iMineShopCenterPresenter = new MineShopCenterPresenter(context, this);
        return new SellerCenterPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        caseListAdaptor = new CaseListAdaptor(context, cases);
        gridView.setAdapter(caseListAdaptor);
    }

    @Override
    protected void init() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void showSuccess(ProductIntroduceOut productIntroduceOut) {
        this.productIntroduceOut = productIntroduceOut;
        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPersonal_pic(), shopBg,
                R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(), shopImg,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        appName.setText(productIntroduceOut.getNick_name());
        mainSell.setText(productIntroduceOut.getMain_business_desc());
        designIdea.setText(productIntroduceOut.getDesign_concept());
        appProf.setText(productIntroduceOut.getSchooling_professional());
        appAward.setText(productIntroduceOut.getAwards_honor());
        appWork.setText(productIntroduceOut.getRepresentative_works());
        qq.setText(productIntroduceOut.getQq());
        weixin.setText(productIntroduceOut.getWeixin());
    }

    @Override
    public void getRefreshData(List<Case> list) {
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        cases.clear();
        cases.addAll(list);
        caseListAdaptor.notifyDataSetChanged();
    }

    @Deprecated
    @Override
    public void responseEditShopInfo(String msg) {

    }

    @Deprecated
    @Override
    public void responseAddCase(String msg) {

    }

    @Deprecated
    @Override
    public void responseEditCase(String msg) {

    }

    @Deprecated
    @Override
    public void responseDelCase(String msg) {

    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getPresenter().loadData(caseIn);
        getPresenter().requestDescData();
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

    @OnClick(R.id.main_edit)
    void mainEdit(){
        startActivity(new Intent(context, SellerCenterEditActivity.class));
    }

    @OnClick(R.id.case_edit)
    void caseEdit(){
        startActivity(new Intent(context, CaseListActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        iMineShopCenterPresenter.queryUserInfo();
        onRefresh();
    }

    @Override
    public void responseUserInfo(UserBean userBean) {
        //初始化数据
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(),
                appPicture, R.mipmap.app_default_photo, R.mipmap.app_default_icon);
        siderTitle.setText(UserBean.getInstance().getUserCache().getNick_name());
    }

    /**
     * 关闭drawer
     */
    private boolean closeDrawer(){
        if (null != drawerLayout && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.showShort(context, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                BaseAppManager.getInstance().AppExit(context);
            }
        }
    }

    @OnClick(R.id.app_shop_center)
    void clickShopCenter(){
        startActivity(new Intent(context, ShopDataActivity.class));
    }

    @OnClick(R.id.app_account_center)
    void clickAccountCenter(){
        closeDrawer();
        startActivity(new Intent(context, AccountActivity.class));
    }

    @OnClick(R.id.app_browser_record)
    void clickBrowserRecord(){
        closeDrawer();
        Intent i = new Intent(context, RecordWebActivity.class);
        UserBean.getInstance().getCacheUid();
        i.putExtra("url", "http://api.app.51craftsman.com/h5_shop/un/browse_record?user_id=" +
                UserBean.getInstance().getCacheUid());
        startActivity(i);
    }

    @OnClick(R.id.app_safe_center)
    void clickSafeCenter(){
        closeDrawer();
        startActivity(new Intent(context, SafeCenterActivity.class));
    }

    @OnClick(R.id.app_shop_icon)
    void clickLeft(){
        if (!closeDrawer()){
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
