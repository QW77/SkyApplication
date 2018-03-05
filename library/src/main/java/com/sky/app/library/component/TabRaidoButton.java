package com.sky.app.library.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sky.app.library.R;

/**
 * 主页的底部TAB页
 */
public class TabRaidoButton extends RelativeLayout {

    RadioGroup rg;
    RelativeLayout container;
    TextView mRadioButton;
    TextView mMsgButton;
    ImageView customRadioButton;
    Drawable keyDown;
    Drawable keyUp;
    TabRaidoButton beforeBtn;

    boolean isChecked = false;//是否选中
    
    public TabRaidoButton(Context context) {
        super(context);
    }
    
    public TabRaidoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttr(context, attrs);
    }

    /**
     * 初始化视图
     * @param context
     */
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_tab_raidobutton_layout, this, true);
        container = (RelativeLayout)view.findViewById(R.id.custom_radio_container);
        mRadioButton = (TextView)view.findViewById(R.id.rb_txt);
        mMsgButton = (TextView)view.findViewById(R.id.rb_msg_txt);
        customRadioButton = (ImageView)view.findViewById(R.id.custom_radiobutton);
    }
    
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TwoTxtRaidoButton);
        
        boolean isChecked = ta.getBoolean(R.styleable.TwoTxtRaidoButton_isChecked, false);
        this.isChecked = isChecked;
        CharSequence oneTxt = ta.getString(R.styleable.TwoTxtRaidoButton_textOne);
        CharSequence twoTxt = ta.getString(R.styleable.TwoTxtRaidoButton_textTwo);
        
        int oneSize = ta.getDimensionPixelOffset(R.styleable.TwoTxtRaidoButton_textOneSize, 0);
        int twoSize = ta.getDimensionPixelOffset(R.styleable.TwoTxtRaidoButton_textTwoSize, 0);

        keyDown = ta.getDrawable(R.styleable.TwoTxtRaidoButton_image_down);
        keyUp = ta.getDrawable(R.styleable.TwoTxtRaidoButton_image_up);
        
        if (oneTxt != null) {
            mRadioButton.setText(oneTxt);
        }
        if (twoTxt != null) {
            mMsgButton.setText(twoTxt);
        }
        if (oneSize > 0)
            mRadioButton.setTextSize(oneSize);
        if (twoSize > 0)
            mMsgButton.setTextSize(twoSize);
        
        setCheckedView(isChecked);
        
        ta.recycle();
    }
    
    public void setTwoTxt(int i) {
        if (i != 0) {
            mMsgButton.setVisibility(View.VISIBLE);
        }else{
        	mMsgButton.setVisibility(View.INVISIBLE);
        }
    }

    public void hideTwoTxt() {
        mMsgButton.setVisibility(View.INVISIBLE);
    }
    
    public void setBeforeBtn(TabRaidoButton beforeBtn) {
        this.beforeBtn = beforeBtn;
    }
    
    public void setCheckedView(Boolean checked) {
        mRadioButton.setSelected(checked);
        mMsgButton.setSelected(checked);
        if (checked) {
            customRadioButton.setImageDrawable(keyDown);
        } else {
            customRadioButton.setImageDrawable(keyUp);
        }
    }
    
    public void setChecked(Boolean checked) {
        this.isChecked = checked;
    }
    
    boolean isChecked() {
        return isChecked;
    }
}
