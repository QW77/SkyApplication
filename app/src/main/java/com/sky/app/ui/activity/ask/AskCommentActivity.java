package com.sky.app.ui.activity.ask;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AddCommentInfo;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.RatingBar;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.CenterActivityPresenter;
import com.sky.app.presenter.CommentPresenter;
import com.sky.app.ui.activity.order.CommentActivity;
import com.sky.app.ui.adapter.CommentGridviewAdapter;
import com.sky.app.ui.custom.AutoHeightGridView;
import com.sky.app.utils.AppDialogUtils;
import com.sky.app.utils.TakePhotoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.Query;

/**
 * 问一问回复评论
 */
public class AskCommentActivity extends BaseViewActivity<OrderContract.CommentPresenter>
        implements OrderContract.CommentView, UserContract.ICenterView {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.fabiao_tv)
    TextView fabiao_tv;
    @BindView(R.id.ask_login_name_et)
    EditText driver_login_name_et;//评论内容

    @BindView(R.id.comment_gridview)
    AutoHeightGridView comment_gridview;

    CommentGridviewAdapter commentGridviewAdapter;
    List<String> imagePath = new ArrayList<String>();
    List<String> imageData = new ArrayList<String>();

    CenterActivityPresenter presenter;
    AskAddCommentInfo addCommentInfo = new AskAddCommentInfo();

    String type;
    private String id;
    private String eval_id;
    private String coment;
    private String cacheUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentask);
        id = getIntent().getStringExtra("id");
        eval_id = getIntent().getStringExtra("eval_id");

    }

    @Override
    protected OrderContract.CommentPresenter presenter() {
        presenter = new CenterActivityPresenter(this, this);
        return new CommentPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText("发表评论");
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        commentGridviewAdapter = new CommentGridviewAdapter(imagePath, AskCommentActivity.this);
        comment_gridview.setAdapter(commentGridviewAdapter);
        comment_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    if (ContextCompat.checkSelfPermission(AskCommentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AskCommentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                Constants.Permission.CAMERA_PERMISSION);
                    } else {
                        AppDialogUtils.showTakePhotoView(AskCommentActivity.this);
                    }
                }

            }
        });
    }

    @OnClick(R.id.fabiao_tv)
    void getfabiao_tv() {
        coment = driver_login_name_et.getText().toString().trim();
        cacheUid = UserBean.getInstance().getCacheUid();
        addCommentInfo.setPics(imageData);
        getPresenter().getAskData(cacheUid, id, "0", eval_id, 5, coment, addCommentInfo);

    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void Succec() {
    }

    @Override
    public void getAskResult() {
        T.showShort(context, "发表评论成功！");
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode, resultCode, data, true);
        if (!TextUtils.isEmpty(msg)) {
            imagePath.add(msg);
            presenter.uploadFile(msg);
            commentGridviewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refresh(UserBean userBean) {}

    @Override
    public void showOnSuccess(String msg) {}

    @Override
    public void getImageUrl(String url) {
        imageData.add(url);
    }
}
