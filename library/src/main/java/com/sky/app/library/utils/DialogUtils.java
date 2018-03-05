package com.sky.app.library.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import com.sky.app.library.component.TimeUtils;

import java.util.Calendar;

/**
 * 弹出框管理
 * Created by sky on 2017/3/18.
 */

public class DialogUtils {

    /**
     * 日期组件
     */
    private static DatePickerDialog datePickerDialog = null;

    private static ProgressDialog progressDialog = null;

    /**
     * 特定加载框
     */
    private static ProgressDialog uploadDialog = null;

    /**
     * 特定加载框
     */
    private static ProgressDialog cancelDialog = null;

    /**
     * 展示loading
     */
    public static void showUploadLoading(Activity activity){
        if (null == uploadDialog){
            uploadDialog = new ProgressDialog(activity);
        }
        uploadDialog.setCancelable(false);
        uploadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        uploadDialog.setMessage("加载中......");

        if (null != uploadDialog && !uploadDialog.isShowing() && !activity.isFinishing()){
            uploadDialog.show();
        }
    }

    /**
     * 隐藏加载框
     */
    public static void hideUploadLoading(){
        if (null != uploadDialog && uploadDialog.isShowing()){
            uploadDialog.cancel();
            uploadDialog = null;
        }
    }

    /**
     * 展示loading
     */
    public static void showLoading(Activity activity){
        if (null == progressDialog){
            progressDialog = new ProgressDialog(activity);
        }
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中......");

        if (null != progressDialog && !progressDialog.isShowing() && !activity.isFinishing()){
            progressDialog.show();
        }
    }

    /**
     * 展示loading
     */
    public static void showLoading(Activity activity, String message){
        if (null == progressDialog){
            progressDialog = new ProgressDialog(activity);
        }
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);

        if (null != progressDialog && !progressDialog.isShowing() && !activity.isFinishing()){
            progressDialog.show();
        }
    }

    /**
     * 展示loading
     */
    public static void showCancelLoading(Activity activity, String message){
        if (null == cancelDialog){
            cancelDialog = new ProgressDialog(activity);
        }
        cancelDialog.setCancelable(true);
        cancelDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        cancelDialog.setMessage(message);

        if (null != cancelDialog && !cancelDialog.isShowing() && !activity.isFinishing()){
            cancelDialog.show();
        }
    }

    /**
     * 隐藏加载框
     */
    public static void hideLoading(){
        if (null != progressDialog && progressDialog.isShowing()){
            progressDialog.cancel();
            progressDialog = null;
        }
    }

    /**
     * 隐藏加载框
     */
    public static void hideCancelLoading(){
        if (null != cancelDialog && cancelDialog.isShowing()){
            cancelDialog.cancel();
            cancelDialog = null;
        }
    }

    /**
     * 展示日期组件(显示在组件上)
     */
    /*public static void showDateDialog(Context context, final TextView textView){
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        if (null != datePickerDialog && !datePickerDialog.isShowing()){
            datePickerDialog.show();
        }
    }*/

    /**
     * 展示日期组件(显示在组件上)
     */
    public static void showDateDialog(Context context, final TextView textView){
        TimeUtils.showDialog(context, new IDatePickerDialog() {
            @Override
            public void showDate(String year, String month, String day) {
                textView.setText(year + "-" + month + "-" + day);
            }
        });
    }

    /**
     * 显示日期组件(回调)
     * @param context
     * @param iDatePickerDialog
     */
    public static void showDateDialog(Context context, final IDatePickerDialog iDatePickerDialog){
        if (null != iDatePickerDialog){
            Calendar calendar = Calendar.getInstance();
            datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    iDatePickerDialog.showDate(year + "", (month + 1) > 9 ? (month + 1) + "" : "0" + (month + 1),
                            dayOfMonth > 9 ? dayOfMonth + "" : "0" + dayOfMonth);
                }
            }, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            if (null != datePickerDialog && !datePickerDialog.isShowing()){
                datePickerDialog.show();
            }
        }
    }

    public interface IDatePickerDialog{
        void showDate(String year, String month, String day);
    }

}
