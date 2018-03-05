package com.sky.shop.ui.activity.OpenIMShop;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.T;
import com.sky.shop.bean.UserBean;

/**
 * openIM登陆
 */
public class OpenIMLoginActivity extends BaseViewActivity<UserOpenIM.IOpenIMPresenter>
        implements UserOpenIM.IOpenIMView {
    private YWIMKit mIMKit;
    final String APP_KEY = "24642621";
    private String user_id;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OpenIMUserBean openIMUserBean = new OpenIMUserBean();
//        user_id = UserBean.getInstance().getUser_id();
//        password = UserBean.getInstance().getPwd();
        openIMUserBean.setUser_id(UserBean.getInstance().getUser_id());
        openIMUserBean.setPwd(UserBean.getInstance().getPwd());
        openIMUserBean.setUser_type("2");
        mIMKit = YWAPI.getIMKitInstance(openIMUserBean.getUser_id(), APP_KEY);
        getPresenter().loginOpenIM(openIMUserBean);
    }

    public void getLogin(final OpenIMUserBean openIMUserBean) {
        //开始登录
        IYWLoginService loginService = mIMKit.getLoginService();
        final YWLoginParam loginParam = YWLoginParam.createLoginParam(openIMUserBean.getUser_id(), openIMUserBean.getPwd());
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                final String appkey = "24629506"; //消息接收者appKey
                Fragment fragment = mIMKit.getChattingFragment(appkey);
                startActivityFromFragment(fragment, mIMKit.getConversationActivityIntent(), 0);
                finish();
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
    protected UserOpenIM.IOpenIMPresenter presenter() {
        return new OpenIMLoginActivityPresenter(this, this);
    }

    @Override
    protected void init() {
    }

    @Override
    public void initViewsAndEvents() {

    }

    @Override
    public void showOnSuccess(OpenIMUserBean openIMUserBean) {
        getLogin(openIMUserBean);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }
}
