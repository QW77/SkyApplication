package com.sky.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AskItem;
import com.sky.app.bean.Message;
import com.sky.app.bean.MessageAskTable;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.presenter.MessagePresenter;
import com.sky.app.ui.activity.ask.AppAskComments;
import com.sky.app.ui.activity.ask.AskComments;
import com.sky.app.ui.activity.ask.AskSecondPic;
import com.sky.app.ui.activity.user.Main2Activity;
import com.sky.app.ui.adapter.recycleview.AskListRcAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xmy on 2018/1/2.
 */

public class AskListFragment extends BaseViewFragment<UserContract.IMessagePresenter>
        implements UserContract.IMessageView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.rc)
    RecyclerView mRecyclerView;//问答item列表
    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新
    //    @BindView(R.id.ask_edit)
//    EditText editText;
//    @BindView(R.id.ask_send)
//    ImageView send;
    @BindView(R.id.ask)
    TextView ask;

    AskListRcAdapter adapter;
    private String two_dir_id;
    private String id;

    private String eval_id;
    List<AskItem.ListBean> list = new ArrayList<>();

    private String eval_url;
    private String eval_comments;
    private String user_id;
    private String askcoment;
    private String cacheUid;

    //    List<String> imagePath = new ArrayList<String>();
    List<String> imageData = new ArrayList<String>();

    private String file_url;
    private String url;

    public static AskListFragment getAskListFragment(String Two_dir_id, String id) {
        AskListFragment mAskListFragment = new AskListFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("Two_dir_id", Two_dir_id);
        mBundle.putString("id", id);
        mAskListFragment.setArguments(mBundle);
        return mAskListFragment;
    }

    @Override
    protected void init() {
        two_dir_id = getArguments().getString("Two_dir_id");
        eval_id = two_dir_id;
        id = getArguments().getString("id");
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        getPresenter().loadData(-1, eval_id);
        if (adapter == null) {
            adapter = new AskListRcAdapter(context);
        }
    }

    /**
     * RecyclerView的条目点击事件和评论和点赞点击事件
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AskListRcAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int pos) {
                id = list.get(pos).getId();
                user_id = list.get(pos).getUser_id();//用户名
                eval_comments = list.get(pos).getEval_comments();//内容
                eval_url = list.get(pos).getPic_url();//用户图像
                Intent intent = new Intent(context, AskComments.class);
                intent.putExtra("id", id);//item的ID
                intent.putExtra("eval_id", eval_id);//two_dir_id
                intent.putExtra("user_id", user_id);//用户名
                intent.putExtra("eval_comments", eval_comments);//内容
                intent.putExtra("eval_url", eval_url);
                startActivity(intent);
            }
        });

        adapter.setOnClickListener(new AskListRcAdapter.OnClickListener() {


            @Override
            public void onClick(View itemView, int pos) {
                switch (itemView.getId()) {
                    case R.id.ask_tv_comment:
                        id = list.get(pos).getId();
                        user_id = list.get(pos).getUser_id();//用户名
                        eval_comments = list.get(pos).getEval_comments();
                        eval_url = list.get(pos).getPic_url();//用户图像
                        cacheUid = UserBean.getInstance().getCacheUid();
                        Intent k = new Intent(context, AppAskComments.class);
                        k.putExtra("id", id);
                        k.putExtra("eval_id", eval_id);
                        k.putExtra("user_id", user_id);
                        k.putExtra("eval_comments", eval_comments);
                        k.putExtra("eval_url", eval_url);
                        k.putExtra("cacheUid", cacheUid);
                        startActivity(k);
                        break;
                    case R.id.ask_tv_praise:
                        id = list.get(pos).getId();
                        cacheUid = UserBean.getInstance().getCacheUid();
                        getPresenter().requestAskUp(id);
                        break;
                    case R.id.ask_pic:
                        List<AskItem.FileListBean> fileList = list.get(pos).getFileList();
                        ArrayList<String> strings = new ArrayList<String>();
                        String urls = "";
                        for (int i = 0; i < fileList.size(); i++) {
                            file_url = list.get(pos).getFileList().get(i).getFile_url();
                            urls = urls + "," + file_url;
                            strings.add(file_url);
                        }
                        if (!urls.equals("")) {
                            urls = urls.substring(1, urls.length());
                        }
                        Intent intent = new Intent(context, Main2Activity.class);
                        intent.putStringArrayListExtra("strings",strings);
                        startActivity(intent);
                }
            }
        });
//        adapter.notifyDataSetChanged();
    }


    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {


        return R.layout.fragment_ask_list;
    }

    @Override
    protected void onDestoryFragment() {
    }

    @Override
    protected UserContract.IMessagePresenter presenter() {
        return new MessagePresenter(context, this);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getPresenter().loadData(-1, eval_id);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void showAskTable(MessageAskTable messageAskTable) {

    }


    //返回获取的点赞数
    @Override
    public void showAskUp() {


    }

    //返回获取的评论数
    @Override
    public void showAskComment() {

    }


    @Override
    public void getRefreshData(List<Message> list) {

    }

    /**
     * 这是recycle view的适配器
     */
    @Override
    public void getRefreshData1(List<AskItem.ListBean> list) {
        this.list = list;
        adapter.setList(list);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void showAskList(List<AskItem.ListBean> list) {
//        list = askItem.getList();
//        this.list = list;
//        adapter = new AskListRcAdapter(context, list);
    }


    @Override
    public void getLoadMoreData(List<Message> list) {
    }

    @Override
    public void deleteSuccess(String msg) {

    }

    //    @OnClick(R.id.ask_send)
//    void send() {
//
//       AskOne askOne = new AskOne();
//        askOne.setPics(imageData);
//        askcoment = editText.getText().toString().trim();
//        getPresenter().requestCenterAsk(eval_id, 5, askcoment, askOne);
//        editText.setText("");//清空输入框中的内容
//    }
//

    /**
     * 点击提问跳转到发表评论详情的界面
     */
    @OnClick(R.id.ask)
    void setAsk() {
        Intent r = new Intent(context, AskSecondPic.class);
        r.putExtra("eval_id", eval_id);
        startActivity(r);
    }
}
