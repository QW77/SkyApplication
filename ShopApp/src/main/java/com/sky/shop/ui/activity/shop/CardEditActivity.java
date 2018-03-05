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
import com.sky.app.library.utils.StringUtil;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.contract.ImageContract;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.presenter.CardPresenter;
import com.sky.shop.presenter.UploadImagePresenter;
import com.sky.shop.utils.AppDialogUtils;
import com.sky.shop.utils.TakePhotoUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 名片编辑
 */
public class CardEditActivity extends BaseViewActivity<SellerContract.ICardPresenter>
    implements SellerContract.ICardView, ImageContract.IUploadView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;
    private ProductIntroduceOut productIntroduceOut = new ProductIntroduceOut();

    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_user_name)
    TextView userName;
    @BindView(R.id.app_main_sell_value)
    EditText sellValue;
    @BindView(R.id.app_time_value)
    EditText timeValue;

    @BindView(R.id.mobile_value)
    EditText mobileValue;
    @BindView(R.id.qq_value)
    EditText qqValue;
    @BindView(R.id.weixin_value)
    EditText weixinValue;

    private ImageContract.IUploadPresenter iUploadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_card_edit);
    }

    @Override
    protected SellerContract.ICardPresenter presenter() {
        iUploadPresenter = new UploadImagePresenter(context, this);
        return new CardPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //请求个人主页信息
        getPresenter().requestCardInfo();
    }

    @Override
    protected void init() {
        title.setText(R.string.app_card_edit_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void responseCardInfo(ProductIntroduceOut productIntroduceOut) {
        this.productIntroduceOut = productIntroduceOut;

        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(), appIcon,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        userName.setText(productIntroduceOut.getNick_name());
        timeValue.setText((TextUtils.isEmpty(productIntroduceOut.getWorktime()) ? "" :
                productIntroduceOut.getWorktime()));
        sellValue.setText((TextUtils.isEmpty(productIntroduceOut.getMain_business_desc()) ? "" :
                productIntroduceOut.getMain_business_desc()));

        qqValue.setText(productIntroduceOut.getQq());
        weixinValue.setText(productIntroduceOut.getWeixin());
        mobileValue.setText(productIntroduceOut.getMobile());
    }

    @Override
    public void responseAddCard(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @Override
    public void setStatus(String status_code) {

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
            ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(), appIcon,
                    R.mipmap.app_default_icon, R.mipmap.app_default_icon);
            iUploadPresenter.uploadFile(msg);
        }
    }

    @OnClick(R.id.app_icon)
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
        if (TextUtils.isEmpty(sellValue.getText().toString().trim())){
            T.showShort(context, "请输入主营业务");
            return;
        }
        if (TextUtils.isEmpty(timeValue.getText().toString().trim())){
            T.showShort(context, "请输入从业时间");
            return;
        }
        if (!StringUtil.isPhone(mobileValue.getText().toString().trim())){
            T.showShort(context, "请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(qqValue.getText().toString().trim())){
            T.showShort(context, "请输入QQ号码");
            return;
        }
        if (TextUtils.isEmpty(weixinValue.getText().toString().trim())){
            T.showShort(context, "请输入微信号码");
            return;
        }
        productIntroduceOut.setMain_business_desc(sellValue.getText().toString().trim());
        productIntroduceOut.setWorktime(timeValue.getText().toString().trim());
        productIntroduceOut.setMobile(mobileValue.getText().toString().trim());
        productIntroduceOut.setQq(qqValue.getText().toString().trim());
        productIntroduceOut.setWeixin(weixinValue.getText().toString().trim());
        getPresenter().requestAddCard(productIntroduceOut);
    }
}
