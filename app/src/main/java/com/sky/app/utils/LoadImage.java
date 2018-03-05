package com.sky.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 用来图片的网络下载，以及图片的缓存处理
 */

public  class LoadImage {


    private static Context context1;

    //缓存到内存
    private static LruCache<String,Bitmap> lruCache=new LruCache<String, Bitmap>(1024*1024*10){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };


    /**
     *
     * @param url 图片地址
     * @param imageView  要显示的控件
     */
    public static void loadImage(Context context,String url , ImageView imageView){
        context1=context;
        //设置tag标记
        imageView.setTag(url);


        //声明一个Bitmap对象
        Bitmap bitmap=null;
        //首先查看内存缓存中是否有这个图片
        bitmap=lruCache.get(url);

        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);

            return;//直接结束方法
        }

        //其次是从SDK中取出图片
        File cacheDir = context1.getCacheDir();
        if (cacheDir.exists()){
            File[] files = cacheDir.listFiles();
            for (File file: files) {
                int a = url.lastIndexOf("/");
                String fileName=url.substring(a+1);
                if (fileName.equals(file.getName())){
                    bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());

                    imageView.setImageBitmap(bitmap);
                    return;
                }

            }
        }

        //启动异步任务获取图片
        getBitmapByAsyncTask(url,imageView);

    }

    /**
     *
     @param url 图片地址
      * @param imageView  要显示的控件
     */
    private static void getBitmapByAsyncTask(String url,ImageView imageView) {
        LoadImageAsyncTask loadImageAsyncTask=new LoadImageAsyncTask(imageView);
        loadImageAsyncTask.execute(url);
    }
    /**
     * 获取圆角位图的方法
     *
     * @param bitmap 需要转化成圆角的位图
     * @param pixels 圆角的度数，数值越大，圆角越大
     * @return 处理后的圆角位图
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 创建一个异步加载图片的异步任务
     */
    public static class LoadImageAsyncTask extends AsyncTask<String ,Void,Bitmap>{

        private String str;
        private ImageView imageView;
        public LoadImageAsyncTask(ImageView imageView){
            this.imageView=imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap=null;
             str=params[0];
            try {
                URL url=new URL(str);

                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    InputStream is = connection.getInputStream();
                    bitmap= BitmapFactory.decodeStream(is);

                    //保存到内存缓存中
                    lruCache.put(str,bitmap);

                    //保存到SDK中
                    saveToSDK(str,bitmap);

                    //关闭流
                    is.close();
                }
                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        private void saveToSDK(String url,Bitmap bitmap) {
            File cacheDir = context1.getCacheDir();
            if (!cacheDir.exists()){//如果不存在就创建出来
                cacheDir.mkdirs();
            }
            //http://118.244.212.82:9092/Images/20160517114849.jpg
            int a = url.lastIndexOf("/");
            String fileName=url.substring(a+1);

            try {
                FileOutputStream outputStream=new FileOutputStream(new File(cacheDir,fileName));
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap!=null){
                if (imageView.getTag()!=null && imageView.getTag().equals(str)){//判断图片与图片地址是否对应
                    imageView.setImageBitmap(bitmap);
                }
            }

        }
    }
}
