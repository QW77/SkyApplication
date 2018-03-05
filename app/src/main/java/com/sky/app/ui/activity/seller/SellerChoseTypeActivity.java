package com.sky.app.ui.activity.seller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.SellMessageComplete;
import com.sky.app.bean.UserBean;
import com.sky.app.library.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by hongbang on 2017/5/11.
 * 选择商户类型,是个人还是公司
 */

public class SellerChoseTypeActivity extends AppCompatActivity {

    @BindView(R.id.app_title)
    TextView appTitle;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;

    @BindView(R.id.sellChoseTypeCompany)
    FrameLayout sellChoseTypeCompany;
    @BindView(R.id.sellChoseTypePerson)
    FrameLayout sellChoseTypePerson;
    @BindView(R.id.sellChoseTypeNextButtom)
    TextView sellChoseTypeNextButtom;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.person)
    TextView person;
    private String companyOrPerspn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_choose_type);
        ButterKnife.bind(this);
        appTitle.setText("选择经营类型");
        normalToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        normalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.sellChoseTypeCompany, R.id.sellChoseTypePerson, R.id.sellChoseTypeNextButtom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sellChoseTypeCompany:
                company.setBackgroundColor(Color.parseColor("#11000000"));
                person.setBackgroundColor(Color.parseColor("#55000000"));
                companyOrPerspn="company";
                break;
            case R.id.sellChoseTypePerson:
                person.setBackgroundColor(Color.parseColor("#11000000"));
                company.setBackgroundColor(Color.parseColor("#55000000"));
                companyOrPerspn="person";
                break;
            case R.id.sellChoseTypeNextButtom:
                if(TextUtils.isEmpty(companyOrPerspn)){
                    T.showShort(this,"请选择商户或者个人");
                    return;
                }else{
                    Intent intent = new Intent(this, SellerFirstCategoryActivity.class);
                    SellMessageComplete sellMessageComplete = new SellMessageComplete();
                    sellMessageComplete.setUser_id(UserBean.getInstance().getUserCache().getUser_id());
                    intent.putExtra("upload", sellMessageComplete);
                    startActivity(intent);
                }
                break;
        }
    }
}
