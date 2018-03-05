package com.sky.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.CircleImageView;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.MineActivityPresenter;
import com.sky.app.ui.activity.address.MyAddressActivity;
import com.sky.app.ui.activity.order.MyOrderActivity;
import com.sky.app.ui.activity.order.MyShoppingActivity;
import com.sky.app.ui.activity.publish.MyPublishActivity;
import com.sky.app.ui.activity.user.CenterActivity;
import com.sky.app.ui.activity.user.MyCollectActivity;
import com.sky.app.ui.activity.user.NoticeActivity;
import com.sky.app.ui.activity.user.SettingsActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 * Created by Administrator on 2017/2/30.
 */
public class MineFragment extends BaseViewFragment<UserContract.IMinePresenter> implements UserContract.IMineView {

    private boolean isLogin;//登录显示

    @BindView(R.id.driver_person_icon)
    CircleImageView circleImageView;//头像
    @BindView(R.id.app_person_name)
    TextView personName;//用户名
    @BindView(R.id.app_icon)
    LinearLayout icon;
    @BindView(R.id.app_shopping)
    TextView shopping;
    @BindView(R.id.app_address)
    TextView address;
    @BindView(R.id.app_collect)
    TextView collect;
    @BindView(R.id.app_order)
    TextView order;
    @BindView(R.id.app_publish)
    TextView publish;
    @BindView(R.id.app_customer)
    TextView customer;
    @BindView(R.id.app_settings)
    TextView settings;
    @BindView(R.id.app_mine_notice)
    LinearLayout minenotice;
    @BindView(R.id.iv_session_point)
    ImageView sessionpoint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {

    }


    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_mine;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected UserContract.IMinePresenter presenter() {
        return new MineActivityPresenter(context, this);
    }

    @Override
    public void showUserInfo() {
        UserBean userBean = UserBean.getInstance();
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(), circleImageView,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        personName.setText(userBean.getNick_name());
    }

    @Override
    public void showMobile(String msg) {
        if (TextUtils.isEmpty(msg)) {
            T.showShort(context, "暂时没有客服联系方式");
            return;
        }
        AppUtils.callPhone(context, msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        isLogin = UserBean.getInstance().checkUserLogin();
        if (isLogin) {
            getPresenter().refreshUserInfo();
        } else {
            circleImageView.setImageBitmap(null);
            personName.setText("");
        }
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @OnClick(R.id.app_icon)
    void clickIcon() {
        getActivity().startActivity(new Intent(context, CenterActivity.class));
    }

    @OnClick(R.id.app_shopping)
    void clickShopping() {
        //跳转购物车
        getActivity().startActivity(new Intent(context, MyShoppingActivity.class));
    }

    @OnClick(R.id.app_address)
    void clickAddr() {
        //跳转收货地址
        getActivity().startActivity(new Intent(context, MyAddressActivity.class));
    }

    @OnClick(R.id.app_collect)
    void clickCollect() {
        //跳转我的收藏
        getActivity().startActivity(new Intent(context, MyCollectActivity.class));
    }

    @OnClick(R.id.app_order)
    void clickOrder() {
        //跳转我的订单
        getActivity().startActivity(new Intent(context, MyOrderActivity.class));
    }

    @OnClick(R.id.app_publish)
    void clickPublish() {
        //跳转我的发布
        getActivity().startActivity(new Intent(context, MyPublishActivity.class));
    }

    @OnClick(R.id.app_customer)
    void clickCustomer() {
        getPresenter().requestMobile();
    }

    @OnClick(R.id.app_settings)
    void clickSettings() {
        //跳转设置
        getActivity().startActivity(new Intent(context, SettingsActivity.class));
    }

    @OnClick(R.id.app_mine_notice)
    void clickNotice() {
        //跳转到通知
        getActivity().startActivity(new Intent(context, NoticeActivity.class));


    }

    @OnClick(R.id.app_shopper)
    void clickShopper() {
        AppUtils.skipBrowser(context, "http://a.app.qq.com/o/simple.jsp?pkgname=com.sky.shop");
    }

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