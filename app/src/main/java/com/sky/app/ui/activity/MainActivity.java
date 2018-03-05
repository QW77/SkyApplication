package com.sky.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.mobileim.IYWPushListener;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.sky.app.R;
import com.sky.app.bean.AppKey;
import com.sky.app.bean.UserBean;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.TabRaidoButton;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.T;
import com.sky.app.test.TestActivity;
import com.sky.app.ui.activity.openIM.OpenIMLoginActivity;
import com.sky.app.ui.activity.user.LoginActivity;
import com.sky.app.ui.fragment.HomePageFragment;
import com.sky.app.ui.fragment.MessageCenterAskFragment;
import com.sky.app.ui.fragment.MessageCenterFragment;
import com.sky.app.ui.fragment.MineFragment;
import com.sky.app.ui.fragment.OpenIMMyFragment;
import com.sky.app.ui.fragment.PublishFragment;
import com.sky.app.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页
 */
public class MainActivity extends BaseViewActivity {

    @BindView(R.id.app_home_page)
    TabRaidoButton homePage;
    @BindView(R.id.app_publish)
    TabRaidoButton publish;
    @BindView(R.id.app_message)
    TabRaidoButton message;
    @BindView(R.id.app_mine)
    TabRaidoButton mine;
    @BindView(R.id.app_home_openIM)
    TabRaidoButton openIM;
//    @BindView(R.id.app_test)
//    TabRaidoButton test;
    @BindView(R.id.driver_content_fl)
    FrameLayout driverContentFl;

    @BindView(R.id.tv_msgs)
    TextView tv_msgs;

    private RxFragment[] pages;

    private int lastIndexOfPage = -1;
    private int currentPageIndex = 0;
    private long exitTime;
    private String target;

    int flag = -1;
    private Fragment conversationFragment;
    private YWIMKit mIMKit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);
        ButterKnife.bind(this);
        initIMKit();
    }


    private IYWPushListener msgPushListener;
    private IYWConversationService conversationService;


    @Override
    protected IBasePresenter presenter() {
        return null;
    }


    @Override
    protected void init() {
        flag = getIntent().getIntExtra(AppKey.HomePage.APP_TAB_LABEL, AppKey.HomePage.index);
        pages = new RxFragment[6];
        pages[AppKey.HomePage.index] = getIndexFragment(flag);
        pages[AppKey.HomePage.publish] = new PublishFragment();
        pages[AppKey.HomePage.message] = new MessageCenterAskFragment();
        pages[AppKey.HomePage.mime] = new MineFragment();

    }

    /**
     * 初始化IMKit
     */
    private void initIMKit() {
        SharedPreferences sp = getSharedPreferences(AppKey.XmlName.APP_CACHE_USER_INFO, MODE_PRIVATE);
        String user_id = sp.getString("uid", null);
        String password = sp.getString("password", null);
        String APP_KEY = "24629506";
        if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(password) || TextUtils.isEmpty(APP_KEY)) {
            LogUtil.e(getClass().getName(), "云旺登陆参数为空");
            startActivity(new Intent(context,LoginActivity.class));
            return;
        }
        mIMKit = YWAPI.getIMKitInstance(user_id, APP_KEY);
        conversationFragment = getConversationFragment();
        getUnreadMessageCount();
    }

    @Override
    protected void onDestoryActivity() {
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


    /**
     * 获取
     *
     * @param flag 是否需要更新
     * @return 首页
     */
    private HomePageFragment getIndexFragment(int flag) {
        HomePageFragment indexFragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppKey.HomePage.APP_TAB_LABEL, flag);
        indexFragment.setArguments(bundle);
        return indexFragment;
    }

    @Override
    public void initViewsAndEvents() {
        switch (flag) {
            case AppKey.HomePage.index:
                homePage.performClick();
                break;
            case AppKey.HomePage.publish:
                publish.performClick();
                break;
            case AppKey.HomePage.message:
                message.performClick();
                break;
            case AppKey.HomePage.mime:
                mine.performClick();
                break;
//            case AppKey.HomePage.openIMText:
//                test.performClick();
//                break;
            //修改第二处
//            case AppKey.HomePage.openIM:
//                openIM.performClick();
//                break;
        }
    }

    @OnClick({R.id.app_home_page, R.id.app_publish, R.id.app_message, R.id.app_mine, R.id.app_home_openIM})
    public void clickTab(TabRaidoButton btn) {
        switch (btn.getId()) {
            case R.id.app_home_page:
                displayPage(AppKey.HomePage.index);
                break;
            case R.id.app_publish:
                displayPage(AppKey.HomePage.publish);
                break;
            case R.id.app_message:
                //检查用户是否登录
                if (mIMKit != null) {
                    displayPage(AppKey.HomePage.message);
                } else {
                    initIMKit();
                }
                break;
            case R.id.app_mine:
                displayPage(AppKey.HomePage.mime);
                break;
            case R.id.app_home_openIM:
                //修改第三处
//                startActivity(new Intent(context, OpenIMLoginActivity.class));
                displayPage(AppKey.HomePage.openIM);
                break;
            //测试界面
//            case R.id.app_test:
//                startActivity(new Intent(context, TestActivity.class));
//                break;
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
                homePage.setCheckedView(true);
                publish.setCheckedView(false);
                message.setCheckedView(false);
                openIM.setCheckedView(false);
                mine.setCheckedView(false);
                break;
            case 1:
                homePage.setCheckedView(false);
                publish.setCheckedView(true);
                message.setCheckedView(false);
                openIM.setCheckedView(false);
                mine.setCheckedView(false);
//                test.setCheckedView(false);
                break;
            case 2:
                homePage.setCheckedView(false);
                publish.setCheckedView(false);
                message.setCheckedView(true);
                openIM.setCheckedView(false);
                mine.setCheckedView(false);
//                test.setCheckedView(false);
                break;
            case 3:
                homePage.setCheckedView(false);
                publish.setCheckedView(false);
                message.setCheckedView(false);
                mine.setCheckedView(true);
                openIM.setCheckedView(false);
//                test.setCheckedView(false);
                break;
            case 4:
                homePage.setCheckedView(false);
                publish.setCheckedView(false);
                message.setCheckedView(false);
                mine.setCheckedView(false);
                openIM.setCheckedView(true);
//                test.setCheckedView(false);
                break;
            case 5:
                homePage.setCheckedView(false);
                publish.setCheckedView(false);
                message.setCheckedView(false);
                mine.setCheckedView(false);
                openIM.setCheckedView(false);
//                test.setCheckedView(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            T.showShort(context, "再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
//            this.moveTaskToBack(true);
            BaseAppManager.getInstance().AppExit(context);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); //这一句必须的，否则Intent无法获得最新的数据
    }


    @Override
    protected void onResume() {
        super.onResume();
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
}
