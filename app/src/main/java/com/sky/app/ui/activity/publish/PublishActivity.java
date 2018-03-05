package com.sky.app.ui.activity.publish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AppKey;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.PublishIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.PublishContentPresenter;
import com.sky.app.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布信息
 */
public class PublishActivity extends BaseViewActivity<PublishContract.IPublishContentPresenter>
        implements PublishContract.IPublishContentView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;
    @BindView(R.id.type)
    RadioGroup type;
    @BindView(R.id.app_title_et)
    EditText ptitle;
    @BindView(R.id.app_content)
    EditText content;

    private String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_publish_content);
    }

    @Override
    protected PublishContract.IPublishContentPresenter presenter() {
        return new PublishContentPresenter(this, this);
    }

    @Override
    protected void init() {
        this.product_id = getIntent().getStringExtra("product_id");

        if (TextUtils.isEmpty(this.product_id)){
            title.setText(R.string.app_publish_info_string);
        }else{
            title.setText(R.string.app_publish_edit_string);

            ProductIn productIn = new ProductIn();
            productIn.setProduct_id(this.product_id);
            getPresenter().requestDetail(productIn);
        }

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
    }

    @Override
    public void showSuccess(String msg) {
        T.showShort(context, msg);
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(AppKey.HomePage.APP_TAB_LABEL, AppKey.HomePage.publish);
        startActivity(i);
        finish();
    }

    @Override
    public void responseDetail(SupplyDetail supplyDetail) {
        switch (supplyDetail.getProduct_type()){
            case 0:
                type.check(R.id.buy);
                break;
            case 1:
                type.check(R.id.supply);
                break;
        }
        ptitle.setText(supplyDetail.getProduct_name());
        content.setText(supplyDetail.getProduct_desc());
    }

    @OnClick(R.id.publish)
    void publish(){
        String product_type = "";
        switch (type.getCheckedRadioButtonId()){
            case R.id.buy:
                product_type = "0";
                break;
            case R.id.supply:
                product_type = "1";
                break;
        }
        if (TextUtils.isEmpty(product_type)){
            T.showShort(context, "请选择类型");
            return;
        }
        if (TextUtils.isEmpty(ptitle.getText().toString())){
            T.showShort(context, "请输入标题");
            return;
        }
        if (TextUtils.isEmpty(content.getText().toString())){
            T.showShort(context, "请输入内容");
            return;
        }
        PublishIn publishIn = new PublishIn();
        publishIn.setProduct_type(product_type);
        publishIn.setProduct_name(ptitle.getText().toString());
        publishIn.setProduct_desc(content.getText().toString());
        publishIn.setProduct_id(this.product_id);
        getPresenter().publish(publishIn);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }
}
