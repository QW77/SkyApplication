package com.sky.app.ui.activity.order;

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
import com.sky.app.ui.adapter.CommentGridviewAdapter;
import com.sky.app.ui.custom.AutoHeightGridView;
import com.sky.app.utils.AppDialogUtils;
import com.sky.app.utils.TakePhotoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评论
 * zlf
 */
public class CommentActivity extends BaseViewActivity<OrderContract.CommentPresenter>
        implements OrderContract.CommentView, UserContract.ICenterView {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.fabiao_tv)
    TextView fabiao_tv;
    @BindView(R.id.driver_login_name_et)
    EditText driver_login_name_et;//评论内容
    @BindView(R.id.order_imageView)
    ImageView order_imageView;//订单图片
    @BindView(R.id.quality_rating_bar)
    RatingBar quality_rating_bar;//质量
    @BindView(R.id.service_rating_bar)
    RatingBar service_rating_bar;//服务
    @BindView(R.id.speed_rating_bar)
    RatingBar speed_rating_bar;//速度
    @BindView(R.id.comment_gridview)
    AutoHeightGridView comment_gridview;

    CommentGridviewAdapter commentGridviewAdapter;

    List<String> imagePath = new ArrayList<String>();
    List<String> imageData = new ArrayList<String>();
    CenterActivityPresenter presenter;

    AddCommentInfo addCommentInfo = new AddCommentInfo();
    String quality_num, service_num, speed_num, pinluntext;
    String image_url, orderId, about_user_id;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        image_url = getIntent().getStringExtra("image");
        orderId = getIntent().getStringExtra("orderId");
        type = getIntent().getIntExtra("type", 0);
        about_user_id = getIntent().getStringExtra("about_user_id");
        if (!TextUtils.isEmpty(image_url)) {
            ImageHelper.getInstance().displayDefinedImage(image_url, order_imageView,
                    R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        }
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
        commentGridviewAdapter = new CommentGridviewAdapter(imagePath, CommentActivity.this);
        comment_gridview.setAdapter(commentGridviewAdapter);
        comment_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    if (ContextCompat.checkSelfPermission(CommentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CommentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                Constants.Permission.CAMERA_PERMISSION);
                    } else {
                        AppDialogUtils.showTakePhotoView(CommentActivity.this);
                    }
                }

            }
        });
        quality_rating_bar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                double d = Double.valueOf(RatingCount);
                int i = (new Double(d)).intValue();
                quality_num = String.valueOf(i);
            }
        });
        service_rating_bar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                double d = Double.valueOf(RatingCount);
                int i = (new Double(d)).intValue();
                service_num = String.valueOf(i);
            }
        });
        speed_rating_bar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                double d = Double.valueOf(RatingCount);
                int i = (new Double(d)).intValue();
                speed_num = String.valueOf(i);
            }
        });
    }

    public boolean chack() {
        pinluntext = driver_login_name_et.getText().toString().trim();
        if (TextUtils.isEmpty(quality_num)) {
            T.showShort(this, "没有选择质量评分");
            return false;
        } else if (TextUtils.isEmpty(service_num)) {
            T.showShort(this, "没有选择服务评分");
            return false;
        } else if (TextUtils.isEmpty(speed_num)) {
            T.showShort(this, "没有选择速度评分");
            return false;
        } else if (TextUtils.isEmpty(pinluntext)) {
            T.showShort(this, "评论内容为空");
            return false;
        }/* else if (imagePath.size() == 0 || imageData.size() == 0) {
            T.showShort(this, "没有选择评论图片");
            return false;
        }*/
        return true;
    }

    @OnClick(R.id.fabiao_tv)
    void getfabiao_tv() {
        if (chack()) {
            if ((0 == type && TextUtils.isEmpty(orderId)) || (1 == type && TextUtils.isEmpty(about_user_id))) {
                T.showShort(context, "订单或商户信息错误");
                return;
            }
            addCommentInfo.setQuality(quality_num);
            addCommentInfo.setService(service_num);
            addCommentInfo.setSpeed(speed_num);
            addCommentInfo.setPics(imageData);
            addCommentInfo.setContent(pinluntext);
            addCommentInfo.setType(type);
            addCommentInfo.setAbout_order_id(orderId);
            addCommentInfo.setAbout_user_id(about_user_id);
            getPresenter().getData(addCommentInfo);
        }
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void Succec() {
        T.showShort(context, "发表评论成功！");
        finish();
    }

    @Override
    public void getAskResult() {

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
    public void refresh(UserBean userBean) {

    }

    @Override
    public void showOnSuccess(String msg) {

    }

    @Override
    public void getImageUrl(String url) {
        imageData.add(url);
    }
}
