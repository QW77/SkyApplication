package com.sky.app.utils;

import android.content.Context;

import com.sky.app.bean.ShareBean;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 分享工具类
 */

public class ShareUtils {


    //这是分享函数，哪里需要分享调用此函数即可，
    //参数可自行设置
    public void showShare(Context context, ShareBean shareBean) {
//        ShareSDK
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher,getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用,// title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle(shareBean.getTitle());

        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareBean.getText());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(Environment.getExternalStorageDirectory().getPath()+"/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("51工匠");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        //网络图片的url：所有平台
//        oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
        // Url：仅在QQ空间使用
        oks.setTitleUrl("http://api.app.51craftsman.com/cc.jsp?type="+shareBean.getType()+"&id="+shareBean.getId()+"&user_id="+shareBean.getUser_id()+"&machine_id="+shareBean.getMachine_id());  //网友点进链接后，可以看到分享的详情
        // 启动分享GUI
        oks.show(context);
    }
}
