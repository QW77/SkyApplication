package com.sky.app.library.component;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 动态字符的捕获
 * Created by sky on 2017/3/24.
 */

public class CustomTextWatcher implements TextWatcher{
    private EditText editText;
    private ICustomTextWatcher iCustomTextWatcher;

    public CustomTextWatcher(EditText editText, ICustomTextWatcher iCustomTextWatcher){
        this.editText = editText;
        this.iCustomTextWatcher = iCustomTextWatcher;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (null != iCustomTextWatcher){
            iCustomTextWatcher.beforeChange(editText, s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (null != iCustomTextWatcher){
            iCustomTextWatcher.changing(editText, s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (null != iCustomTextWatcher){
            iCustomTextWatcher.changed(editText, s);
        }
    }

    public interface ICustomTextWatcher{
        void beforeChange(EditText e, CharSequence s, int start, int count, int after);
        void changing(EditText e, CharSequence s, int start, int before, int count);
        void changed(EditText e, Editable s);
    }
}
