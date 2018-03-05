package com.sky.app.model;

import android.content.Context;

import com.sky.app.api.ApiService;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.StringUtil;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.CodeActivityPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送验证码
 * Created by sky on 2017/2/18.
 */

public class CodeModel extends BaseModel<CodeActivityPresenter> implements UserContract.ICodeModel{

    public CodeModel(Context context, CodeActivityPresenter codeActivityPresenter){
        super(context, codeActivityPresenter);
    }

    @Override
    public void sendCode(String mobile) {
        final String code = StringUtil.randStr(6);
        L.msg("验证码===>" + code);
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("msisdn", mobile);
        dataMap.put("sms_content", "" + code + "是本次操作的手机验证码，如非本人操作，请忽略本短信。【51工匠】");
        dataMap.put("client_ip", "127.0.0.1");
        dataMap.put("company_id", "CP2017021701");
        dataMap.put("sms_type", "1");
        dataMap.put("priority", "1");
        dataMap.put("pre_process_time", "0");
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_CODE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestCode(dataMap),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBean.getInstance().setCode(code);
                    }

                    @Override
                    public void onFailure(String error) {
                        UserBean.getInstance().setCode(code);
                    }
                }));
    }
}
