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
import android.view.MenuItem;
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
import com.sky.shop.bean.Area;
import com.sky.shop.bean.AreaList;
import com.sky.shop.bean.Category;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.DecorationCity;
import com.sky.shop.bean.DecorationCityList;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.SearchDecorationCity;
import com.sky.shop.bean.StringBean;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ImageContract;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.ShopDataPresenter;
import com.sky.shop.presenter.UploadImagePresenter;
import com.sky.shop.utils.AppDialogUtils;
import com.sky.shop.utils.ListDialog;
import com.sky.shop.utils.TakePhotoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商户资料
 */
public class ShopDataActivity extends BaseViewActivity<ShopContract.IShopDataPresenter>
        implements ShopContract.IShopDataView, ImageContract.IUploadView {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_icon)
    ImageView icon;
    @BindView(R.id.login_name)
    TextView loginName;
    @BindView(R.id.seller_name)
    EditText sellerName;
    @BindView(R.id.area_name)
    TextView areaName;
    @BindView(R.id.area_detail)
    EditText areaDetail;
    @BindView(R.id.decorate_city)
    TextView decorateCity;
    @BindView(R.id.company_type)
    TextView companyType;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.qq)
    EditText qq;
    @BindView(R.id.weixin)
    EditText weixin;
    @BindView(R.id.main_yinye)
    EditText mainYinye;
    @BindView(R.id.time)
    EditText time;

    private List<StringBean> areaList, decorateList, companyList;

    private ProductIntroduceOut productIntroduceOut = new ProductIntroduceOut();
    private ImageContract.IUploadPresenter iUploadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_shop_data);
    }

    @Override
    protected ShopContract.IShopDataPresenter presenter() {
        iUploadPresenter = new UploadImagePresenter(context, this);
        return new ShopDataPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.app_save:
                        if (TextUtils.isEmpty(sellerName.getText().toString().trim())){
                            T.showShort(context, "请输入商户名称");
                            return false;
                        }
//                        if (TextUtils.isEmpty(mobile.getText().toString().trim())){
//                            T.showShort(context, "请输入手机号码");
//                            return false;
//                        }
                        if (TextUtils.isEmpty(areaName.getText().toString())){
                            T.showShort(context, "请选择所属区域");
                            return false;
                        }
                        if (TextUtils.isEmpty(areaDetail.getText().toString().trim())){
                            T.showShort(context, "请输入地址");
                            return false;
                        }
                        /*if (TextUtils.isEmpty(decorateCity.getText().toString())){
                            T.showShort(context, "请选择装饰城");
                            return false;
                        }*/
                        if (TextUtils.isEmpty(companyType.getText().toString())){
                            T.showShort(context, "请选择厂家类型");
                            return false;
                        }
//                        if (TextUtils.isEmpty(email.getText().toString().trim())){
//                            T.showShort(context, "请输入邮箱");
//                            return false;
//                        }
//                        if (TextUtils.isEmpty(qq.getText().toString().trim())){
//                            T.showShort(context, "请输入qq号");
//                            return false;
//                        }
//                        if (TextUtils.isEmpty(weixin.getText().toString().trim())){
//                            T.showShort(context, "请输入微信号");
//                            return false;
//                        }
//                        if (TextUtils.isEmpty(mainYinye.getText().toString().trim())){
//                            T.showShort(context, "请输入主营描述");
//                            return false;
//                        }
//                        if (TextUtils.isEmpty(time.getText().toString().trim())){
//                            T.showShort(context, "请输入从业时间");
//                            return false;
//                        }
                        productIntroduceOut.setNick_name(sellerName.getText().toString().trim());
                        productIntroduceOut.setMobile(mobile.getText().toString().trim());
                        productIntroduceOut.setArea_id(areaName.getTag().toString());
                        productIntroduceOut.setArea_name(areaName.getText().toString().trim());
                        productIntroduceOut.setAddress(areaDetail.getText().toString().trim());
                        productIntroduceOut.setDecorative_id(decorateCity.getTag().toString());
                        productIntroduceOut.setManufacturer_type_id(companyType.getTag().toString());
                        productIntroduceOut.setEmail(email.getText().toString().trim());
                        productIntroduceOut.setQq(qq.getText().toString().trim());
                        productIntroduceOut.setWeixin(weixin.getText().toString().trim());
                        productIntroduceOut.setMain_business_desc(mainYinye.getText().toString().trim());
                        productIntroduceOut.setWorktime(time.getText().toString().trim());
                        getPresenter().updateShopInfo(productIntroduceOut);
                        break;
                }
                return false;
            }
        });
        onRefresh();
    }

    @Override
    protected void init() {
        title.setText(R.string.app_shop_data_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        toolbar.inflateMenu(R.menu.app_center_menu);

        getPresenter().requestAreaList();
        getPresenter().requestCompanyType();
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void getImageUrl(String url) {
        productIntroduceOut.setPic_url(url);
    }

    /**
     * 刷新
     */
    private void onRefresh(){
        getPresenter().queryUserInfo();
    }

    @Override
    public void responseUserInfo(UserBean userBean) {
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(), icon,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        loginName.setText(UserBean.getInstance().getUserCache().getMobile());
        sellerName.setText(userBean.getNick_name());
        areaName.setText(userBean.getArea_name());
        areaName.setTag(userBean.getArea_id());
        areaDetail.setText(userBean.getAddress());
        decorateCity.setText(userBean.getDecorative_name());
        decorateCity.setTag(userBean.getDecorative_id());
        companyType.setText(userBean.getManufacturer_type_name());
        companyType.setTag(userBean.getManufacturer_type_id());
        mobile.setText(userBean.getMobile());
        email.setText(userBean.getEmail());
        qq.setText(userBean.getQq());
        weixin.setText(userBean.getWeixin());
        mainYinye.setText(userBean.getMain_business_desc());
        time.setText(userBean.getWorktime());

        //查询装饰城
        queryDecorateCity(userBean.getArea_id());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode,
                resultCode, data, true);
        L.msg("返回的图片地址：" + msg);
        if (!TextUtils.isEmpty(msg)){
            ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(), icon,
                    R.mipmap.app_default_icon, R.mipmap.app_default_icon);
            iUploadPresenter.uploadFile(msg);
        }
    }


    @Override
    public void responseUpdateShopInfo(String msg) {
        UserBean userBean = UserBean.getInstance().getUserCache();
        userBean.setPic_url(productIntroduceOut.getPic_url());
        UserBean.getInstance().setUserCache(userBean);
        T.showShort(context, msg);
        finish();
    }

    @Override
    public void responseAreaList(AreaList areaList) {
        List<StringBean> beanList = new ArrayList<>();
        for (Area area : areaList.getList()){
            StringBean stringBean = new StringBean();
            stringBean.setId(area.getTwo_dir_id());
            stringBean.setValue(area.getTwo_dir_name());
            beanList.add(stringBean);
        }
        this.areaList = beanList;
    }

    @Override
    public void responseDecorateCity(DecorationCityList decorationCityList) {
        List<StringBean> decorationCities = new ArrayList<>();
        for (DecorationCity decorationCity : decorationCityList.getList()){
            StringBean bean = new StringBean();
            bean.setValue(decorationCity.getThree_dir_name());
            bean.setId(decorationCity.getThree_dir_id());
            decorationCities.add(bean);
        }
        this.decorateList = decorationCities;
    }

    @Override
    public void responseCompanyType(CategoryList categoryList) {
        List<StringBean> stringBeens = new ArrayList<>();
        for (Category category : categoryList.getList()){
            StringBean stringBean = new StringBean();
            stringBean.setId(category.getOne_dir_id());
            stringBean.setValue(category.getOne_dir_name());
            stringBeens.add(stringBean);
        }
        this.companyList = stringBeens;
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

    @OnClick(R.id.area_name)
    void clickAreaName(){
        AppDialogUtils.showListDialog(this, new ListDialog.OnItemClickListener() {
            @Override
            public void item(View v, int pos) {
                decorateCity.setText("");
                decorateCity.setTag("");
                areaName.setText(areaList.get(pos).getValue());
                areaName.setTag(areaList.get(pos).getId());
                AppDialogUtils.closeListDialog();

                //查询装饰城
                queryDecorateCity(areaList.get(pos).getId());
            }
        }, areaList);
    }

    @OnClick(R.id.decorate_city)
    void clickDecorate(){
        if (null == decorateList || decorateList.isEmpty()){
            T.showShort(context, "该区域下没有装饰城信息");
            return;
        }
        AppDialogUtils.showListDialog(this, new ListDialog.OnItemClickListener() {
            @Override
            public void item(View v, int pos) {
                decorateCity.setText(decorateList.get(pos).getValue());
                decorateCity.setTag(decorateList.get(pos).getId());
                AppDialogUtils.closeListDialog();
            }
        }, decorateList);
    }

    @OnClick(R.id.company_type)
    void clickCompanyType(){
        AppDialogUtils.showListDialog(this, new ListDialog.OnItemClickListener() {
            @Override
            public void item(View v, int pos) {
                companyType.setText(companyList.get(pos).getValue());
                companyType.setTag(companyList.get(pos).getId());
                AppDialogUtils.closeListDialog();
            }
        }, companyList);
    }

    /**
     * 查询装饰城
     */
    private void queryDecorateCity(String id){
        //查询装饰城
        SearchDecorationCity searchDecorationCity = new SearchDecorationCity();
        searchDecorationCity.setTwo_dir_id(id);
        getPresenter().requestDecorateCity(searchDecorationCity);
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
}
