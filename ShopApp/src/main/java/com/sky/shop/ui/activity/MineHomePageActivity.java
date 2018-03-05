package com.sky.shop.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.MineShopCenterPresenter;
import com.sky.shop.ui.activity.shop.SafeCenterActivity;
import com.sky.shop.ui.activity.shop.ShopDataActivity;
import com.sky.shop.ui.activity.user.AccountActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 */
@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
public class MineHomePageActivity extends BaseViewActivity<ShopContract.IMineShopCenterPresenter>
        implements ShopContract.IMineShopCenterView{

    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.app_picture)
    ImageView appPicture;
    @BindView(R.id.app_sider_title)
    TextView siderTitle;

    @BindView(R.id.app_content_wv)
    WebView wv;
    @BindView(R.id.app_myProgressBar)
    ProgressBar progress;//进度条
    private Handler handler = new Handler();//延时UI

    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_card_main);
    }

    @Override
    protected ShopContract.IMineShopCenterPresenter presenter() {
        return new MineShopCenterPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected void init() {
        title.setText(R.string.add_home_page_string);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        initWebview();
        wv.loadUrl("http://api.app.51craftsman.com/h5_shop/shop_decoration?user_id=" +
                UserBean.getInstance().getCacheUid());
    }

    /**
     * 设置webview
     */
    private void initWebview() {
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest webResourceRequest) {
                view.loadUrl(webResourceRequest.getUrl().toString());
                return true;
            }
        });
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setLoadsImagesAutomatically(true);//自动加载图片
        webSettings.setAppCacheEnabled(false);//是否使用应用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        wv.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (null != progress){
                    progress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);

                if (null != progress){
                    if (newProgress >= 100) {
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                progress.setVisibility(View.GONE);
                            }
                        }, 200);
                    } else {
                        if (progress.getVisibility() == View.GONE){
                            progress.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {
        if (null != wv){
            wv.destroy();
        }
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
            if (!wv.canGoBack()) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    T.showShort(context, "再按一次退出应用");
                    exitTime = System.currentTimeMillis();
                } else {
                    BaseAppManager.getInstance().AppExit(context);
                }
            } else {
                wv.goBack();
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

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().queryUserInfo();
    }

    @Override
    public void responseUserInfo(UserBean userBean) {
        //初始化数据
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(),
                appPicture, R.mipmap.app_default_photo, R.mipmap.app_default_icon);
        siderTitle.setText(UserBean.getInstance().getUserCache().getNick_name());
    }
}
