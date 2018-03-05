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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.Case;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.contract.ImageContract;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.presenter.SellerCenterPresenter;
import com.sky.shop.presenter.UploadImagePresenter;
import com.sky.shop.utils.AppDialogUtils;
import com.sky.shop.utils.TakePhotoUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 案例编辑
 */
public class CaseEditActivity extends BaseViewActivity<SellerContract.ISellerCenterPresenter>
    implements SellerContract.ISellerCenterView, ImageContract.IUploadView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.upload_image_resource)
    ImageView imageView;

    @BindView(R.id.case_name)
    EditText caseName;

    private ImageContract.IUploadPresenter iUploadPresenter;
    private Case aCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_case_edit);
    }

    @Override
    protected SellerContract.ISellerCenterPresenter presenter() {
        iUploadPresenter = new UploadImagePresenter(context, this);
        return new SellerCenterPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void init() {
        this.aCase = (Case) getIntent().getSerializableExtra("caseList");
        if (null == this.aCase) this.aCase = new Case();
        title.setText(R.string.app_case_edit_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        if (!TextUtils.isEmpty(this.aCase.getProduct_id())){
            caseName.setText(this.aCase.getProduct_name());
            ImageHelper.getInstance().displayDefinedImage(this.aCase.getProduct_image_url(), imageView,
                    R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        }
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(this);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Deprecated
    @Override
    public void showSuccess(ProductIntroduceOut productIntroduceOut) {

    }

    @Deprecated
    @Override
    public void getRefreshData(List<Case> list) {

    }

    @Deprecated
    @Override
    public void responseEditShopInfo(String msg) {

    }

    @Override
    public void responseAddCase(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @Override
    public void responseEditCase(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @Deprecated
    @Override
    public void responseDelCase(String msg) {

    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @OnClick(R.id.upload)
    void upload(){
        //上传图片
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.Permission.CAMERA_PERMISSION);
        }else{
            AppDialogUtils.showTakePhotoView(this);
        }
    }

    @OnClick(R.id.confirm)
    void confirm(){
        //确定
        if (TextUtils.isEmpty(caseName.getText().toString().trim())){
            T.showShort(context, "请输入案例名称");
            return;
        }
        if (TextUtils.isEmpty(aCase.getProduct_image_url())){
            T.showShort(context, "请上传案例图片");
            return;
        }
        aCase.setProduct_name(caseName.getText().toString().trim());
        if (!TextUtils.isEmpty(this.aCase.getProduct_id())){
            getPresenter().requestEditCase(aCase);
        }else{
            getPresenter().requestAddCase(aCase);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode,
                resultCode, data, true);
        L.msg("返回的图片地址：" + msg);
        if (!TextUtils.isEmpty(msg)){
            ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(), imageView,
                    R.mipmap.app_default_icon, R.mipmap.app_default_icon);
            iUploadPresenter.uploadFile(msg);
        }
    }

    @Override
    public void getImageUrl(String url) {
        aCase.setProduct_image_url(url);
    }
}
