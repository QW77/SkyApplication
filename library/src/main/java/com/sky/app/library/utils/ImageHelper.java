package com.sky.app.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;

/**
 * 图片缓存类
 */
public class ImageHelper extends ImageLoader {
    
    private static ImageHelper instance;

    private ImageHelper(){

    }
    
    public static ImageHelper getInstance(){
        if (null == instance){
            synchronized (ImageHelper.class){
                if (null == instance){
                    instance = new ImageHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 显示图片
     * @param uri 图片地址
     * @param view 控件
     * @param defImage 默认图片
     * @param failImage 失败图片
     */
    public void displayDefinedImage(String uri, ImageView view, int defImage, int failImage){
        DisplayImageOptions.Builder builder = getDisplayBuilder();
        builder.showImageForEmptyUri(failImage);
        builder.showImageOnFail(failImage);
        builder.showImageOnLoading(defImage);
        displayImage(uri, view, builder.build());
    }

    /**
     * 显示图片
     * @param uri 图片地址
     * @param view 控件
     * @param defImage 默认地址
     * @param failImage 失败图片
     */
    public void displayRoundImage(String uri, ImageView view, int defImage, int failImage){
        DisplayImageOptions.Builder builder = getDisplayBuilder();
        builder.showImageForEmptyUri(failImage);
        builder.showImageOnFail(failImage);
        builder.displayer(new RoundedBitmapDisplayer(100));
        builder.showImageOnLoading(defImage);
        displayImage(uri, view, builder.build());
    }
    
    public DisplayImageOptions.Builder getDisplayBuilder() {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        
        // 缓存在文件系统
        builder.cacheOnDisk(true);
        
        // 缓存在内存
        builder.cacheInMemory(true);
        
        // 自动翻转图片
        builder.considerExifParams(true);
        
        builder.imageScaleType(ImageScaleType.EXACTLY);
        
        builder.bitmapConfig(Config.RGB_565);
        return builder;
    }
    
    public Builder getImageLoaderConfiguration(Context context, String path){
        Builder config = new Builder(context);
        config.diskCache(new UnlimitedDiskCache(new File(path)));//本地图片缓存
        config.threadPriority(Thread.NORM_PRIORITY);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 Mb
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app
		config.build();
        return config;
    }
    
//    /**
//     * 获取圆角位图的方法
//     *
//     * @param bitmap 需要转化成圆角的位图
//     * @param pixels 圆角的度数，数值越大，圆角越大
//     * @return 处理后的圆角位图
//     */
//    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels){
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//        final float roundPx = pixels;
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//        return output;
//    }
}