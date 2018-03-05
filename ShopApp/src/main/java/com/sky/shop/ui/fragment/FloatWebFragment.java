package com.sky.shop.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.shop.R;

import butterknife.BindView;

/**
 * web页面
 * @author wjx
 *
 */
@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
public class FloatWebFragment extends BaseViewFragment {

	@BindView(R.id.app_content_wv)
	WebView wv;
	@BindView(R.id.app_myProgressBar)
	ProgressBar progress;//进度条

	private String url, titleName;//url路径/标题
	private Handler handler = new Handler();//延时UI

	@Override
	protected void initViewsAndEvents() {
		initWebview();
		wv.loadUrl(url);
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.app_web;
	}

	@Override
	protected void onDestoryFragment() {

	}

	@Override
	protected IBasePresenter presenter() {
		return null;
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
				progress.setProgress(newProgress);
				super.onProgressChanged(view, newProgress);
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
		});
	}

	@Override
	protected void init() {
		url = (String) getArguments().get("url");
	}
}