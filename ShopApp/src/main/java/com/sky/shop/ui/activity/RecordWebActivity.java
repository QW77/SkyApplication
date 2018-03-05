package com.sky.shop.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.T;
import com.sky.shop.R;

import butterknife.BindView;

/**
 * web页面
 * @author wjx
 *
 */
@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
public class RecordWebActivity extends BaseViewActivity {

	@BindView(R.id.normal_toolbar)
	Toolbar toolbar;
	@BindView(R.id.app_title)
	TextView title;

	@BindView(R.id.app_content_wv)
	WebView wv;
	@BindView(R.id.app_myProgressBar)
	ProgressBar progress;//进度条

	private String url;//url路径
	private boolean flag;//是否退出
	private Handler handler = new Handler();//延时UI
	private long exitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_record_web);
	}

	@Override
	protected IBasePresenter presenter() {
		return null;
	}

	@Override
	protected void initViewsAndEvents() {
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		initWebview();
		wv.loadUrl(url);
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
	protected void init() {
		title.setText(R.string.add_record_string);
		toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		flag = intent.getBooleanExtra("flag", false);
	}

	@Override
	protected void onDestoryActivity() {
		if (null != wv){
			wv.destroy();
		}
	}

	@Override
	public void onBackPressed() {
		if(!wv.canGoBack()){
			if (flag){
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					T.showShort(context, "再按一次退出应用");
					exitTime = System.currentTimeMillis();
				} else {
//            this.moveTaskToBack(true);
					BaseAppManager.getInstance().AppExit(context);
				}
			}else{
				finish();
			}

		}else{
			wv.goBack();
		}
	}
}