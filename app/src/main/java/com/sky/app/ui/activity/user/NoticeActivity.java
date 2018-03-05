package com.sky.app.ui.activity.user;




import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.ui.fragment.MessageCenterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeActivity extends AppCompatActivity {

    @BindView(R.id.app_title)
    TextView appTitle;
    @BindView(R.id.shop_collect)
    Button shopCollect;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.Mine_linear)
    LinearLayout mineLinear;
    private MessageCenterFragment messageCenterFragment;
    private FragmentManager mSupportFragmentManager = getSupportFragmentManager();
    private Fragment currentFragment;

// BaseViewActivity<UserContract.IMessagePresenter>
//        implements UserContract.IMessageView, SwipeRefreshLayout.OnRefreshListener
//    @BindView(R.id.app_title)
//    TextView title;
//    @BindView(R.id.app_swipe)
//    SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.app_message_list)
//    LuRecyclerView mRecyclerView;


//    MessageAdaptor messageAdaptor;
//    List<Message> supplyDetailArrayList = new ArrayList<>();
//    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //使用fragment替换原来的布局
        messageCenterFragment = new MessageCenterFragment();
        showFragment(messageCenterFragment);
    }

    private void showFragment(Fragment fragment) {

        final FragmentTransaction mFt = mSupportFragmentManager.beginTransaction();
        if (fragment == null) {
            return;
        }
        if (currentFragment == null) {
            currentFragment = fragment;
            mFt.add(R.id.Mine_linear, fragment);
            mFt.show(fragment);
            mFt.commit();
        }
        if (currentFragment.getClass().getSimpleName().equals(fragment.getClass().getSimpleName())) {
            return;
        } else {
            if (!fragment.isAdded()) {
                mFt.add(R.id.Mine_linear, fragment);
                mFt.show(fragment);
            } else {
                mFt.show(fragment);
            }
            mFt.hide(currentFragment);
            mFt.commit();
            currentFragment = fragment;

        }
    }





}
