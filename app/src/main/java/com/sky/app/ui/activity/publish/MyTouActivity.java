package com.sky.app.ui.activity.publish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTouActivity extends AppCompatActivity {
    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;
    @BindView(R.id.my_toutiao_1)
    TextView myToutiao1;
    @BindView(R.id.my_toutiao_2)
    TextView myToutiao2;
    private String noticeContent;
//    @BindView(R.id.normal_toolbar)
//    Toolbar normalToolbar;
//    @BindView(R.id.my_tou_listview)
//    ListView myTouListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tou);
        ButterKnife.bind(this);
        init();
        initViewsAndEvent();
    }

    private void init() {
        title.setText("工匠头条");
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        noticeContent = getIntent().getStringExtra("noticeContent");
        myToutiao1.setText(noticeContent);
    }

    private void initViewsAndEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
