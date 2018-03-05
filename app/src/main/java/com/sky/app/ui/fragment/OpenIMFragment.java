package com.sky.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.sky.app.ui.activity.openIM.OpenIMLoginActivity;

/**
 * Created by Administrator on 2017/10/28 0028.
 */

public class OpenIMFragment extends Fragment {

    private OpenIMLoginActivity openIMLoginActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openIMLoginActivity = new OpenIMLoginActivity();
    }
}
