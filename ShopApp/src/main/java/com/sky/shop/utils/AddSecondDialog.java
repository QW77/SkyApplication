package com.sky.shop.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.ui.fragment.ShopCategoryPageFragment;

/**
 * 添加一级分类
 * Created by sky on 2017/3/18.
 */

public class AddSecondDialog extends Dialog{
    private Activity activity = null;

    private ShopCategoryPageFragment.IFirstCategoryCallBack iFirstCategoryCallBack;

    EditText firstCategory;
    Button save;
    Button cancel;
    String name;

    public AddSecondDialog(@NonNull Activity activity, String name,
                           ShopCategoryPageFragment.IFirstCategoryCallBack iFirstCategoryCallBack) {
        super(activity, R.style.CustomDialog);
        this.activity = activity;
        this.iFirstCategoryCallBack = iFirstCategoryCallBack;
        this.name = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_add_second_category_layout);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        //初始化
        initView();
        initClick();
    }

    /**
     * 初始化事件
     */
    private void initClick() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(firstCategory.getText().toString().trim())){
                    T.showShort(activity, "请输入二级分类");
                    return;
                }
                if (null != iFirstCategoryCallBack){
                    iFirstCategoryCallBack.back(firstCategory.getText().toString().trim());
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        firstCategory = (EditText) findViewById(R.id.first_category);
        save = (Button) findViewById(R.id.app_save_btn);
        cancel = (Button) findViewById(R.id.app_cancel_btn);

        firstCategory.setText(name);
    }
}
