package com.sky.shop.ui.activity.shop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.SellMessageComplete;
import com.sky.shop.bean.SellUpload;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.UploadActivityPresenter;
import com.sky.shop.ui.activity.user.LoginActivity;
import com.sky.shop.utils.AppDialogUtils;
import com.sky.shop.utils.TakePhotoUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SellerUploadShengFenZhenActivity extends BaseViewActivity<UploadActivityPresenter>
        implements ShopContract.IUploadView {

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
                    SellMessageComplete upload = (SellMessageComplete) getIntent().getSerializableExtra("upload");
                    SellUpload sellUpload = new SellUpload();
                    sellUpload.setId_card_positive(id_card_positive);
                    sellUpload.setId_card_reverse(id_card_reverse);
                    upload.setOther_desc(new Gson().toJson(sellUpload));
                    getPresenter().sellRegister(upload);
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
                        R.mipmap.app_person_icon, R.mipmap.app_person_icon);
                getPresenter().uploadFirstFile(msg);
            }else{
                ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(),
                        sellUploadFanmianPic,
                        R.mipmap.app_person_icon, R.mipmap.app_person_icon);
                getPresenter().uploadSecondFile(msg);
            }

        }
    }

    @Override
    public void getFirstImageUrl(String url) {
        id_card_positive = url;
    }

    @Override
    public void getSecondImageUrl(String url) {
        id_card_reverse = url;
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        T.showShort(context, "个人商户注册成功");
//        userBean.setSeller_type("名片");
//        UserBean.getInstance().setUserCache(userBean);
        Intent i = new Intent(context, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }
}
