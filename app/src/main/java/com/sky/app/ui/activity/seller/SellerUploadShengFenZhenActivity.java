package com.sky.app.ui.activity.seller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.SellMessageComplete;
import com.sky.app.bean.SellUpload;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.SellerContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.UploadActivityPresenter;
import com.sky.app.utils.AppDialogUtils;
import com.sky.app.utils.TakePhotoUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SellerUploadShengFenZhenActivity extends BaseViewActivity<UploadActivityPresenter>  implements SellerContract.IUploadView {

    @BindView(R.id.app_title)
    TextView appTitle;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.sell_upload_zhengmian)
    FrameLayout sellUploadZhengmian;
    @BindView(R.id.sell_upload_fanmian)
    FrameLayout sellUploadFanmian;
    @BindView(R.id.sell_upload_next_buttom)
    TextView sellUploadNextButtom;
    @BindView(R.id.sell_upload_zhengmian_pic)
    ImageView sellUploadZhengmianPic;
    @BindView(R.id.sell_upload_fanmian_pic)
    ImageView sellUploadFanmianPic;
    @BindView(R.id.activity_seller_upload_sheng_fen_zhen)
    LinearLayout activitySellerUploadShengFenZhen;
    private String tag;//标识是上传正面还是上传反面
    private String id_card_reverse;
    private String id_card_positive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_upload_sheng_fen_zhen);
        ButterKnife.bind(this);
//        ButterKnife.bind(this);
        normalToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        normalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        appTitle.setText("实名认证");
    }

    @Override
    protected UploadActivityPresenter presenter() {
        return new UploadActivityPresenter(this,this);
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.sell_upload_zhengmian, R.id.sell_upload_fanmian, R.id.sell_upload_next_buttom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sell_upload_zhengmian:
                tag="front";
                choosePicture();
                break;
            case R.id.sell_upload_fanmian:
                tag="back";
                choosePicture();
                break;
            case R.id.sell_upload_next_buttom:
                if(TextUtils.isEmpty(id_card_positive)||TextUtils.isEmpty(id_card_reverse)){

                    T.showShort(SellerUploadShengFenZhenActivity.this,"请上传照片");
                    return;
                }else{
                    Intent intent = new Intent(this,SellerUploadYinYeZhiZhaoActivity.class);
                    SellMessageComplete upload = (SellMessageComplete) getIntent().getSerializableExtra("upload");
                    SellUpload sellUpload = new SellUpload();
                    sellUpload.setId_card_positive(id_card_positive);
                    sellUpload.setId_card_reverse(id_card_reverse);
                    upload.setOther_desc_bean(sellUpload);
                    intent.putExtra("upload",upload);
                    startActivity(intent);
                }

                break;
        }
    }

    @Override
    protected void onDestoryActivity() {

    }

    private void choosePicture() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE},
                    Constants.Permission.CAMERA_PERMISSION);
        } else {
            AppDialogUtils.showTakePhotoView(SellerUploadShengFenZhenActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode,
                resultCode, data, true);
        if (!TextUtils.isEmpty(msg)) {

            if("front".equals(tag)){
                ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(),
                        sellUploadZhengmianPic,
                        R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
                getPresenter().uploadFirstFile(msg);
            }else{
                ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(),
                        sellUploadFanmianPic,
                        R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
                getPresenter().uploadSecondFile(msg);
            }

        }
    }


    @Override
    public void getFirstImageUrl(String url) {
        Log.e("sssssssssss", "getFirstImageUrl: "+url );
        id_card_positive = url;
    }

    @Override
    public void getSecondImageUrl(String url) {
        Log.e("sssssssssss", "getSecondImageUrl: "+url );
        id_card_reverse = url;
    }

    @Override
    public void registerSuccess(UserBean userBean) {

    }
}
