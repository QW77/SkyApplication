package com.sky.shop.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.shop.R;
import com.sky.shop.bean.UserBean;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;

/**
 * Created by sky on 2017/6/30.
 */

public class ImageWebViewActivity extends BaseViewActivity {

    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.app_picture)
    ImageView appPicture;
    @BindView(R.id.app_sider_title)
    TextView siderTitle;

    @BindView(R.id.app_content_wv)
    WebView wv;
    @BindView(R.id.app_myProgressBar)
    ProgressBar progress;//进度条
    private Handler handler = new Handler();//延时UI

    private long exitTime;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_card_main);
        wv.loadUrl("http://api.app.51craftsman.com/h5_shop/card?user_id=" + UserBean.getInstance().getCacheUid());
        WebSettings mWebSettings = wv.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);

        wv.setWebChromeClient(mWebChromeClient);
        wv.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void onDestoryActivity() {

    }

    public static final int INPUT_FILE_REQUEST_CODE = 1;
        private ValueCallback<Uri> mUploadMessage;
        private final static int FILECHOOSER_RESULTCODE = 2;
        private ValueCallback<Uri[]> mFilePathCallback;

        private String mCameraPhotoPath;

        //在sdcard卡创建缩略图
        //createImageFileInSdcard
        @SuppressLint("SdCardPath")
        private File createImageFile() {
            //mCameraPhotoPath="/mnt/sdcard/tmp.png";
            File file=new File(Environment.getExternalStorageDirectory()+"/","tmp.png");
            mCameraPhotoPath=file.getAbsolutePath();
            if(!file.exists())
            {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }

        private WebChromeClient mWebChromeClient = new WebChromeClient() {


            // android 5.0 这里需要使用android5.0 sdk
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    WebChromeClient.FileChooserParams fileChooserParams) {

                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }


                mFilePathCallback = filePathCallback;

                /**
                 Open Declaration   String android.provider.MediaStore.ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE"
                 Standard Intent action that can be sent to have the camera application capture an image and return it.
                 The caller may pass an extra EXTRA_OUTPUT to control where this image will be written. If the EXTRA_OUTPUT is not present, then a small sized image is returned as a Bitmap object in the extra field. This is useful for applications that only need a small image. If the EXTRA_OUTPUT is present, then the full-sized image will be written to the Uri value of EXTRA_OUTPUT. As of android.os.Build.VERSION_CODES.LOLLIPOP, this uri can also be supplied through android.content.Intent.setClipData(ClipData). If using this approach, you still must supply the uri through the EXTRA_OUTPUT field for compatibility with old applications. If you don't set a ClipData, it will be copied there for you when calling Context.startActivity(Intent).
                 See Also:EXTRA_OUTPUT
                 标准意图，被发送到相机应用程序捕获一个图像，并返回它。通过一个额外的extra_output控制这个图像将被写入。如果extra_output是不存在的，
                 那么一个小尺寸的图像作为位图对象返回。如果extra_output是存在的，那么全尺寸的图像将被写入extra_output URI值。
                 */
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        //设置MediaStore.EXTRA_OUTPUT路径,相机拍照写入的全路径
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (Exception ex) {
                        // Error occurred while creating the File
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        System.out.println(mCameraPhotoPath);
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                    System.out.println(takePictureIntent);
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

                return true;
            }



            //The undocumented magic method override
            //Eclipse will swear at you if you try to put @Override here
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILECHOOSER_RESULTCODE);

            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(i, "Image Chooser"),
                        FILECHOOSER_RESULTCODE);
            }

            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Image Chooser"),
                        ImageWebViewActivity.FILECHOOSER_RESULTCODE);
            }
        };

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == mUploadMessage) return;
                Uri result = data == null || resultCode != RESULT_OK ? null
                        : data.getData();
                if (result != null) {
                    String imagePath = AppUtils.getRealpathFromUri(this, result);
                    if (!TextUtils.isEmpty(imagePath)) {
                        result = Uri.parse("file:///" + imagePath);
                    }
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            } else if (requestCode == INPUT_FILE_REQUEST_CODE && mFilePathCallback != null) {
                // 5.0的回调
                Uri[] results = null;

                // Check that the response is a good one
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        // If there is not data, then we may have taken a photo
                        if (mCameraPhotoPath != null) {
                            results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                        }
                    } else {
                        String dataString = data.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }

                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
            } else {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

    }
}
