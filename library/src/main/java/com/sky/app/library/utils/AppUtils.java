package com.sky.app.library.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.sky.app.library.base.bean.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sky on 2017/2/13.
 * app开发常用工具类
 * 1、分辨率转换
 * 2、拨打电话
 * 3、获取sd卡路径
 * 4、判断sd卡是否存在
 * 5、获取屏幕宽度和高度
 * 6、调用系统内部下载浏览器下载
 * 7、发送短信
 * 8、获取两点之间距离
 * 9、检测网络链接
 * 10、跳转到浏览器
 * 11、格式化下载速率
 * 12、获取app版本号
 * 13、获取手机mac地址
 * 14、获取本机号码
 * 15、获取应用版本名称
 * 16、读取清单文件配置
 * 17、获取应用名称
 * 18、获取应用图标索引
 * 19、获取app包名
 * 20、获取imei号
 * 21、获取手机ip地址
 * 22、安装app
 * 23、获取安装的apk包
 * 24、获取assets目录下文件内容
 * 25、获取assets目录下面指定的.properties文件内容
 * 26、获取用户代理信息
 * 27、获取完整的堆栈信息
 * 28、保存屏幕截图
 * 29、保存图片
 * 30、读取网络图片
 * 31、判断gps是否开启
 * 32、获取当前android的api号
 * 33、判断网络类型
 * 34、获取系统颜色／解析字符颜色
 * 35、跳转activity(带动画)
 * 36、跳转到系统相机
 * 37、打开关闭软件盘
 * 38、选取文件系统中的文件
 * 39、拍照上传，裁剪
 * 40、创建新文件夹
 * 41、退出应用
 * 42、处理金额保留两位小数
 * 43、改变textView上的icon颜色
 * 44、设置中划线
 * 45、处理手机号码*号
 */

