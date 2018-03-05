package com.sky.shop.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.QQLogin;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.AppKey;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.QQWeixinIn;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.QQContract;
import com.sky.shop.contract.UserContract;
import com.sky.shop.presenter.CodeActivityPresenter;
import com.sky.shop.presenter.activity.LoginActivityPresenter;
import com.sky.shop.ui.activity.MineShopCenterActivity;
import com.sky.shop.ui.activity.OpenIMShop.OpenIMUserBean;
import com.sky.shop.ui.activity.shop.CardActivity;
import com.sky.shop.ui.activity.shop.SellerCenterActivity;
import com.sky.shop.ui.activity.shop.SellerChoseTypeActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseViewActivity<UserContract.ILoginPresenter>
        implements UserContract.ILoginView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;
    @BindView(R.id.driver_login_btn)
    TextView login;//登录按钮
    @BindView(R.id.driver_login_name_et)
    EditText loginName;//登录名称
    @BindView(R.id.driver_login_pwd_et)
    EditText loginPwd;//登录密码
    private long exitTime;

    private UserContract.ICodePresenter iCodePresenter = null;
    private YWIMKit mIMKit;
    final String APP_KEY = "24642621";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_login);
    }

    @Override
    protected UserContract.ILoginPresenter presenter() {
        iCodePresenter = new CodeActivityPresenter(this, this);
        return new LoginActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_login_string);
    }

    @Override
    public void initViewsAndEvents() {

    }


    @OnClick(R.id.driver_login_btn)
    void clickLogin(){
        if (TextUtils.isEmpty(loginName.getText().toString())){
            T.showShort(context, "用户名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(loginPwd.getText().toString())){
            T.showShort(context, "用户密码不能为空！");
            return;
        }
        ForgetIn forgetIn = new ForgetIn();
        forgetIn.setMobile(loginName.getText().toString());
        forgetIn.setUser_type("2");
        forgetIn.setPwd(Md5Util.md5(loginPwd.getText().toString()));
        getPresenter().login(forgetIn);


    }

    @OnClick(R.id.driver_no_account)
    void clickRegister(){
        startActivity(new Intent(context, RegisterActivity.class));
    }

    @OnClick(R.id.driver_forget_pwd)
    void clickForgetPwd(){
        startActivity(new Intent(context, ForgetPwdActivity.class));
    }

    @Override
    public void showUserInfo() {
        OpenIMUserBean openIMUserBean = new OpenIMUserBean();
//        user_id = UserBean.getInstance().getUser_id();
//        password = UserBean.getInstance().getPwd();
        openIMUserBean.setUser_id(UserBean.getInstance().getUser_id());
        openIMUserBean.setPwd(UserBean.getInstance().getPwd());
        openIMUserBean.setUser_type("2");
        mIMKit = YWAPI.getIMKitInstance(openIMUserBean.getUser_id(), APP_KEY);
        getPresenter().loginOpenIM(openIMUserBean);
        onResume();
    }
    @Override
    public void showOnSuccess(OpenIMUserBean openIMUserBean) {
        getLogin(openIMUserBean);
    }

    public void getLogin(final OpenIMUserBean openIMUserBean) {
        //开始登录
        IYWLoginService loginService = mIMKit.getLoginService();
        final YWLoginParam loginParam = YWLoginParam.createLoginParam(openIMUserBean.getUser_id(), openIMUserBean.getPwd());
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                Toast.makeText(context,"登录成功！",Toast.LENGTH_SHORT).show();
//                final String appkey = "24629506"; //消息接收者appKey
//                Fragment fragment = mIMKit.getChattingFragment(appkey);
//                startActivityFromFragment(fragment, mIMKit.getConversationActivityIntent(), 0);
//                finish();
            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onError(int errCode, String description) {
                if (errCode == 1) {
                    getPresenter().registerOpenIM(openIMUserBean);
                    return;
                }
                if (errCode == 2) {
//                    getPresenter().registerOpenIM(forgetIn);

                    getPresenter().upDateOpenIM(openIMUserBean);
                    return;
                }
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                IMNotificationUtils.getInstance().showToast(getApplicationContext(), description);

            }
        });
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        DialogUtils.hideLoading();
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            T.showShort(context, "再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
//            this.moveTaskToBack(true);
            BaseAppManager.getInstance().AppExit(context);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserBean.getInstance().checkUserLogin()){
            String s = UserBean.getInstance().getUserCache().getSeller_type();
            if (TextUtils.isEmpty(s)){//没有类型
                startActivity(new Intent(context, SellerChoseTypeActivity.class));
                finish();
            }else{
                switch (s) {
                    case "店铺":
                        Intent i = new Intent(context, MineShopCenterActivity.class);
                        i.putExtra(AppKey.HomePage.APP_TAB_LABEL, AppKey.HomePage.openIM);
                        startActivity(i);
                        finish();
                        break;
                    case "名片":
                        startActivity(new Intent(context, CardActivity.class));
                        finish();
                        break;
                    case "个人主页":
                        startActivity(new Intent(context, SellerCenterActivity.class));
                        finish();
                        break;
                }
            }

        }
    }
}
