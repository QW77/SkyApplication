package com.sky.app.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.app.R;
import com.sky.app.bean.AskEveal;
import com.sky.app.bean.AskItem;
import com.sky.app.bean.Message;
import com.sky.app.bean.MessageAskTable;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.MessagePresenter;
import com.sky.app.ui.activity.ask.MyAnserActivity;
import com.sky.app.ui.activity.ask.MyAskActivity;
import com.sky.app.ui.adapter.fragmentpager.AskFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 问一问中心
 * Created by Administrator on 2017/2/30.
 */
public class MessageCenterAskFragment extends BaseViewFragment<UserContract.IMessagePresenter>
        implements UserContract.IMessageView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    //    @BindView(R.id.app_title)
//    ImageView title;
    @BindView(R.id.ask_show)
    ImageView askshow;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_ask)
    ViewPager mVp;

    PopupWindow popupWindow;


    AskFragmentAdapter mAdapter;//viewpager适配器
    private List<MessageAskTable.ListBean> mTypeList;
    private String two_dir_id;
    private String user_id;
    private String cacheUid;
    private String askcoment;
    private String two_dir_id1;
    private MessageAskTable.ListBean listBean;
    private String two_dir_name;


    @Override
    protected void init() {
        getPresenter().requestAskTable();
        askshow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ask_show:
                showPopuWindow();
                break;
            case R.id.tv_ask_question:
                startActivity(new Intent(context, MyAskActivity.class));
                break;
            case R.id.tv_ask_answer:
                startActivity(new Intent(context, MyAnserActivity.class));
                break;
        }
    }

    private void showPopuWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_ask_fragment, null);
        TextView tv_question = (TextView) view.findViewById(R.id.tv_ask_question);//我的提问
        TextView tv_answer = (TextView) view.findViewById(R.id.tv_ask_answer);//我的回答

        tv_question.setOnClickListener(this);
        tv_answer.setOnClickListener(this);

        DisplayMetrics dm = getResources().getDisplayMetrics();//屏幕的尺寸
        int width = dm.widthPixels;

        popupWindow = new PopupWindow(getActivity());
        popupWindow.setContentView(view);//popupWindow加载布局
        //设置宽高
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(width / 3);
        popupWindow.setOutsideTouchable(true);//popupWindow外可以点击
        popupWindow.setFocusable(true);//popupWindow获得焦点，否则返回的是Activity
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.app_message_1));
        popupWindow.showAsDropDown(askshow);//在某一个控件下方
    }

    @Override
    protected void initViewsAndEvents() {
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_message_center_ask;
    }

    @Override
    protected void onDestoryFragment() {
    }

    @Override
    protected UserContract.IMessagePresenter presenter() {
        return new MessagePresenter(context, this);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    public void onRefresh() {
    }

    //展示问一问上面的Table
    @Override
    public void showAskTable(MessageAskTable messageAskTable) {
        List<AskListFragment> mAskViewList = new ArrayList<>();
        mTypeList = messageAskTable.getList();
        for (int i = 0; i < mTypeList.size(); i++) {
            two_dir_id = mTypeList.get(i).getTwo_dir_id();
            two_dir_name = mTypeList.get(i).getTwo_dir_name();
            user_id = mTypeList.get(i).getUser_id();
            AskListFragment askView = AskListFragment.getAskListFragment(two_dir_id, user_id);
            mAskViewList.add(askView);
        }
        if (mAdapter == null) {
            mAdapter = new AskFragmentAdapter(getActivity().getSupportFragmentManager(), mTypeList, mAskViewList);
            mVp.setAdapter(mAdapter);//给ViewPager设置适配器
        } else {
            mAdapter.setTypeList(mTypeList);
            mAdapter.setFragmentList(mAskViewList);
            mAdapter.notifyDataSetChanged();
        }
        tabLayout.setupWithViewPager(mVp);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    @Override
    public void showAskUp() {



    }

    @Override
    public void showAskComment() {

    }

    @Override
    public void getRefreshData(List<Message> list) {

    }

    @Override
    public void getRefreshData1(List<AskItem.ListBean> list) {

    }

    @Override
    public void showAskList(List<AskItem.ListBean> list) {

    }



    @Override
    public void getLoadMoreData(List<Message> list) {
    }

    @Override
    public void deleteSuccess(String msg) {
        T.showShort(context, msg);
    }



}