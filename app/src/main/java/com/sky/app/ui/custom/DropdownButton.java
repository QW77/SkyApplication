package com.sky.app.ui.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sky.app.R;

/**
 * tab按钮
 * Created by Administrator on 2015/5/28.
 */
public class DropdownButton extends RelativeLayout {
    TextView textView;
    View bottomLine;

    public DropdownButton(Context context) {
        this(context, null);
    }

    public DropdownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.app_dropdown_tab_button, this, true);
        textView = (TextView) view.findViewById(R.id.app_tab_txt_tv);
        bottomLine = view.findViewById(R.id.app_tab_line);
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public void setTextSize(int type, float size){
        textView.setTextSize(type, size);
    }

    public void setChecked(Drawable icon, boolean checked, boolean isShowLine) {
        if (checked) {
            textView.setTextColor(getResources().getColor(R.color.main_colorPrimary));
            if (isShowLine){
                bottomLine.setVisibility(VISIBLE);
            }else{
                bottomLine.setVisibility(GONE);
            }
        } else {
            textView.setTextColor(getResources().getColor(R.color.sky_color_333333));
            bottomLine.setVisibility(GONE);
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }
}
