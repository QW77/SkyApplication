package com.sky.app.library.component;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.sky.app.library.R;
import com.sky.app.library.utils.DialogUtils;

import java.util.Calendar;

/**
 * Created by sky on 2017/9/18.
 */

public class TimeUtils {

    private static TimeUtils datePickerDialog = null;

    public static void showDialog(Context context, final DialogUtils.IDatePickerDialog iDatePickerDialog){
        if (null != iDatePickerDialog){
            //通过自定义控件AlertDialog实现
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.date_dialog, null);
            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
            //设置日期简略显示 否则详细显示 包括:星期\周
            datePicker.setCalendarViewShown(false);
            //初始化当前日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), null);
            //设置date布局
            builder.setView(view);
            builder.setTitle("设置日期信息");
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    iDatePickerDialog.showDate(datePicker.getYear() + "", (datePicker.getMonth() + 1) + "",
                            datePicker.getDayOfMonth() + "");
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }
    }
}
