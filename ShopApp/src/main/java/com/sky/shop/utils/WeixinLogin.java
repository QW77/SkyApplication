package com.sky.shop.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
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
    private Context context;
    private static IWXAPI api;
    public static final String SECRET = "7a751ba5339f7a4630d631d54d2d3770";
    public static final String APP_ID = "wx9d3a7b8ccd271f56";
    public static IWeixinLoginListener mIWeixinLoginListener;

    public static WeixinLogin getInstance(Context context){
        if (null == instance){
            synchronized (WeixinLogin.class){
                if (null == instance){
                    instance = new WeixinLogin(context);
                }
            }
        }
        return instance;
    }

    public WeixinLogin(Context context){
        this.context = context;

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
     * 登录微信
     */
    public void loginWeixin(IWeixinLoginListener iWeixinLoginListener){
        mIWeixinLoginListener = iWeixinLoginListener;
        if (!api.isWXAppInstalled()){
            T.showShort(context, "您还未安装微信客户端！");
            return;
        }
        DialogUtils.showLoading((Activity) context);
        SendAuth.Req req = new SendAuth.Req();

        //授权读取用户信息
        req.scope = "snsapi_userinfo";

        //用于保持请求和回调的状态，授权请求后原样带回给第三方
        req.state = "app_wechat";

        //向微信发送请求
        api.sendReq(req);
    }

    /**
     * 通过code获取access_token
     * @param code
     */
    public void getAccessToken(final String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + APP_ID +
                "&secret=" + SECRET +
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
                        mIWeixinLoginListener.onFailure("登录失败，请重新登录");
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                if (null != mIWeixinLoginListener){
                    mIWeixinLoginListener.onFailure("操作失败");
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
                        mIWeixinLoginListener.onSuccess(new JSONObject(success));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (null != mIWeixinLoginListener){
                            mIWeixinLoginListener.onFailure("操作失败");
                        }
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                if (null != mIWeixinLoginListener){
                    mIWeixinLoginListener.onFailure("操作失败");
                }
            }
        });
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
        void onSuccess(JSONObject jsonObject);
        void onFailure(String msg);
    }
}
