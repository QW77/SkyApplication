package com.sky.app.ui.activity.ask;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.presenter.askpresenter.AskAllCommentPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAskActivity extends BaseViewActivity<ShopContract.IAskAllComentsPresenter>
        implements ShopContract.IAskAllCommentView {


    @BindView(R.id.ask_listview)
    ListView askListview;
    @BindView(R.id.publish_title_tou)
    TextView myaskhead;
    @BindView(R.id.publish_normal_toolbar)
    Toolbar myasktooblar;
    private String cacheUid;
    private String user_id;
    private MyAskAdapter myAskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ask);
        ButterKnife.bind(this);
    }

    @Override
    protected ShopContract.IAskAllComentsPresenter presenter() {
        return new AskAllCommentPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        myasktooblar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void init() {
        myaskhead.setText(R.string.my_ask_head);
        myasktooblar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        cacheUid = UserBean.getInstance().getCacheUid();
        user_id = cacheUid;
        getPresenter().requestMyAsk(user_id,0);
    }


    @Override
    protected void onDestoryActivity() {

    }


    @Override
    public void showCollectView(String msg) {

    }

    @Override
    public void getRefreshData1(List<AskSecondComent.ListBean> list, int num) {

    }

    @Override
    public void getLoadMoreData1(List<AskSecondComent.ListBean> list) {

    }

    @Override
    public void showAskComent(List<AskSecondComent.ListBean> list) {

    }

    //展示问一问中的我的提问的数据
    @Override
    public void showMyAsk(List<MyAsk.ListBean> list) {
        myAskAdapter = new MyAskAdapter(list, context);
        askListview.setAdapter(myAskAdapter);

    }

    @Override
    public void showMyAnser(List<MyAnser.ListBean> list) {}


}

