package com.sky.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sky.app.bean.QQWeixinIn;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.library.utils.http.SimpleHttpUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信一键登录
 * Created by sky on 2017/4/6.
 */

public class WeixinLogin {
    private static WeixinLogin instance = null;
    private static Context context;
    private static IWXAPI api;
    public static final String SECRET = "6c29c4b61d4d27522b9d19019137b2e9";
    public static final String APP_ID = "wxc2cc69d02bdd7f2c";
    public static IWeixinLoginListener mIWeixinLoginListener;

    public static WeixinLogin getInstance(Context ctx){
        context = ctx;
        if (null == instance){
            synchronized (WeixinLogin.class){
                if (null == instance){
                    instance = new WeixinLogin();
                }
            }
        }
        return instance;
    }

    public WeixinLogin(){
        //初始化微信组件
        initWeiXin();
    }

    /**
     * 初始化微信组件
     * @return
     */
    private void initWeiXin(){
        api = WXAPIFactory.createWXAPI(context, APP_ID, false);
        api.registerApp(APP_ID);
    }

    /**
     * 判断微信是否安装客户端
     * @return
     */
    public boolean isWXAppInstalled() {
        return api.isWXAppInstalled();
    }
    /**
     * 登录微信
     */
    public void loginWeixin(IWeixinLoginListener iWeixinLoginListener){
        mIWeixinLoginListener = iWeixinLoginListener;
        SendAuth.Req req = new SendAuth.Req();

        //授权读取用户信息
        req.scope = "snsapi_userinfo";

        //用于保持请求和回调的状态，授权请求后原样带回给第三方
        req.state = "app_wechat";

        //向微信发送请求
        api.sendReq(req);
    }

    /**
     * 初始化onIntent事件
     * @param intent
     */
    public void onNewIntent(Intent intent, IWXAPIEventHandler iwxapiEventHandler){
        api.handleIntent(intent, iwxapiEventHandler);
    }

    /**
     * 微信登录回调
     */
    public interface IWeixinLoginListener{
        void onSuccess(QQWeixinIn qqWeixinIn);
        void onFailure(String msg);
    }

    /**
     * 通过code获取access_token
     */
    public void getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + WeixinLogin.APP_ID +
                "&secret=" + WeixinLogin.SECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        SimpleHttpUtils.getInstance((Activity) context).get(url, new HttpUtils.IHttpCallBackListener() {
            @Override
            public void onSuccess(String success) {
                try {
                    JSONObject json = new JSONObject(success);
                    getUserInfo(json.getString("access_token"), json.getString("openid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (null != mIWeixinLoginListener){
                        mIWeixinLoginListener.onFailure("操作失败，请重新试一下");
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                if (null != mIWeixinLoginListener){
                    mIWeixinLoginListener.onFailure("操作失败，请重新试一下");
                }
            }
        });
    }

    /**
     * 获取用户信息
     * @param access_token
     * @param openid
     */
    private void getUserInfo(String access_token, String openid){
        String url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + access_token +
                "&openid=" + openid;
        SimpleHttpUtils.getInstance((Activity) context).get(url, new HttpUtils.IHttpCallBackListener() {
            @Override
            public void onSuccess(String success) {
                if (null != mIWeixinLoginListener){
                    try {
                        QQWeixinIn qqWeixinIn = new QQWeixinIn();
                        JSONObject jsonObject = new JSONObject(success);
                        qqWeixinIn.setOpen_id(jsonObject.getString("openid"));
                        qqWeixinIn.setNickname(jsonObject.getString("nickname"));
                        qqWeixinIn.setGender(1 == jsonObject.getInt("sex") ? "男" : "女");
                        qqWeixinIn.setFigureurl(jsonObject.getString("headimgurl"));
                        qqWeixinIn.setUser_type("1");
                        mIWeixinLoginListener.onSuccess(qqWeixinIn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (null != mIWeixinLoginListener){
                            mIWeixinLoginListener.onFailure("操作失败，请重新试一下");
                        }
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                if (null != mIWeixinLoginListener){
                    mIWeixinLoginListener.onFailure("操作失败，请重新试一下");
                }
            }
        });
    }
}
