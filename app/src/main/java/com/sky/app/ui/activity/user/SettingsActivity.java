package com.sky.app.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AppKey;
import com.sky.app.bean.BindIn;
import com.sky.app.bean.BindOut;
import com.sky.app.bean.QQWeixinIn;
import com.sky.app.bean.UpdatePwdIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.QQLogin;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.CodeActivityPresenter;
import com.sky.app.presenter.SettingsActivityPresenter;
import com.sky.app.ui.activity.MainActivity;
import com.sky.app.utils.AppDialogUtils;
import com.sky.app.utils.WeixinLogin;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingsActivity extends BaseViewActivity<UserContract.ISettingPresenter>
        implements UserContract.ISettingView, WeixinLogin.IWeixinLoginListener, QQLogin.IQQLoginListener{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;
    @BindView(R.id.app_mobile)
    TextView mobile;
    @BindView(R.id.app_weixin)
    TextView weixin;
    @BindView(R.id.app_qq)
    TextView qq;

    private UserContract.ICodePresenter iCodePresenter;
    private QQLogin qqLogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings);
        this.qqLogin = new QQLogin(this, QQLogin.MAIN_APP_ID);
    }

    @Override
    protected UserContract.ISettingPresenter presenter() {
        iCodePresenter = new CodeActivityPresenter(this, this);
        return new SettingsActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_settings_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        onRefresh();
    }

    @OnClick(R.id.app_logout_btn)
    void clickLogout(){
        QQLogin.logout(this);
        UserBean.getInstance().cleanUserCache();
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(AppKey.HomePage.APP_TAB_LABEL, AppKey.HomePage.mime);
        startActivity(i);
        BaseAppManager.getInstance().finishAllActivity();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        qqLogin.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 刷新
     */
    private void onRefresh(){
        getPresenter().queryUserDetail();
    }

    @Override
    public void refreshView(BindOut bindOut) {
        if (1 == bindOut.getMobile()){
            mobile.setText(TextUtils.isEmpty(AppUtils.change4Mobile(bindOut.getMobile_num())) ?
                    "已绑定" : AppUtils.change4Mobile(bindOut.getMobile_num()));
            mobile.setEnabled(false);
        }else{
            mobile.setText("未绑定");
            mobile.setEnabled(true);
        }

        if (1 == bindOut.getWeixin()){
            weixin.setText(TextUtils.isEmpty(bindOut.getWeixin_nickname()) ? "已绑定" : bindOut.getWeixin_nickname());
            weixin.setEnabled(false);
        }else{
            weixin.setText("未绑定");
            weixin.setEnabled(true);
        }

        if (1 == bindOut.getQq()){
            qq.setText(TextUtils.isEmpty(bindOut.getQq_nickname()) ? "已绑定" : bindOut.getQq_nickname());
            qq.setEnabled(false);
        }else{
            qq.setText("未绑定");
            qq.setEnabled(true);
        }
    }

    @Override
    public void responseUpdatePwd(String pwd) {
        T.showShort(context, pwd);
        AppDialogUtils.closePwdDialog();
    }

    @Override
    public void responseBindData(String msg) {
        T.showShort(context, msg);
        AppDialogUtils.closeBindMobileDialog();
        onRefresh();
    }

    @OnClick(R.id.app_mobile)
    void clickMobile(){
        //绑定手机号码
        AppDialogUtils.showBindMobileDialog(this, new IBindMobileCallBack() {
            @Override
            public void bind(String mobile) {
                BindIn bindIn = new BindIn();
                bindIn.setType(2);
                bindIn.setValue(mobile);
                getPresenter().bindData(bindIn);
            }

            @Override
            public void send(String mobile) {
                iCodePresenter.sendCode(mobile);
            }
        }, true);
    }

    @OnClick(R.id.app_weixin)
    void clickWeixin(){
        if (!WeixinLogin.getInstance(context).isWXAppInstalled()){
            T.showShort(context, "您没有安装微信客户端!");
            return;
        }
        DialogUtils.showLoading(this);
        WeixinLogin.getInstance(context).loginWeixin(this);
    }

    @OnClick(R.id.app_qq)
    void clickQQ(){
        //绑定qq
        DialogUtils.showLoading(this);
        this.qqLogin.login(this);
    }

    @OnClick(R.id.app_pwd)
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

    @Override
    public void onSuccess(String token, JSONObject jsonObject) {
        DialogUtils.hideLoading();
        BindIn bindIn = new BindIn();
        bindIn.setType(1);
        bindIn.setValue(token);
        getPresenter().bindData(bindIn);
    }

    @Override
    public void onSuccess(QQWeixinIn qqWeixinIn) {
        DialogUtils.hideLoading();
        BindIn bindIn = new BindIn();
        bindIn.setType(0);
        bindIn.setValue(qqWeixinIn.getOpen_id());
        getPresenter().bindData(bindIn);
    }

    @Override
    public void onFailure(String msg) {
        DialogUtils.hideLoading();
        T.showShort(context, msg);
    }

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
}
