package com.sky.shop.utils;

import android.app.Activity;

import com.sky.shop.bean.StringBean;
import com.sky.shop.ui.activity.shop.SafeCenterActivity;
import com.sky.shop.ui.fragment.ShopCategoryPageFragment;

import java.util.List;

/**
 * Created by sky on 2017/5/13.
 */

public class AppDialogUtils {
    /**
     * 拍照界面
     */
    private static TakePhotoDialog takePhotoDialog = null;

    /**
     * 新增一级
     */
    private static AddFirstDialog addFirstDialog = null;

    /**
     * 新增二级
     */
    private static AddSecondDialog addSecondDialog = null;

    /**
     * 修改登录密码
     */
    private static PwdDialog pwdDialog = null;

    /**
     * 绑定手机号码
     */
    private static BindMobileDialog bindMobileDialog = null;

    /**
     * 绑定邮箱
     */
    private static EmailDialog emailDialog = null;

    /**
     * 通用列表弹框
     */
    private static ListDialog listDialog = null;

    /**
     * 展示拍照界面
     * @param activity
     */
    public static void showTakePhotoView(Activity activity){
        takePhotoDialog = new TakePhotoDialog(activity);
        if (null != takePhotoDialog && !takePhotoDialog.isShowing()){
            takePhotoDialog.show();
            takePhotoDialog = null;
        }
    }

    /**
     * 展示新增界面
     * @param activity
     */
    public static void showAddFirstDialog(Activity activity, String name,
                                          ShopCategoryPageFragment.IFirstCategoryCallBack iFirstCategoryCallBack){
        addFirstDialog = new AddFirstDialog(activity, name, iFirstCategoryCallBack);
        if (null != addFirstDialog && !addFirstDialog.isShowing()){
            addFirstDialog.show();
        }
    }

    /**
     * 关闭新增窗口
     */
    public static void closeAddFirstDialog(){
        if (null != addFirstDialog && addFirstDialog.isShowing()){
            addFirstDialog.dismiss();
            addFirstDialog = null;
        }
    }

    /**
     * 展示新增界面
     * @param activity
     */
    public static void showAddSecondDialog(Activity activity, String name,
                                          ShopCategoryPageFragment.IFirstCategoryCallBack iFirstCategoryCallBack){
        addSecondDialog = new AddSecondDialog(activity, name, iFirstCategoryCallBack);
        if (null != addSecondDialog && !addSecondDialog.isShowing()){
            addSecondDialog.show();
        }
    }

    /**
     * 关闭新增窗口
     */
    public static void closeAddSecondDialog(){
        if (null != addSecondDialog && addSecondDialog.isShowing()){
            addSecondDialog.dismiss();
            addSecondDialog = null;
        }
    }

    /**
     * 展示修改密码界面
     * @param activity
     */
    public static void showPwdDialog(Activity activity, SafeCenterActivity.IPwdCallBack iPwdCallBack){
        pwdDialog = new PwdDialog(activity, iPwdCallBack);
        if (null != pwdDialog && !pwdDialog.isShowing()){
            pwdDialog.show();
        }
    }

    /**
     * 关闭修改登录密码窗口
     */
    public static void closePwdDialog(){
        if (null != pwdDialog && pwdDialog.isShowing()){
            pwdDialog.dismiss();
            pwdDialog = null;
        }
    }

    /**
     * 展示绑定手机号码
     * @param activity
     */
    public static void showBindMobileDialog(Activity activity,
                                            SafeCenterActivity.IBindMobileCallBack iBindMobileCallBack,
                                            boolean isCanClose){
        bindMobileDialog = new BindMobileDialog(activity, iBindMobileCallBack, isCanClose);
        if (null != bindMobileDialog && !bindMobileDialog.isShowing()){
            bindMobileDialog.show();
        }
    }

    /**
     * 关闭修改登录密码窗口
     */
    public static void closeBindMobileDialog(){
        if (null != bindMobileDialog && bindMobileDialog.isShowing()){
            bindMobileDialog.dismiss();
            bindMobileDialog = null;
        }
    }

    /**
     * 展示绑定邮箱
     * @param activity
     */
    public static void showBindEmailDialog(Activity activity,
                                            SafeCenterActivity.IBindEmailCallBack iBindEmailCallBack){
        emailDialog = new EmailDialog(activity, iBindEmailCallBack);
        if (null != emailDialog && !emailDialog.isShowing()){
            emailDialog.show();
        }
    }

    /**
     * 关闭绑定邮箱
     */
    public static void closeBindEmailDialog(){
        if (null != emailDialog && emailDialog.isShowing()){
            emailDialog.dismiss();
            emailDialog = null;
        }
    }

    /**
     * 展示通用列表
     * @param activity
     */
    public static void showListDialog(Activity activity, ListDialog.OnItemClickListener onItemClickListener, List<StringBean> stringBeen){
        listDialog = new ListDialog(activity, onItemClickListener, stringBeen);
        if (null != listDialog && !listDialog.isShowing()){
            listDialog.show();
        }
    }

    /**
     * 关闭通用列表
     */
    public static void closeListDialog(){
        if (null != listDialog && listDialog.isShowing()){
            listDialog.dismiss();
            listDialog = null;
        }
    }
}
