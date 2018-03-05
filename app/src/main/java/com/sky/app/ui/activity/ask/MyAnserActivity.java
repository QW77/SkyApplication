package com.sky.app.ui.activity.ask;

import android.support.v7.app.AppCompatActivity;
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

public class MyAnserActivity extends BaseViewActivity<ShopContract.IAskAllComentsPresenter>
        implements ShopContract.IAskAllCommentView {

    @BindView(R.id.anser_listview)
    ListView anserListview;
    @BindView(R.id.publish_title_tou)
    TextView myanserhead;
    @BindView(R.id.publish_normal_toolbar)
    Toolbar myansertooblar;

    private String cacheUid;
    private String user_id;
    private MyAnserAdapter myAskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_anser);
        ButterKnife.bind(this);
    }

    @Override
    protected ShopContract.IAskAllComentsPresenter presenter() {
        return new AskAllCommentPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        myansertooblar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void init() {
        myanserhead.setText(R.string.my_anser);
        myansertooblar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        cacheUid = UserBean.getInstance().getCacheUid();
        user_id = cacheUid;
        getPresenter().requestMyAnser(user_id,1);
    }


    @Override
    protected void onDestoryActivity() {

    }


    @Override
    public void showMyAsk(List<MyAsk.ListBean> list) {

    }
    //展示问一问中的我的回答的数据
    @Override
    public void showMyAnser(List<MyAnser.ListBean> list) {
        myAskAdapter = new MyAnserAdapter(list, context);
        anserListview.setAdapter(myAskAdapter);
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
}

