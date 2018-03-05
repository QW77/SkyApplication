package com.sky.app.library.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * QQ一键登录
 * Created by sky on 2017/4/5.
 */

public class QQLogin {
    private Activity act;
    public static Tencent mTencent;
    private String SCOPE = "all";
    public static final String DRIVER_APP_ID = "1105981109";
    public static final String TRANSPORT_APP_ID = "1106130612";
    public static final String MAIN_APP_ID = "1106153558";
    public static final String SHOP_APP_ID = "1106153558";
    private UserInfo userInfo;
    private IQQLoginListener iqqLoginListener;

    public QQLogin(Activity act, String appid){
        this.act = act;
        this.mTencent = Tencent.createInstance(appid, act);
    }

    /**
     * 登录
     */
    public void login(IQQLoginListener iqqLoginListener){
        this.iqqLoginListener = iqqLoginListener;
        if (!mTencent.isSessionValid()){
            singleLgin();
        }else{
            logout(act);
            singleLgin();
        }
    }

    /**
     * 单纯的登录
     */
    private void singleLgin(){
        if (!mTencent.isSessionValid()){
            this.mTencent.login(act, SCOPE, loginListener);
        }
    }

    /**
     * 登录
     */
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };

    /**
     * 初始化参数
     * @param jsonObject
     */
    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            userInfo = new UserInfo(act, mTencent.getQQToken());
            userInfo.getUserInfo(userInfoListener);

        } else {
            if (null != iqqLoginListener){
                iqqLoginListener.onFailure("操作失效");
            }
        }
    }

    /**
     * 异步操作
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {//返回用户信息
                L.msg("返回数据:" + msg.obj);
                JSONObject response = (JSONObject) msg.obj;
                try {
                    if (response.getInt("ret") == com.sky.app.library.base.bean.Constants.HttpStatus.HTTP_OK_STATUS){
                        if (null != iqqLoginListener){
                            iqqLoginListener.onSuccess(mTencent.getOpenId(), response);
                        }
                    }else{
                        if (null != iqqLoginListener){
                            iqqLoginListener.onFailure("操作失效");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    };

    /**
     * 获取用户信息
     */
    private IUiListener userInfoListener = new IUiListener() {

        @Override
        public void onError(UiError e) {
            if (null != iqqLoginListener){
                iqqLoginListener.onFailure("操作失败");
            }
        }

        @Override
        public void onComplete(final Object response) {
            Message msg = new Message();
            msg.obj = response;
            msg.what = 0;
            mHandler.sendMessage(msg);
        }

        @Override
        public void onCancel() {
            T.showShort(act, "取消操作");
            if (null != iqqLoginListener){
                iqqLoginListener.onFailure("取消操作");
            }
        }
    };

    /**
     * 登录回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
    }

    /**
     * 基础类
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                if (null != iqqLoginListener){
                    iqqLoginListener.onFailure("操作失败");
                }
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                if (null != iqqLoginListener){
                    iqqLoginListener.onFailure("操作失败");
                }
                return;
            }
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {}

        @Override
        public void onError(UiError e) {
            if (null != iqqLoginListener){
                iqqLoginListener.onFailure("操作失败");
            }
        }

        @Override
        public void onCancel() {
            T.showShort(act, "取消操作");
            if (null != iqqLoginListener){
                iqqLoginListener.onFailure("取消操作");
            }
        }
    }

    /**
     * 注销
     */
    public static void logout(Activity act){
        if (null != mTencent && mTencent.isSessionValid()){
            mTencent.logout(act);
        }
    }

    /**
     * QQ登录回调
     */
    public interface IQQLoginListener{
        void onSuccess(String token, JSONObject jsonObject);
        void onFailure(String msg);
    }
}
