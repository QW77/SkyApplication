package com.sky.app.ui.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.app.MainApplication;
import com.sky.app.R;
import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.ProvCityArea;
import com.sky.app.contract.MineContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.MyAddressDetailPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的收货地址详情
 */
public class MyAddressDetailActivity extends BaseViewActivity<MineContract.IMyAddressDetailPresenter>
        implements MineContract.IMyAddressDetailView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_user_name)
    EditText userName;
    @BindView(R.id.app_mobile)
    EditText mobile;
    @BindView(R.id.app_address)
    TextView address;
    @BindView(R.id.app_detail)
    EditText detail;
    @BindView(R.id.app_save)
    TextView save;

    AddressDetail addressDetail;
    ProvCityArea provCityArea;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_mine_my_address_detail);
    }

    @Override
    protected MineContract.IMyAddressDetailPresenter presenter() {
        return new MyAddressDetailPresenter(this, this);
    }

    @Override
    protected void init() {
        this.flag = getIntent().getStringExtra("flag");
        if ("2".equals(flag)){//详情
            this.addressDetail = (AddressDetail) getIntent().getSerializableExtra("address");
            title.setText(R.string.app_my_address_detail_string);
            save.setVisibility(View.GONE);
        }else if ("3".equals(flag)){//修改
            this.addressDetail = (AddressDetail) getIntent().getSerializableExtra("address");
            title.setText(R.string.app_my_address_fix_string);
        }else{
            title.setText(R.string.app_my_address_add_string);
        }
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        if (null != addressDetail){
            userName.setText(addressDetail.getContact_name());
            mobile.setText(addressDetail.getContact_mobile());
            address.setText(addressDetail.getProv_name() + " " + addressDetail.getCity_name() +
                    " " + addressDetail.getArea_name());
            detail.setText(addressDetail.getContact_address());
        }
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
    public void showSuccess(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @OnClick(R.id.app_save)
    void clickSave(){
        if (TextUtils.isEmpty(userName.getText().toString().trim())){
            T.showShort(context, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(mobile.getText().toString().trim())){
            T.showShort(context, "请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(address.getText().toString().trim())){
            T.showShort(context, "请输入地址");
            return;
        }
        if (TextUtils.isEmpty(detail.getText().toString().trim())){
            T.showShort(context, "请输入详细地址");
            return;
        }
        if (null == addressDetail){
            addressDetail = new AddressDetail();
        }
        addressDetail.setContact_name(userName.getText().toString());

        if (null != provCityArea){
            addressDetail.setProv_id(provCityArea.getPro_id());
            addressDetail.setCity_id(provCityArea.getCity_id());
            addressDetail.setArea_id(provCityArea.getArea_id());
        }
        addressDetail.setContact_mobile(mobile.getText().toString());
        addressDetail.setContact_address(detail.getText().toString());
        addressDetail.setIs_default(1);

        if ("3".equals(flag)){//修改
            getPresenter().edit(addressDetail);
        }else{
            getPresenter().add(addressDetail);
        }
    }

    @OnClick(R.id.app_address)
    void clickAddress(){
        startActivity(new Intent(context, ProvActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != MainApplication.provCityArea && TextUtils.isEmpty(flag)){
            this.provCityArea = MainApplication.provCityArea;
            address.setText(provCityArea.getPro_name() + " " + provCityArea.getCity_name() +
                    " " + provCityArea.getArea_name());
        }
    }
}
