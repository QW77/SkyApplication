package com.sky.app.library.utils;

import android.util.Log;

import com.sky.app.library.base.MyApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 日志输出类
 * @author wjx
 *
 */
public class L {
	private static final String TAG = "sky";
	private static final int LEVEL = 4;// 日志输出级别
	private static final int V = 0;
	private static final int D = 1;
	private static final int I = 2;
	private static final int W = 3;
	private static final int E = 4;

    /**
     * 正常日志输出
     * @param msg
     */
	/*public static void msg(String msg) {
		if (MyApplication.isDebug){
			switch (LEVEL) {
			case V:
				Log.w(TAG, msg);
				break;
			case D:
				Log.d(TAG, msg);
				break;
			case I:
				Log.i(TAG, msg);
				break;
			case W:
				Log.w(TAG, msg);
				break;
			case E:
				Log.e(TAG, msg);
				break;
			default:
				break;
			}
		}
	}*/

	/**
	 * 打印堆栈错误信息
	 */
	public static void error(Throwable throwable, String msg){
		if (MyApplication.isDebug){
			Log.e(TAG, msg, throwable);
		}
	}

	/**
	 * 大数据返回
	 * @param msg
	 */
	public static void msg(String msg) {
		if (MyApplication.isDebug) {
			int segmentSize = 3 * 1024;
			long length = msg.length();
			if (length <= segmentSize) {// 长度小于等于限制直接打印
				Log.e("sky", msg);
			} else {
				while (msg.length() > segmentSize) {// 循环分段打印日志
					String logContent = msg.substring(0, segmentSize);
					msg = msg.replace(logContent, "");
					Log.e("sky", logContent);
				}
				Log.e("sky", msg);// 打印剩余日志
			}
		}
	}

	/**
	 * 完整的堆栈信息
	 * @param e Exception
	 * @return
	 */
	public static String getStackTrace(Throwable e) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException ie) {
					ie.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
		return sw.toString();
	}
}