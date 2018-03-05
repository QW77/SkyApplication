package com.sky.app.utils;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.sky.app.library.utils.T;

/**
 * apk下载管理
 * @author wjx
 *
 */
@SuppressLint("NewApi")
public class ApkDownloadManager {
	private static ApkDownloadManager apkDownloadManager;
	private DownloadManager dManager;
	private Context context;
	
	private ApkDownloadManager(Context context){
		this.context = context;
		this.dManager = (DownloadManager) this.context.getSystemService(Context.DOWNLOAD_SERVICE);
	}
	
	public static ApkDownloadManager getInstance(Context context){
		if (null == apkDownloadManager){
			apkDownloadManager = new ApkDownloadManager(context);
		}
		return apkDownloadManager;
	}
	
	/**
	 * 开始下载
	 * @param url
	 */
	public void startDownload(String url){
		if (TextUtils.isEmpty(url)) return;
		T.showShort(context, "正在下载");
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.setVisibleInDownloadsUi(true);//在通知栏中显示
        request.setDescription("正在下载");
        request.setDestinationInExternalPublicDir("com.sky.app/package",
        		url.substring(url.lastIndexOf("/") + 1));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        request.allowScanningByMediaScanner();// 设置为可被媒体扫描器找到
//        request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
        request.setVisibleInDownloadsUi(true);// 设置为可见和可管理
        dManager.enqueue(request);
	}
}