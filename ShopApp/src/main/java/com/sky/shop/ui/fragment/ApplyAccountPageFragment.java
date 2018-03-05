package com.sky.shop.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.AccountBean;
import com.sky.shop.bean.ApplyAccountIn;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.presenter.fragment.ApplyAccountFragmentPresenter;
import com.sky.shop.ui.activity.user.ChoosWithdrawalseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请提现账户Tab页
 * Created by Administrator on 2017/2/30.
 */
public class ApplyAccountPageFragment extends BaseViewFragment<AccountContract.IApplyAccountPresenter>
        implements AccountContract.IApplyAccountView {

    @BindView(R.id.chack_code)
    RelativeLayout chack_code;
    @BindView(R.id.bank_id_tv)
    TextView bank_id_tv;
    @BindView(R.id.yue_tv)
    TextView yue_tv;
    @BindView(R.id.driver_bank_et)
    EditText driver_bank_et;
    @BindView(R.id.driver_real_name_et)
    EditText driver_real_name_et;
    @BindView(R.id.driver_card_et)
    EditText driver_card_et;
    @BindView(R.id.driver_remark_et)
    EditText remark;
    AccountBean bean;

    @BindView(R.id.driver_apply_money_et)
    EditText applyMoneyEt;
    @BindView(R.id.driver_confirm_apply_money_et)
    EditText confirmApplyMoney;
    @BindView(R.id.driver_real_money_et)
    EditText realMoney;
    @BindView(R.id.remain)
    TextView remain;

    //手续费
    double rate = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        //获取手续费
        getPresenter().requestWithDraw();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sky.shop.account");
        filter.setPriority(Integer.MAX_VALUE);
        getActivity().registerReceiver(myReceiver, filter);
    }

    @Override
    protected void initViewsAndEvents() {
        applyMoneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                realMoney.setText(AppUtils.parseDouble(Double.parseDouble(s.toString())
                        - Double.parseDouble(AppUtils.parseDouble(Double.valueOf(s.toString())*(rate*0.01)))) + "");
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_account_apply;
    }

    @Override
    protected void onDestoryFragment() {
        getActivity().unregisterReceiver(myReceiver);
    }

    @Override
    protected AccountContract.IApplyAccountPresenter presenter() {
        return new ApplyAccountFragmentPresenter(context, this);
    }

    @OnClick(R.id.app_confirm_account_tv)
    void confirm(){
        if (TextUtils.isEmpty(applyMoneyEt.getText().toString().trim())){
            T.showShort(context, "请输入提现金额");
            return;
        }
        if (TextUtils.isEmpty(bank_id_tv.getText().toString().trim())
                || "选择".equals(bank_id_tv.getText().toString().trim())){
            T.showShort(context, "请选择提现账户");
            return;
        }
        if (TextUtils.isEmpty(realMoney.getText().toString().trim())){
            T.showShort(context, "请输入到账金额");
            return;
        }
        if (!applyMoneyEt.getText().toString().trim().equals(confirmApplyMoney.getText().toString().trim())){
            T.showShort(context, "两次提现金额不一样");
            return;
        }
        ApplyAccountIn applyAccountIn = new ApplyAccountIn();
        applyAccountIn.setWithdrawal_money(Double.parseDouble(applyMoneyEt.getText().toString().trim()));
        applyAccountIn.setBank_id(bank_id_tv.getText().toString().trim());
        applyAccountIn.setPoundage_money(Double.valueOf(applyMoneyEt.getText().toString().trim())*(rate*0.01));
        applyAccountIn.setActual_money(Double.parseDouble(realMoney.getText().toString().trim()));
        applyAccountIn.setRemark(remark.getText().toString().trim());
        getPresenter().applyAccount(applyAccountIn);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @OnClick(R.id.chack_code)
    void getchack_code() {
        Intent intent = new Intent(getActivity(), ChoosWithdrawalseActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0:
                if (null != data) {
                    bean = (AccountBean) data.getSerializableExtra("accountbean");
                    if (!TextUtils.isEmpty(bean.getBank_id())) {
                        bank_id_tv.setText(bean.getBank_id());
                    }
                    if (!TextUtils.isEmpty(bean.getBank_name())) {
                        driver_bank_et.setText(bean.getBank_name());
                    }
                    if (!TextUtils.isEmpty(bean.getBank_user_name())) {
                        driver_real_name_et.setText(bean.getBank_user_name());
                    }
                    if (!TextUtils.isEmpty(bean.getBank_no())) {
                        driver_card_et.setText(bean.getBank_no());
                    }
                    break;
                }
        }
    }

    @Override
    public void responseApplyAccount(String msg) {
        T.showShort(context, msg);
        getActivity().finish();
    }

    @Override
    public void responseWithDraw(double rate) {
        this.rate = rate;
        remain.setText(rate + "%");
    }

    /**
     * 接口广播
     */
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.sky.shop.account".equals(intent.getAction())){
                double yue = intent.getDoubleExtra("yue", 0);
                yue_tv.setText(AppUtils.parseDouble(yue/100));
            }
        }
    };

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(getActivity());
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }
}