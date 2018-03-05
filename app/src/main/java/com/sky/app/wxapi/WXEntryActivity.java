package com.sky.app.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.sky.app.library.utils.L;
import com.sky.app.utils.WeixinLogin;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.umeng.weixin.callback.WXCallbackActivity;

import static com.sky.app.utils.WeixinLogin.mIWeixinLoginListener;

/**
 * 微信一键登录回调*/
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WeixinLogin.getInstance(this).onNewIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

	@Override
	public void onResp(BaseResp resp) {
		switch (resp.getType()) {
			case ConstantsAPI.COMMAND_SENDAUTH:
				switch (resp.errCode){
					case BaseResp.ErrCode.ERR_OK://发送成功
						String code = ((SendAuth.Resp)resp).code;
						WeixinLogin.getInstance(this).getAccessToken(code);
						L.msg("返回的Code数据:" + code);
						break;
					case BaseResp.ErrCode.ERR_USER_CANCEL:
						if (null != mIWeixinLoginListener){
							mIWeixinLoginListener.onFailure("用户取消登录");
						}
						break;
					case BaseResp.ErrCode.ERR_AUTH_DENIED:
					case BaseResp.ErrCode.ERR_SENT_FAILED:
					case BaseResp.ErrCode.ERR_UNSUPPORT:
					default:
						if (null != mIWeixinLoginListener){
							mIWeixinLoginListener.onFailure("登录失败");
						}
						break;
				}
				finish();
				break;
			case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
				finish();
				break;
			case  ConstantsAPI.COMMAND_PAY_BY_WX:
				switch (resp.errCode){
					case BaseResp.ErrCode.ERR_OK:
						break;
					case BaseResp.ErrCode.ERR_USER_CANCEL:
					default:
						break;
				}
				break;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		WeixinLogin.getInstance(this).onNewIntent(intent, this);
	}
}