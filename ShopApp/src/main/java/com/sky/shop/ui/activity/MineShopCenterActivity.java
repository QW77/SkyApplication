package com.sky.shop.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.IYWPushListener;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.alibaba.mobileim.utility.LogUtil;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.TabRaidoButton;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.AppKey;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.MineShopCenterPresenter;
import com.sky.shop.ui.activity.OpenIMShop.OpenIMLoginActivity;
import com.sky.shop.ui.activity.shop.SafeCenterActivity;
import com.sky.shop.ui.activity.shop.ShopDataActivity;
import com.sky.shop.ui.activity.user.AccountActivity;
import com.sky.shop.ui.activity.user.LoginActivity;
import com.sky.shop.ui.fragment.FloatWebFragment;
import com.sky.shop.ui.fragment.MessageCenterFragment;
import com.sky.shop.ui.fragment.ShopDecorateFragment;
import com.sky.shop.utils.TakePhotoUtils;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的卖家中心商铺
 */
public class MineShopCenterActivity extends BaseViewActivity<ShopContract.IMineShopCenterPresenter>
        implements ShopContract.IMineShopCenterView {

    @BindView(R.id.app_message)
    TabRaidoButton message;
    @BindView(R.id.app_order)
    TabRaidoButton order;
    @BindView(R.id.app_decorate)
    TabRaidoButton decorate;
    @BindView(R.id.app_message_openIM)
    TabRaidoButton openIM;
//    @BindView(R.id.app_message_openIM)
//    TabRaidoButton openIM;


    @BindView(R.id.app_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.app_picture)
    ImageView appPicture;
    @BindView(R.id.app_sider_title)
    TextView siderTitle;
    @BindView(R.id.tv_msgs)
    TextView tv_msgs;

    private RxFragment[] pages;

    private int lastIndexOfPage = -1;
    private int currentPageIndex = 0;
    int flag = -1;
    private long exitTime;
    private YWIMKit mIMKit;
    private Fragment conversationFragment;
    private IYWPushListener msgPushListener;
    private IYWConversationService conversationService;
//    ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIMKit();
        setContentView(R.layout.app_seller_shop_main);
    }

    /**
     * 注册监听，获取未读消息数量
     */
    public void getUnreadMessageCount() {
        if (msgPushListener == null) {
            msgPushListener = new IYWPushListener() {
                @Override
                public void onPushMessage(IYWContact iywContact, YWMessage ywMessage) {
                    //当有新消息到达时，会触发此方法的回调
                    //第一个参数是新消息发送者信息
                    //第二个参数是消息
                    //用户在这里可以做自己的消息提醒
                    if (tv_msgs != null) {
                        int count = mIMKit.getUnreadCount();
                        if (count == 0) {
                            tv_msgs.setText("0");
                            tv_msgs.setVisibility(View.GONE);
                        } else if (count < 100) {
                            tv_msgs.setText(mIMKit.getUnreadCount() + "");
                            tv_msgs.setVisibility(View.VISIBLE);
                        } else {
                            tv_msgs.setText("99+");
                            tv_msgs.setVisibility(View.VISIBLE);
                        }
                        setBadgeNumber(context,mIMKit.getUnreadCount());

                    }
                }

                @Override
                public void onPushMessage(YWTribe ywTribe, YWMessage ywMessage) {

                }
            };
        }
        if (conversationService == null) {
            conversationService = mIMKit.getConversationService();
        }
//        //如果之前add过，请清除
//        conversationService.removePushListener(msgPushListener);
        //增加新消息到达的通知
        conversationService.addPushListener(msgPushListener);
    }

    @Override
    protected ShopContract.IMineShopCenterPresenter presenter() {
        return new MineShopCenterPresenter(context, this);
    }

    @Override
    protected void init() {
        flag = getIntent().getIntExtra(AppKey.HomePage.APP_TAB_LABEL, AppKey.HomePage.openIM);
        pages = new RxFragment[4];

        pages[AppKey.HomePage.message_center] = new MessageCenterFragment();
        FloatWebFragment floatWebFragment = new FloatWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", "http://api.app.51craftsman.com/product_order/to_merchant_orders?user_id=" +
                UserBean.getInstance().getCacheUid());
        floatWebFragment.setArguments(bundle);

        pages[AppKey.HomePage.order_manage] = floatWebFragment;
        pages[AppKey.HomePage.shop_decorate] = new ShopDecorateFragment();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sky.shop.left");
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(myReceiver, filter);
    }

    /**
     * 初始化IMKit
     */
    private void initIMKit() {
        SharedPreferences sp = getSharedPreferences(AppKey.XmlName.SHOP_CACHE_USER_INFO, MODE_PRIVATE);
        String user_id = sp.getString("uid", null);
        String password = sp.getString("password", null);
        String APP_KEY = "24642621";
        if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(password) || TextUtils.isEmpty(APP_KEY)) {
            Log.e(getClass().getName(), "云旺登陆参数为空");
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        mIMKit = YWAPI.getIMKitInstance(user_id, APP_KEY);
        conversationFragment = getConversationFragment();
        getUnreadMessageCount();
    }

    @Override
    protected void onDestoryActivity() {
//        unregisterReceiver(myReceiver);
        //取消监听
        conversationService.removePushListener(msgPushListener);
    }

    /**
     * 获取最近联系人列表Fragment
     *
     * @return
     */
    private Fragment getConversationFragment() {
//        Bundle bundle = new Bundle();
//        bundle.putInt(AppKey.HomePage.APP_TAB_LABEL, open);
//        conversationFragment.setArguments(bundle);
//        return conversationFragment;
        Fragment conversationFragment = mIMKit.getConversationFragment();
        return conversationFragment;
    }


    @Override
    protected void initViewsAndEvents() {
        switch (flag) {

            case AppKey.HomePage.openIM:
                openIM.performClick();
                break;
            case AppKey.HomePage.message_center:
                message.performClick();
                break;
            case AppKey.HomePage.order_manage:
                order.performClick();
                break;
            case AppKey.HomePage.shop_decorate:
                decorate.performClick();
                break;
        }
    }


    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }


    @OnClick({R.id.app_message, R.id.app_message_openIM, R.id.app_order, R.id.app_decorate})
    public void clickTab(TabRaidoButton btn) {
        switch (btn.getId()) {
            case R.id.app_message:
                displayPage(AppKey.HomePage.message_center);
                break;
            case R.id.app_message_openIM:
                displayPage(AppKey.HomePage.openIM);
//                startActivity(new Intent(this, OpenIMLoginActivity.class));
                break;
            case R.id.app_order:
                displayPage(AppKey.HomePage.order_manage);
                break;
            case R.id.app_decorate:
                displayPage(AppKey.HomePage.shop_decorate);
                break;

        }
    }

    /**
     * 显示Tab页
     *
     * @param pageIndex
     */
    public void displayPage(int pageIndex) {
        if (pageIndex == lastIndexOfPage) return;

        currentPageIndex = pageIndex;

        switchTab(pageIndex);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        boolean isAdded = false;
        if (pageIndex == AppKey.HomePage.openIM) {
            isAdded = conversationFragment.isAdded();
        } else {
            isAdded = pages[pageIndex].isAdded();
        }
        if (isAdded) {
            //显示fragment
            if (pageIndex == AppKey.HomePage.openIM) {
                ft.show(conversationFragment);
            } else {
                ft.show(pages[pageIndex]);
            }
        } else {
            //添加fragment
            if (pageIndex == AppKey.HomePage.openIM) {
                ft.add(R.id.driver_content_fl, conversationFragment, String.valueOf(pageIndex));
            } else {
                ft.add(R.id.driver_content_fl, pages[pageIndex], String.valueOf(pageIndex));
            }
        }

        if (lastIndexOfPage >= 0) {
            //隐藏fragmnt
            if (lastIndexOfPage == AppKey.HomePage.openIM) {
                ft.hide(conversationFragment);
            } else {
                ft.hide(pages[lastIndexOfPage]);
            }
        }
        ft.commitAllowingStateLoss();
        lastIndexOfPage = pageIndex;
    }

    /**
     * 切换标签选择
     *
     * @param position 位置
     */
    private void switchTab(int position) {
        switch (position) {
            case 0:
                message.setCheckedView(true);
                openIM.setCheckedView(false);
                order.setCheckedView(false);
                decorate.setCheckedView(false);
                break;
            case 1:
                message.setCheckedView(false);
                openIM.setCheckedView(true);
                order.setCheckedView(false);
                decorate.setCheckedView(false);
                break;
            case 2:
                message.setCheckedView(false);
                openIM.setCheckedView(false);
                order.setCheckedView(true);
                decorate.setCheckedView(false);
                break;
            case 3:
                message.setCheckedView(false);
                openIM.setCheckedView(false);
                order.setCheckedView(false);
                decorate.setCheckedView(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.showShort(context, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
//            this.moveTaskToBack(true);
                BaseAppManager.getInstance().AppExit(context);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode,
                resultCode, data, true);
        L.msg("图片上传===>" + msg);
        Intent a = new Intent();
        a.setAction("com.sky.shop.image");
        a.putExtra("image", msg);
        sendBroadcast(a);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /**
     * 关闭drawer
     */
    private boolean closeDrawer() {
        if (null != drawerLayout && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }


    @OnClick(R.id.app_shop_center)
    void clickShopCenter() {
        startActivity(new Intent(context, ShopDataActivity.class));
    }

    @OnClick(R.id.app_account_center)
    void clickAccountCenter() {
        closeDrawer();
        startActivity(new Intent(context, AccountActivity.class));
    }

    @OnClick(R.id.app_browser_record)
    void clickBrowserRecord() {
        closeDrawer();
        Intent i = new Intent(context, RecordWebActivity.class);
        UserBean.getInstance().getCacheUid();
        i.putExtra("url", "http://api.app.51craftsman.com/h5_shop/un/browse_record?user_id=" +
                UserBean.getInstance().getCacheUid());
        startActivity(i);
    }

    @OnClick(R.id.app_safe_center)
    void clickSafeCenter() {
        closeDrawer();
        startActivity(new Intent(context, SafeCenterActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().queryUserInfo();
        if (!UserBean.getInstance().checkUserLogin()) {
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        if (mIMKit == null) {
            initIMKit();
        } else {
            int count = mIMKit.getUnreadCount();
            if (count == 0) {
                tv_msgs.setText("0");
                tv_msgs.setVisibility(View.GONE);
            } else if (count < 100) {
                tv_msgs.setText(mIMKit.getUnreadCount() + "");
                tv_msgs.setVisibility(View.VISIBLE);
            } else {
                tv_msgs.setText("99+");
                tv_msgs.setVisibility(View.VISIBLE);
            }
            setBadgeNumber(context,mIMKit.getUnreadCount());
        }

    }
    public static void setBadgeNumber(Context context, int number) {
        try {
            if (number < 0) number = 0;
            Bundle bundle = new Bundle();
            bundle.putString("package", context.getPackageName());
            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
            bundle.putString("class", launchClassName);
            bundle.putInt("badgenumber", number);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void responseUserInfo(UserBean userBean) {
        //初始化数据
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(),
                appPicture, R.mipmap.app_default_photo, R.mipmap.app_default_icon);
        siderTitle.setText(UserBean.getInstance().getUserCache().getNick_name());
    }

    /**
     * 接口广播
     */
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.sky.shop.left".equals(intent.getAction())) {
                L.msg("比纳篮");
                if (!closeDrawer()) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        }

    };

}
