package com.sky.app.utils;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;

/**
 * 监听下载
 * @author wjx
 *
 */
@SuppressLint("NewApi")
public class DownloadReceiver extends BroadcastReceiver {
		
	@Override
	public void onReceive(Context context, Intent intent) {
		DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
		if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
			DownloadManager.Query query = new DownloadManager.Query();

			//在广播中取出下载任务的id
			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
			query.setFilterById(id);
			Cursor c = manager.query(query);
			if(c.moveToFirst()) {
				int fileUriIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
				String fileUri = c.getString(fileUriIdx);
				String fileName = null;
				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
					if (fileUri != null) {
						fileName = Uri.parse(fileUri).getPath();
					}
				} else {
					//Android 7.0以上的方式：请求获取写入权限，这一步报错
					int fileNameIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
					fileName = c.getString(fileNameIdx);
				}
				AppUtils.installApk(context, fileName);
				DialogUtils.hideCancelLoading();
			}
		}
	}
}