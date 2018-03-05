package com.sky.app.library.component.footerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.sky.app.library.R;

/**
 * @author deadline
 * @time 2016/10/22
 */
public class SimpleFooterView extends BaseFooterView{

    private TextView mText;

    private ProgressBar progressBar;

    public SimpleFooterView(Context context) {
        this(context, null);
    }

    public SimpleFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.app_foot_loading, this);
        progressBar = (ProgressBar) view.findViewById(R.id.driver_progress_bar);
        mText = (TextView) view.findViewById(R.id.driver_progress_bar_txt);
    }

    @Override
    public void onLoadingMore() {
        progressBar.setVisibility(VISIBLE);
        mText.setVisibility(VISIBLE);
        mText.setText(R.string.loading);
    }

    public void showText(){
        progressBar.setVisibility(GONE);
        mText.setVisibility(VISIBLE);
    }

    /**************文字自行修改或根据传入的参数动态修改****************/

    @Override
    public void onNoMore(CharSequence message) {
        showText();
        mText.setText("-- the end --");
    }

    @Override
    public void onError(CharSequence message) {
        showText();
        mText.setText("啊哦，好像哪里不对劲!");
    }

    @Override
    public void onNetChange(boolean isAvailable) {
        showText();
        mText.setText("网络连接不通畅!");
    }
}
