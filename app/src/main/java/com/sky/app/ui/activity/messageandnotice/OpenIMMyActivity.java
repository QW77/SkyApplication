package com.sky.app.ui.activity.messageandnotice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.sky.app.bean.UserBean;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.ui.activity.openIM.OpenIMLoginActivityPresenter;
import com.sky.app.ui.activity.openIM.OpenIMUserBean;
import com.sky.app.ui.activity.openIM.UserOpenIM;

/**
 * openIM登陆
 */
public class OpenIMMyActivity extends BaseViewActivity<UserOpenIM.IOpenIMPresenter>
        implements UserOpenIM.IOpenIMView {






    private YWIMKit mIMKit;
    final String APP_KEY = "24629506";
    private String user_id;
    private String password;
    // final String target = target; //消息接收者ID
    //消息接收者appKey
    private String target;
    private String appkey = "24642621";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("seller_id", Context.MODE_PRIVATE);
        target = sp.getString("seller_id", null);
        user_id = UserBean.getInstance().getUser_id();
        password = UserBean.getInstance().getPwd();
        OpenIMUserBean openIMUserBean = new OpenIMUserBean();
        openIMUserBean.setUser_id(user_id);
        openIMUserBean.setPwd(password);
        openIMUserBean.setUser_type("1");
        getPresenter().loginOpenIM(openIMUserBean);
        mIMKit = YWAPI.getIMKitInstance(user_id, APP_KEY);

    }

    public void getLogin(final OpenIMUserBean openIMUserBean) {
        //开始登录
        IYWLoginService loginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(openIMUserBean.getUser_id(), openIMUserBean.getPwd());
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                if (target != null) {
                    Intent intent = mIMKit.getChattingActivityIntent(target, appkey);
                    startActivity(intent);
                    sp.edit().remove("seller_id").commit();
                } else {
                    final String appkey = "24629506"; //消息接收者appKey
                    Fragment fragment = mIMKit.getChattingFragment(appkey);
                    startActivityFromFragment(fragment, mIMKit.getConversationActivityIntent(), 0);
                }
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
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestoryActivity() {

    }
}
