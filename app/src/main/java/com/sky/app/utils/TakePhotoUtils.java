package com.sky.app.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by sky on 2017/3/22.
 */

public class TakePhotoUtils {
    private static TakePhotoUtils instance = null;
    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private static final int PHOTO_REQUEST_CAREMA = 1;      //拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;     //相册
    private static final int PHOTO_REQUEST_CUT = 3;         //裁剪
    private static final String NO_CAMERA = "没有相机功能！";

    private String imageName;//图片名称

    public static TakePhotoUtils getInstance(Activity activity){
        if (null == instance){
            synchronized (TakePhotoUtils.class){
                instance = new TakePhotoUtils(activity);
            }
        }else{
            instance.setActivity(activity);
        }
        return instance;
    }

    public TakePhotoUtils(Activity activity){
        this.activity = activity;
    }

    /**
     * 拍照
     */
    public void takePoto(String imageName){
        this.imageName = imageName;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            File tempFile = new File(Constants.Path.APP_IMAGE_CACHE_PATH);
            if (null != tempFile && !tempFile.exists()) tempFile.mkdirs();
            tempFile = new File(tempFile, imageName);
            //拍照
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
            Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            activity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
        } else {
            T.showShort(activity, NO_CAMERA);
        }
    }


    /**
     * 选取文件
     */
    public void choosePhoto(String imageName){
        this.imageName = imageName;
        AppUtils.choosePhoto(activity, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 获取回调数据
     * @param requestCode
     * @param resultCode
     * @param data
     * @param isCrop
     * @return 地址
     */
    public String onActivityResult(int requestCode, int resultCode, Intent data,
                                   boolean isCrop) {
        if (resultCode != Activity.RESULT_OK) return "";

        switch (requestCode){
            case PHOTO_REQUEST_GALLERY://相册
                L.msg("5->" + data);
                if (data != null) {
                    Uri uri = data.getData();
                    L.msg("6->" + uri.getPath());
                    if (isCrop){//裁剪
                        crop(uri);
                    }else{
                        L.msg("7->" + AppUtils.getRealpathFromUri(activity, uri));
                        return AppUtils.getRealpathFromUri(activity, uri);
                    }
                }
                break;
            case PHOTO_REQUEST_CAREMA://拍照
                File file = new File(Constants.Path.APP_IMAGE_CACHE_PATH + imageName);
                Log.e("lbh12346789",file.toString());
                Uri uri = FileProvider.getUriForFile(activity, "com.sky.app_select_product_category.fileProvider", file);

                L.msg("3->" + uri.getPath());
                if (isCrop){//裁剪
                    crop(uri);
                }else{
                    L.msg("4->" + file.getAbsolutePath());
                    return file.getAbsolutePath();
                }
                break;
            case PHOTO_REQUEST_CUT://裁剪
                L.msg("2->" + cropUrl.getPath());

                if (cropUrl != null) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(cropUrl));
                        return Utils.saveLocalImage(bitmap, imageName);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        return "";
    }

    /**
     * 裁剪图片
     * @param uri
     */
    private Uri cropUrl = null;
    private void crop(Uri uri) {
        L.msg("8->" + uri.getPath());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        cropUrl = Uri.parse("file://" + Constants.Path.APP_IMAGE_CACHE_PATH + imageName);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUrl);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}
