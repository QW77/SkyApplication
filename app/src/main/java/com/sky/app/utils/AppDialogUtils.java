package com.sky.app.utils;

import android.app.Activity;

import com.sky.app.bean.ProductDeatilResponse;
import com.sky.app.ui.activity.product.ProductDetailActivity;
import com.sky.app.ui.activity.user.SettingsActivity;

/**
 * Created by sky on 2017/5/13.
 */

public class AppDialogUtils {
    /**
     * 拍照界面
     */
    private static TakePhotoDialog takePhotoDialog = null;

    /**
     * 修改登录密码
     */
    private static PwdDialog pwdDialog = null;

    /**
     * 购买商品选择类型
     */
    private static BuyDialog buyDialog = null;

    /**
     * 绑定手机号码
     */
    private static BindMobileDialog bindMobileDialog = null;

    /**
     * 支付
     */
    private static PayDialog payDialog = null;

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
     * 展示修改密码界面
     * @param activity
     */
    public static void showPwdDialog(Activity activity, SettingsActivity.IPwdCallBack iPwdCallBack){
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
     * 展示购买商品选择类型
     * @param activity
     */
    public static void showBuyDialog(Activity activity, ProductDeatilResponse productDeatilResponse,
                                     ProductDetailActivity.ISelectCallBack iSelectCallBack){
        buyDialog = new BuyDialog(activity, productDeatilResponse, iSelectCallBack);
        if (null != buyDialog && !buyDialog.isShowing()){
            buyDialog.show();
        }
    }

    /**
     * 关闭购买商品选择窗口
     */
    public static void closeBuyDialog(){
        if (null != buyDialog && buyDialog.isShowing()){
            buyDialog.dismiss();
            buyDialog = null;
        }
    }


    /**
     * 展示绑定手机号码
     * @param activity
     */
    public static void showBindMobileDialog(Activity activity,
                                            SettingsActivity.IBindMobileCallBack iBindMobileCallBack,
                                            boolean isCanClose){
        bindMobileDialog = new BindMobileDialog(activity, iBindMobileCallBack, isCanClose);
        if (null != bindMobileDialog && !bindMobileDialog.isShowing()){
            bindMobileDialog.show();
        }
    }

    /**
     * 关闭绑定手机号码窗口
     */
    public static void closeBindMobileDialog(){
        if (null != bindMobileDialog && bindMobileDialog.isShowing()){
            bindMobileDialog.dismiss();
            bindMobileDialog = null;
        }
    }

    /**
     * 展示支付界面
     * @param activity
     */
    public static void showPayDialog(Activity activity,
                                            PayDialog.IPayCallback iPayCallback){
        payDialog = new PayDialog(activity, iPayCallback);
        if (null != payDialog && !payDialog.isShowing()){
            payDialog.show();
        }
    }

    /**
     * 关闭支付界面
     */
    public static void closePayDialog(){
        if (null != payDialog && payDialog.isShowing()){
            payDialog.dismiss();
            payDialog = null;
        }
    }
}
