package com.sky.app.ui.activity.openIM;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.YWContactFactory;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationCreater;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.kit.chat.ChattingFragment;
import com.sky.app.R;
import com.sky.app.bean.AppKey;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 聊天界面
 * targetAppKey，myAppKey请先确定好，并换成正确的appkey
 * <p>
 * 测试用的是18118850546  13776557157，这两个账号，密码均为123456
 * 13776557157对应的云旺账号171110021888
 */
public class ChatActivity extends BaseViewActivity {


    private SharedPreferences sp;
    private Bundle bundle;

    private String user_id;
    private String targetUserId;
    private YWIMKit mIMKit;
    private String targetAppKey = "24629506",//对方的appkey
            myAppKey = "24629506";//自己的appkey
    private YWConversation conversation;


    @BindView(R.id.rl_seller)
    RelativeLayout rl_seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Init();
        initFragment();
    }

    @OnClick({R.id.bt_send_link})
    public void onClick(View v) {
        switch (v.getId()) {
            //发送商品链接
            case R.id.bt_send_link:
                LogUtil.e(this, "发送链接");
                sendLink(bundle.getString("link"));
                break;
        }
    }

    /**
     * 初始化
     */
    private void Init() {
        bundle = getIntent().getExtras();
        sp = getSharedPreferences(AppKey.XmlName.APP_CACHE_USER_INFO, MODE_PRIVATE);

        user_id = sp.getString("uid", null);
        targetUserId = bundle.getString("targetUserId");
        targetUserId = "171110021888";//测试代码

        mIMKit = YWAPI.getIMKitInstance(user_id, myAppKey);

    }

    /**
     * 发送链接
     *
     * @param msg 链接字符串
     */
    private void sendLink(String msg) {

        //创建一条文本或者表情消息
        YWMessage message = YWMessageChannel.createTextMessage(msg);
        //创建一个与消息接收者的聊天会话
        IYWContact contact = YWContactFactory.createAPPContact(targetUserId, targetAppKey);

        //创建一条与消息接收者的会话，contact：表示聊天对象id
        YWIMCore imCore = mIMKit.getIMCore();
        final YWConversationCreater conversationCreater = imCore.getConversationService().getConversationCreater();
        YWConversation conversation = conversationCreater.createConversationIfNotExist(contact);
        //将消息发送给对方
        conversation.getMessageSender().sendMessage(message, 3, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                // 发送成功
                LogUtil.e(ChatActivity.this, "消息发送成功");

            }

            @Override
            public void onProgress(int arg0) {
                LogUtil.e(ChatActivity.this, "消息发送中=Progress=" + arg0);

            }

            @Override
            public void onError(int arg0, String arg1) {
                // 发送失败
                LogUtil.e(ChatActivity.this, "消息发送失败=code=" + arg0 + "=desc=" + arg1);
            }
        });

    }

    /**
     * 初始化聊天Fragment
     */
    private void initFragment() {
        //加载聊天Fragment
        ChattingFragment fragment = (ChattingFragment) mIMKit.getChattingFragment(targetUserId, targetAppKey);
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }


    @Override
    protected void init() {

    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void onDestoryActivity() {

    }

}
