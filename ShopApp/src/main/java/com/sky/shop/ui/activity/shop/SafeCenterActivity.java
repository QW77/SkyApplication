package com.sky.shop.ui.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.BindIn;
import com.sky.shop.bean.BindOut;
import com.sky.shop.bean.UpdatePwdIn;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.UserContract;
import com.sky.shop.presenter.CodeActivityPresenter;
import com.sky.shop.presenter.activity.SafeCenterPresenter;
import com.sky.shop.ui.activity.user.LoginActivity;
import com.sky.shop.utils.AppDialogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 安全中心中心
 */
public class SafeCenterActivity extends BaseViewActivity<UserContract.ISafeCenterPresenter>
    implements UserContract.ISafeCenterView/*, WeixinLogin.IWeixinLoginListener,
        QQLogin.IQQLoginListener*/{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.bind_mobile)
    TextView bindMobile;
    @BindView(R.id.bind_email)
    TextView bindEmail;
//    @BindView(R.id.app_weixin)
//    TextView weixin;
//    @BindView(R.id.app_qq)
//    TextView qq;

    private UserContract.ICodePresenter iCodePresenter;
//    private QQLogin qqLogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_safe_center);
//        this.qqLogin = new QQLogin(this, QQLogin.SHOP_APP_ID);
    }

    @Override
    protected UserContract.ISafeCenterPresenter presenter() {
        iCodePresenter = new CodeActivityPresenter(this, this);
        return new SafeCenterPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        onRefresh();
    }

    @Override
    protected void init() {
        title.setText(R.string.app_safe_center_string);
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

    @OnClick(R.id.shop_logout_btn)
    void logout(){
//        QQLogin.logout(this);
        UserBean.getInstance().cleanUserCache();
        startActivity(new Intent(context, LoginActivity.class));
        BaseAppManager.getInstance().finishAllActivity();
    }

    @Override
    public void refreshView(BindOut bindOut) {
        if (1 == bindOut.getMobile()){
            bindMobile.setText(TextUtils.isEmpty(AppUtils.change4Mobile(bindOut.getMobile_num())) ?
                    "已绑定" : AppUtils.change4Mobile(bindOut.getMobile_num()));
            bindMobile.setEnabled(false);
        }else{
            bindMobile.setText("未绑定");
            bindMobile.setEnabled(true);
        }
        if (1 == bindOut.getEmail()){
            bindEmail.setText(bindOut.getEmail_account());
            bindEmail.setEnabled(false);
        }else{
            bindEmail.setText("未绑定");
            bindEmail.setEnabled(true);
        }

//        if (1 == bindOut.getWeixin()){
//            weixin.setText(TextUtils.isEmpty(bindOut.getWeixin_nickname()) ? "已绑定" : bindOut.getWeixin_nickname());
//            weixin.setEnabled(false);
//        }else{
//            weixin.setText("未绑定");
//            weixin.setEnabled(true);
//        }
//
//        if (1 == bindOut.getQq()){
//            qq.setText(TextUtils.isEmpty(bindOut.getQq_nickname()) ? "已绑定" : bindOut.getQq_nickname());
//            qq.setEnabled(false);
//        }else{
//            qq.setText("未绑定");
//            qq.setEnabled(true);
//        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        qqLogin.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public void responseUpdatePwd(String pwd) {
        T.showShort(context, pwd);
        AppDialogUtils.closePwdDialog();
    }

    @Override
    public void responseBindData(String msg) {
        T.showShort(context, msg);
        AppDialogUtils.closeBindMobileDialog();
        AppDialogUtils.closeBindEmailDialog();
        onRefresh();
    }

//    @OnClick(R.id.app_weixin)
//    void clickWeixin(){
//        DialogUtils.showLoading(this);
//        WeixinLogin.getInstance(context).loginWeixin(this);
//    }
//
//    @OnClick(R.id.app_qq)
//    void clickQQ(){
//        //绑定qq
//        DialogUtils.showLoading(this);
//        this.qqLogin.login(this);
//    }

    /**
     * 刷新
     */
    private void onRefresh(){
        getPresenter().queryUserDetail();
    }

//    @OnClick(R.id.bind_mobile)
//    void clickMobile(){
//        //绑定手机号码
//        AppDialogUtils.showBindMobileDialog(this, new IBindMobileCallBack() {
//            @Override
//            public void bind(String mobile) {
//                BindIn bindIn = new BindIn();
//                bindIn.setType(2);
//                bindIn.setValue(mobile);
//                getPresenter().bindData(bindIn);
//            }
//
//            @Override
//            public void send(String mobile) {
//                iCodePresenter.sendCode(mobile);
//            }
//        }, true);
//    }

    @OnClick(R.id.login_pwd)
    void clickPwd(){
        //修改登录密码
        AppDialogUtils.showPwdDialog(this, new IPwdCallBack() {
            @Override
            public void pwd(String oldPwd, String newPwd) {
                UpdatePwdIn updatePwdIn = new UpdatePwdIn();
                updatePwdIn.setOld_password(Md5Util.md5(oldPwd));
                updatePwdIn.setNew_password(Md5Util.md5(newPwd));
                getPresenter().updatePwd(updatePwdIn);
            }
        });
    }

    @OnClick(R.id.bind_email)
    void clickEmail(){
        AppDialogUtils.showBindEmailDialog(this, new IBindEmailCallBack() {
            @Override
            public void bind(String email) {
                BindIn bindIn = new BindIn();
                bindIn.setType(3);
                bindIn.setValue(email);
                getPresenter().bindData(bindIn);
            }
        });
    }

//    @Override
//    public void onSuccess(JSONObject jsonObject) {
//        DialogUtils.hideLoading();
//        try {
//            String openid = jsonObject.has("openid") ? jsonObject.getString("openid") : "";
//            if (TextUtils.isEmpty(openid)){
//                T.showShort(context, "微信返回数据异常");
//                return;
//            }
//            BindIn bindIn = new BindIn();
//            bindIn.setType(0);
//            bindIn.setValue(openid);
//            getPresenter().bindData(bindIn);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onSuccess(String token, JSONObject jsonObject) {
//        DialogUtils.hideLoading();
//        BindIn bindIn = new BindIn();
//        bindIn.setType(1);
//        bindIn.setValue(token);
//        getPresenter().bindData(bindIn);
//    }

//    @Override
//    public void onFailure(String msg) {
//        DialogUtils.hideLoading();
//        T.showShort(context, msg);
//    }

    /**
     * 修改密码弹出回调
     */
    public interface IPwdCallBack{
        void pwd(String oldPwd, String newPwd);
    }

    /**
     * 绑定手机
     */
    public interface IBindMobileCallBack{
        void bind(String mobile);
        void send(String mobile);
    }

    /**
     * 绑定邮箱
     */
    public interface IBindEmailCallBack{
        void bind(String email);
    }
}
