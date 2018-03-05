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
 * 商户主页编辑
 */
public class SellerCenterEditActivity extends BaseViewActivity<SellerContract.ISellerCenterPresenter>
    implements SellerContract.ISellerCenterView, ImageContract.IUploadView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.shop_img)
    ImageView shopImg;
    @BindView(R.id.app_user_name)
    TextView userName;
    @BindView(R.id.app_main_sell)
    EditText mainSell;
    @BindView(R.id.app_design_idea)
    EditText designIdea;
    @BindView(R.id.app_prof)
    EditText appProf;
    @BindView(R.id.app_award)
    EditText appAward;
    @BindView(R.id.app_work)
    EditText appWork;
    @BindView(R.id.mobile_value)
    EditText mobile;
    @BindView(R.id.qq_value)
    EditText qq;
    @BindView(R.id.weixin_value)
    EditText weixin;

    private ProductIntroduceOut productIntroduceOut = new ProductIntroduceOut();
    private ImageContract.IUploadPresenter iUploadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_seller_center_edit);
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
        getPresenter().requestDescData();
    }

    @Override
    protected void init() {
        title.setText(R.string.app_home_page_edit_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
    }

    @Override
    public void showSuccess(ProductIntroduceOut productIntroduceOut) {
        this.productIntroduceOut = productIntroduceOut;
        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(), shopImg,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        userName.setText(productIntroduceOut.getNick_name());
        mainSell.setText(productIntroduceOut.getMain_business_desc());
        designIdea.setText(productIntroduceOut.getDesign_concept());
        appProf.setText(productIntroduceOut.getSchooling_professional());
        appAward.setText(productIntroduceOut.getAwards_honor());
        appWork.setText(productIntroduceOut.getRepresentative_works());
        qq.setText(productIntroduceOut.getQq());
        weixin.setText(productIntroduceOut.getWeixin());
        mobile.setText(productIntroduceOut.getMobile());
    }

    @Deprecated
    @Override
    public void getRefreshData(List<Case> list) {

    }

    @Override
    public void responseEditShopInfo(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @Deprecated
    @Override
    public void responseAddCase(String msg) {

    }

    @Deprecated
    @Override
    public void responseEditCase(String msg) {

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

    @Override
    protected void onDestoryActivity() {
        if (null != iUploadPresenter){
            iUploadPresenter.destroy();
        }
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
    public void getImageUrl(String url) {
        productIntroduceOut.setPic_url(url);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode,
                resultCode, data, true);
        L.msg("返回的图片地址：" + msg);
        if (!TextUtils.isEmpty(msg)){
            ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(), shopImg,
                    R.mipmap.app_default_icon, R.mipmap.app_default_icon);
            iUploadPresenter.uploadFile(msg);
        }
    }

    @OnClick(R.id.shop_img)
    void clickIcon(){
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
        if (TextUtils.isEmpty(mainSell.getText().toString().trim())){
            T.showShort(context, "请输入主营业务");
            return;
        }
        productIntroduceOut.setMain_business_desc(mainSell.getText().toString().trim());
        if (TextUtils.isEmpty(designIdea.getText().toString().trim())){
            T.showShort(context, "请输入设计理念");
            return;
        }
        productIntroduceOut.setDesign_concept(designIdea.getText().toString().trim());
        if (TextUtils.isEmpty(appProf.getText().toString().trim())){
            T.showShort(context, "请输入学历专业");
            return;
        }
        productIntroduceOut.setSchooling_professional(appProf.getText().toString().trim());
        if (TextUtils.isEmpty(appAward.getText().toString().trim())){
            T.showShort(context, "请输入奖项荣誉");
            return;
        }
        productIntroduceOut.setAwards_honor(appAward.getText().toString().trim());
        if (TextUtils.isEmpty(appWork.getText().toString().trim())){
            T.showShort(context, "请输入代表作品");
            return;
        }
        productIntroduceOut.setRepresentative_works(appWork.getText().toString().trim());
        if (TextUtils.isEmpty(mobile.getText().toString().trim())){
            T.showShort(context, "请输入手机号码");
            return;
        }
        productIntroduceOut.setMobile(mobile.getText().toString().trim());
        if (TextUtils.isEmpty(qq.getText().toString().trim())){
            T.showShort(context, "请输入QQ");
            return;
        }
        productIntroduceOut.setQq(qq.getText().toString().trim());
        if (TextUtils.isEmpty(weixin.getText().toString().trim())){
            T.showShort(context, "请输入微信号码");
            return;
        }
        productIntroduceOut.setWeixin(weixin.getText().toString().trim());
        getPresenter().requestEditShopInfo(productIntroduceOut);
    }
}
