package com.sky.app.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationCreater;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.sky.app.R;
import com.sky.app.bean.AppKey;
import com.sky.app.ui.activity.publish.MyPublishActivity;
import com.sky.app.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 聊天界面
 */
public class TestActivity extends AppCompatActivity {


    @BindView(R.id.test_main)
    Button testMain;
    private SharedPreferences sp;
    String user_id;
    String target;
    YWIMKit mIMKit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        Init();
        initFragment();
    }

    @OnClick(R.id.test_main)
    void clickMain() {
        LogUtil.e(this, "开始发送消息");
        sendLink("hello world");

    }

//    public void test(View v) {
//        switch (v.getId()) {
//            //发送商品链接
//            case R.id.bt_send_link:
//                LogUtil.e(this, "开始发送消息");
//                sendLink("hello world");
//                break;
//        }
//    }

    /**
     * 初始化
     */
    private void Init() {
        sp = getSharedPreferences(AppKey.XmlName.APP_CACHE_USER_INFO, MODE_PRIVATE);
        user_id = sp.getString("uid", null);
        target = "171110021888";
        target = sp.getString("uid", null);
        mIMKit = YWAPI.getIMKitInstance(user_id, "24629506");
    }

    /**
     * 发送链接
     *
     * @param msg 链接字符串
     */
    private void sendLink(String msg) {
        //创建一条文本或者表情消息
        YWMessage message = YWMessageChannel.createTextMessage(msg);
        //创建一个与消息接收者的聊天会话，userId：表示聊天对象id
        YWIMCore imCore = mIMKit.getIMCore();
        final YWConversationCreater conversationCreater = imCore.getConversationService().getConversationCreater();
        YWConversation conversation = conversationCreater.createConversationIfNotExist(target);
        //将消息发送给对方
        conversation.getMessageSender().sendMessage(message, 500, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                LogUtil.e(TestActivity.this, "消息发送成功");
                // 发送成功
            }

            @Override
            public void onProgress(int arg0) {
                LogUtil.e(TestActivity.this, "消息发送中=Progress=" + arg0);

            }

            @Override
            public void onError(int arg0, String arg1) {
                LogUtil.e(TestActivity.this, "消息发送失败=code=" + arg0 + "=desc=" + arg1);
                // 发送失败
            }
        });

    }

    /**
     * 初始化聊天Fragment
     */
    private void initFragment() {
        //加载聊天Fragment
        Fragment fragment = mIMKit.getChattingFragment(target, "24629506");
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }


}
