package com.sky.shop.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.sky.app.library.base.bean.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.sky.app.library.utils.AppUtils.getImageURI;

/**
 * Created by sky on 2017/5/13.
 */

public class Utils {

    /**
     * 保存图片
     * @param path
     */
    public static void saveImage(Context context, String path){
        try{
            File file = new File(Constants.Path.SHOP_IMAGE_CACHE_PATH);
            if (!file.exists()){
                file.mkdirs();
            }
            getImageURI(context, path, file);
        }catch (Exception e){
            e.printStackTrace();
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
        File fileDir = new File(Constants.Path.SHOP_IMAGE_CACHE_PATH);
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
}
