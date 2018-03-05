package com.sky.shop.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.Add_Account_ApplyBean;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.presenter.Add_Account_ApplyPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * zhanglf
 * 添加提现账户
 */
public class Add_Account_ApplyActivity extends BaseViewActivity<AccountContract.Add_Account_ApplyPresenter>
        implements AccountContract.Add_Account_ApplyView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView driverTitle;
    @BindView(R.id.username_et)
    EditText username_et;//用户名称
    @BindView(R.id.blank_name_et)
    EditText blank_name_et;//银行名称
    @BindView(R.id.z_blank_et)
    EditText z_blank_et;//支行名称
    @BindView(R.id.blank_number_et)
    EditText blank_number_et;//银行账号
    @BindView(R.id.submit_apply_tv)
    TextView submit_apply_tv;//确定
    @BindView(R.id.remark)
    EditText remark;//备注

    String username_text,blank_name_text,z_blank_text,blank_number_text,idcard_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_apply);
    }

    @Override
    protected void init() {
        driverTitle.setText(R.string.add_account_apply_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @OnClick(R.id.submit_apply_tv) void getsubmit_apply_tv(){
            if (chack()){
                Add_Account_ApplyBean info=new Add_Account_ApplyBean();
                info.setBank_type("1");
                info.setUser_id(UserBean.getInstance().getCacheUid());
                info.setBank_name(blank_name_text);
                info.setBank_name_sub(z_blank_text);
                info.setBank_user_name(username_text);
                info.setBank_no(blank_number_text);
                info.setRemark(remark.getText().toString().trim());
                getPresenter().forget(UserBean.getInstance().getCacheUid(),info);
            }
    }
    public boolean chack(){
        username_text=username_et.getText().toString().trim();
        blank_name_text=blank_name_et.getText().toString().trim();
        z_blank_text=z_blank_et.getText().toString().trim();
        blank_number_text=blank_number_et.getText().toString().trim();
        if (TextUtils.isEmpty(username_text)){
            T.showShort(this,"用户名称不能为空");
            return false;
        }else if (TextUtils.isEmpty(blank_name_text)){
            T.showShort(this,"银行名称不能为空");
            return false;
        }else if (TextUtils.isEmpty(z_blank_text)){
            T.showShort(this,"支行名称不能为空");
            return false;
        }else if (TextUtils.isEmpty(blank_number_text)){
            T.showShort(this,"银行账号不能为空");
            return false;
        }else {
            return true;
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
    protected AccountContract.Add_Account_ApplyPresenter presenter() {
        return new Add_Account_ApplyPresenter(this, this);
    }

    @Override
    public void onSuccess() {
        finish();
        T.showShort(context, "增加提现账户成功");
    }
}