public final class AppUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue){
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue){
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5f);
    }

    /**
     * 拨打号码
     * @param context
     * @param tel
     */
    public static void callPhone(Context context, String tel){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        context.startActivity(intent);
    }

    /**
     * 获取sd卡路径
     * @return
     */
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist){
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }else{
            sdDir = Environment.getDataDirectory();
        }
        return sdDir.toString();
    }

    /**
     * 判断是否存在sd卡
     * @return
     */
    public static boolean isExistSDCard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 屏幕宽度
     * @param act
     * @return
     */
    public static int getScreenWidth(Activity act){
        Point point = new Point();
        act.getWindowManager().getDefaultDisplay().getSize(point);
        return point.x;
    }

    /**
     * 屏幕高度
     * @param act
     * @return
     */
    public static int getScreenHeight(Activity act){
        Point point = new Point();
        act.getWindowManager().getDefaultDisplay().getSize(point);
        return point.y;
    }

    /**
     * 调用系统下载
     * @param context
     * @param url
     */
    public static void sysDownload(Context context, String url){
        DownloadManager downloadManager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalFilesDir(context, null,
                url.substring(url.lastIndexOf("/") + 1, url.length()));
        downloadManager.enqueue(request);
    }

    /**
     * 浏览器下载
     * @param context
     * @param url
     */
    public static void browserDownload(Context context, String url){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e){
            sysDownload(context, url);
        }
    }

    /**
     * 发送短信
     * @param context
     * @param smsBody
     */
    public static void sendSMS(Context context, String smsBody){
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }

    /**
     * 获取两点之间距离 返回double 类型
     *
     * @param lat1 位置1纬度
     * @param lon1 位置1经度
     * @param lat2 位置2纬度
     * @param lon2 位置2经度
     * @return
     */
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0];
    }

    /**
     * 判断网络类型
     * @param context
     * @return
     */
    public static int checkNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        //没有网络
        if (null == info || !info.isAvailable() || !info.isConnected()){
            return Constants.Network.NO_NETWORK;
        }

        if (ConnectivityManager.TYPE_WIFI == info.getType()){
            return Constants.Network.NETWORK_WIFI;//wifi
        }else{
            return Constants.Network.NETWORK_MOBILE;//数据
        }
    }

    /**
     * 检测网络是否连接
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (null != info && info.isConnected() && info.isAvailable());
    }

    /**
     * 跳转到浏览器
     * @param context
     * @param value
     */
    public static void skipBrowser(Context context, String value) {
        try {
            Uri uri = Uri.parse(value);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化下载文件大小
     *
     * @param finishedSize
     * @param totalSize
     * @return
     */
    public static String formatSize(long finishedSize, long totalSize) {
        StringBuilder sb = new StringBuilder(50);
        float finished = ((float) finishedSize) / 1024 / 1024;
        if (finished < 1) {
            sb.append(String.format("%1$.2fK/", ((float) finishedSize) / 1024));
        } else {
            sb.append((String.format("%1$.2fM/ ", finished)));
        }
        float total = ((float) totalSize) / 1024 / 1024;
        if (total < 1) {
            sb.append(String.format("%1$.2fK", ((float) totalSize) / 1024));
        } else {
            sb.append(String.format("%1$.2fM", total));
        }
        return sb.toString();
    }

    /**
     * 获取app版本号
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        int appVersion;
        try {
            String packName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packName, 0);
            appVersion = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            appVersion = 0;
        }
        return appVersion;
    }

    /**
     * 获取手机mac地址
     * @param context
     * @return
     */
    /*********begin***********/
    public static String getMobileMacAddr(Context context){
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String str="";
                String macSerial="";
                try {
                    Process pp = Runtime.getRuntime().exec(
                            "cat /sys/class/net/wlan0/address ");
                    InputStreamReader ir = new InputStreamReader(pp.getInputStream());
                    LineNumberReader input = new LineNumberReader(ir);
                    for (; null != str;) {
                        str = input.readLine();
                        if (str != null) {
                            macSerial = str.trim();// 去空格
                            break;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (macSerial == null || "".equals(macSerial)) {
                    try {
                        return loadFileAsString("/sys/class/net/eth0/address")
                                .toUpperCase().substring(0, 17);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return macSerial;
            }else {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                return wifi.getConnectionInfo().getMacAddress();
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }
    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }
    /*********end***********/

    /**
     * 获取电话号码
     */
    public static String getNativePhoneNumber(Context context) {
        try{
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getLine1Number();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @Title: readApplicationInfo
     * @Description: 读取清单文件中的指定配置信息
     * @param: @param context
     * @param: @param name
     * @return: void
     * @throws
     */
    public static String getMetaData(Context context, String name){
        if (null == name || "".equals(name)) return "";
        PackageManager pm = context.getPackageManager();
        try{
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            Bundle bundle = appInfo.metaData;
            return bundle.getString(name) == null ? "" : bundle.getString(name);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取清单文件中的配置
     * @param context
     * @return
     */
    public static Bundle getMetaData(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);

            if(appInfo != null && appInfo.metaData != null) {
                return appInfo.metaData;
            }
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return new Bundle();
    }

    /**
     * 获取应用程序名称
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        String applicationName = (String) packageManager
                .getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * 获取application的图标
     * @param context
     * @return
     */
    public static int getApplicationIcon(Context context){
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.applicationInfo.icon;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取app包名
     * @param context
     * @return
     */
    public static String getPackageName(Context context){
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 返回imei
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE)).getDeviceId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机ip地址
     *
     * @return
     */
    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 安装apk
     * @param context
     */
    public static void installApk(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", new File(path));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 获取安装的apk包
     * @param context
     * @return
     */
    private static String getPackageCodePath(Context context) {
        try {
            return (String)context.getClass().getMethod("getPackageCodePath").invoke(context);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     ** 安装 apk 文件
     **
     **@param apkFile
     **/
    /*public static void installApk(Context context, File apkFile) {
        Intent installApkIntent = new Intent();
        installApkIntent.setAction(Intent.ACTION_VIEW);
        installApkIntent.addCategory(Intent.CATEGORY_DEFAULT);
        installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            installApkIntent.setDataAndType(FileProvider.getUriForFile(context.getApplicationContext(),
                    "com.sky.app.fileProvider", apkFile),
                    "application/vnd.android.package-archive");
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            installApkIntent.setDataAndType(Uri.fromFile(apkFile),
                "application/vnd.android.package-archive");
        }
        if (context.getPackageManager().queryIntentActivities(installApkIntent, 0).size() > 0) {
            context.startActivity(installApkIntent);
        }
    }*/

    /***
     * 获取assets目录下文件内容
     * @param context
     * @param assetsFile
     * @return
     */
    public static String getAssetConfigs(Context context, String assetsFile) {
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            reader = new InputStreamReader(context.getAssets().open(assetsFile));
            br = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取assets目录下面指定的.properties文件内容
     * @param context
     * @param assetsPropertiesFile
     * @return
     */
    public static Map<String, String> getAssetPropConfig(Context context, String assetsPropertiesFile) {
        try {
            Properties pro = new Properties();
            pro.load(context.getAssets().open(assetsPropertiesFile));
            Map<String, String> result = new HashMap<>();
            for(Map.Entry<Object, Object> entry: pro.entrySet()) {
                String keyStr = entry.getKey().toString().trim();
                String keyVal = entry.getValue().toString().trim();
                if(!result.containsKey(keyStr)) {
                    result.put(keyStr, keyVal);
                }
            }
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户代理信息
     */
    public static String getUserUa(Context context) {
        WebView webview = new WebView(context);
        webview.layout(0, 0, 0, 0);
        return webview.getSettings().getUserAgentString();
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

    /**
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap){
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        if (bitmap != null && !bitmap.isRecycled())
            bitmap.recycle();
        return resizedBitmap;
    }

    /**
     * 读取网络图片
     * @param path
     * @param cache
     * @return
     * @throws Exception
     */
    public static Uri getImageURI(Context context, String path, File cache) throws Exception {
        String name = Md5Util.md5(path) + path.substring(path.lastIndexOf("."));
        File file = new File(cache, name);
        // 如果图片存在本地缓存目录，则不去服务器下载
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
        } else {
            // 从网络上获取图片
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();

                /*// 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(context.getContentResolver(),
                            file.getAbsolutePath(), name, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

                // 返回一个URI对象
                return Uri.fromFile(file);
            }
        }
        return null;
    }

    /**
     * 保存图片
     * @param path
     */
    public static void saveImage(Context context, String path){
        try{
            File file = new File(Constants.Path.IMAGE_CACHE_PATH);
            if (!file.exists()){
                file.mkdirs();
            }
            getImageURI(context, path, file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保存屏幕截图
     * @param v
     */
    public static void saveScreenShot(Context context, View v){
        FileOutputStream fos = null;
        try{
            String name = System.currentTimeMillis() + ".png";
            Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas();
            canvas.setBitmap(bitmap);
            v.draw(canvas);

            // 首先保存图片
            File fileDir = new File(Constants.Path.IMAGE_CACHE_PATH);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File file = new File(fileDir, name);
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (null != fos){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存本地图片
     * @param bitmap
     * @param name
     * @return
     */
    public static String saveLocalImage(Bitmap bitmap, String name){
        // 首先保存图片
        FileOutputStream fos = null;
        File fileDir = new File(Constants.Path.IMAGE_CACHE_PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, name);
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            if (!bitmap.isRecycled()){
                bitmap.recycle();
                bitmap = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }finally {
            if (null != fos){
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    /**
     * 判断gps是否开启
     * @param context
     * @return
     */
    public static boolean isOpenGps(Context context){
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 获取当前android的api号
     * @return
     */
    public static int getAndroidAPI(){
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统颜色
     * @return
     */
    public static int getSystemColor(Context context, int i){
        return ContextCompat.getColor(context, i);
    }

    /**
     * 解析颜色
     * @param color
     * @return
     */
    public static int parseColor(String color){
        return Color.parseColor(color);
    }

    /**
     * 跳转activity
     * @param context
     */
    public void startActivity(Context context, Class<?> clazz, int start, int end){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(context, start, end);
        ActivityCompat.startActivity(context, new Intent(context, clazz), options.toBundle());
    }

    /**
     * 隐藏软件盘
     * @param view
     */
    public static void hideSoftInput(View view) {
        InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && view != null && view.getWindowToken() != null)
            im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软件盘
     * @param context
     */
    public static void showSoftInput(Dialog context) {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * 展示软件盘
     * @param view
     */
    public static void showSoftInput(View view) {
        InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && view != null)
            im.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 调用文件选择软件来选择文件
     **/
    public static void chooseFile(Context context, int request) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "请选择一个文件"),
                    request);
        } catch (Exception e) {
            T.showShort(context, "请安装文件管理器");
        }
    }


    /**
     * 调用文件选择软件来选择文件
     **/
    public static void chooseAudio(Context context, int request) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    request);
        } catch (Exception e) {
            T.showShort(context, "请安装文件管理器");
        }
    }

    /**
     * 调用文件选择软件来选择文件
     **/
    public static void chooseVideo(Context context, int request) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    request);
        } catch (Exception e) {
            T.showShort(context, "请安装文件管理器");
        }
    }

    /**
     * 将URI转换为真实路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealpathFromUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String path = null;
        if (uri.getScheme().compareTo("content") == 0) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex("_data"));// 获取绝对路径
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (uri.getScheme().compareTo("file") == 0) {
            path = uri.toString().replace("file://", "");
        }
        return path;
    }

    /**
     * 打开相机拍照，将照片保存到指定路径
     *
     * @param context
     * @param file    照片存放位置
     * @param request
     */
    public static void startCamera(Activity context, File file, int request) {
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Uri imageUri;
        if (AppUtils.getAndroidAPI() >= Constants.VERSION_CODES.ANDROID_API_24) {
            //通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        } else {
            imageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent();
        if (AppUtils.getAndroidAPI() >= Constants.VERSION_CODES.ANDROID_API_24) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        context.startActivityForResult(intent, request);
    }

    /**
     * 直接启动系统相机程序
     *
     * @param context
     */
    public static void startSystemCameraApp(Context context) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            PackageManager pm = context.getPackageManager();
            ResolveInfo info = pm.resolveActivity(i, 0);
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 从相册获取照片
     * @param context
     * @param request
     */
    public static void choosePhoto(Activity context, int request) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, request);
    }

    /**
     * 裁剪图片 （带图片）
     * @param context
     * @param imagePath
     * @param aspectX
     * @param aspectY
     * @param outputX
     * @param outputY
     * @param savaPath
     * @param request
     */
    public static void cropImage(Activity context, String imagePath, int aspectX, int aspectY,
                                 int outputX, int outputY, String savaPath, int request) {
        File file = new File(savaPath);
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri fromUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fromUri = getImageContentUri(context, new File(imagePath));
        } else {
            fromUri = Uri.fromFile(new File(imagePath));
        }
        intent.setDataAndType(fromUri, "image/*");
        //设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //裁剪时是否保留图片的比例，这里的比例是1:1
        intent.putExtra("scale", true);
        //是否将数据保留在Bitmap中返回
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        //设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
//        //是否是圆形裁剪区域，设置了也不一定有效
//        intent.putExtra("circleCrop", true);
        context.startActivityForResult(intent, request);
    }

    /**
     * 获取图片的URI
     * @param context
     * @param imageFile
     * @return
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        try{
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[] { MediaStore.Images.Media._ID },
                    MediaStore.Images.Media.DATA + "=? ",
                    new String[] { filePath }, null);

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                return Uri.withAppendedPath(baseUri, "" + id);
            } else {
                if (imageFile.exists()) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.DATA, filePath);
                    return context.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                } else {
                    return null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 退出应用程序
     */
    public static void exit() {
        try {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启QQ聊天
     * @param context
     * @param qq
     */
    public static void qqChat(Context context, String qq){
        try {
            if (TextUtils.isEmpty(qq)) return;
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qq));
            context.startActivity(intent);
        } catch (Exception e) {
            T.showShort(context, "您还未安装QQ客户端");
        }
    }

    /**
     * 处理金额数据 保留两位小数
     */
    public static String parseDouble(double data){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(data);
    }
    /**
     * 处理地图距离数据 保留两位小数
     */
    public static String parseDoubleDistance(double data){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(data);
    }
    /**
     * 改变textView上的icon颜色
     */
    public static void changeTextViewIcon(Context context, TextView view, int res, int pos){
        Drawable drawable = context.getResources().getDrawable(res);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        switch (pos){
            case 1://left
                view.setCompoundDrawables(drawable, null, null, null);
                break;
            case 2://top
                view.setCompoundDrawables(null, drawable, null, null);
                break;
            case 3://right
                view.setCompoundDrawables(null, null, drawable, null);
                break;
            case 4://bottom
                view.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    /**
     * 设置中划线并加清晰
     */
    public static void setLineThrough(TextView textView){
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 改变手机号码
     * @param mobile
     */
    public static String change4Mobile(String mobile){
        if (!TextUtils.isEmpty(mobile) && mobile.length() == 11){
            return mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
        }
        return "";
    }
}