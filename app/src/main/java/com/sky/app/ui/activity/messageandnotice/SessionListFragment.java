package com.sky.app.ui.activity.messageandnotice;


import android.support.v4.widget.SwipeRefreshLayout;

import com.sky.app.bean.SupplyDetail;
import com.sky.app.contract.PublishContract;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewFragment;

import java.util.List;

/**
 * Created by bao on 2017/8/17.
 * 最近对话列表
 */

public class SessionListFragment extends BaseViewFragment<PublishContract.IPublishPresenter>
        implements PublishContract.IPublishView, SwipeRefreshLayout.OnRefreshListener, ShopContract.ICollectionView {

//
//    // 将最近联系人列表fragment动态集成进来。 开发者也可以使用在xml中配置的方式静态集成。
//    private void addRecentContactsFragment() {
//        fragment = new RecentContactsFragment();
//
//        getChildFragmentManager().beginTransaction().add(R.id.messages_fragment,fragment).commit();
//       // final UI activity = (UI) getActivity();
//
//        // 如果是activity从堆栈恢复，FM中已经存在恢复而来的fragment，此时会使用恢复来的，而new出来这个会被丢弃掉
//        //fragment = (RecentContactsFragment) activity.addFragment(fragment);
//
//        fragment.setCallback(new RecentContactsCallback() {
//            @Override
//            public void onRecentContactsLoaded() {
//                // 最近联系人列表加载完毕
//            }
//
//            @Override
//            public void onUnreadCountChange(int unreadCount) {
//                // ReminderManager.getInstance().updateSessionUnreadNum(unreadCount);
//            }
//
//            @Override
//            public void onItemClick(RecentContact recent) {
//                // 回调函数，以供打开会话窗口时传入定制化参数，或者做其他动作
//                switch (recent.getSessionType()) {
//                    case P2P:
//                        SessionHelper.startP2PSession(getActivity(), recent.getContactId());
//                        break;
//                    case Team:
//                        SessionHelper.startTeamSession(getActivity(), recent.getContactId(), null);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public String getDigestOfAttachment(RecentContact recentContact, MsgAttachment attachment) {
//                // 设置自定义消息的摘要消息，展示在最近联系人列表的消息缩略栏上
//                // 当然，你也可以自定义一些内建消息的缩略语，例如图片，语音，音视频会话等，自定义的缩略语会被优先使用。
//                if (attachment instanceof GuessAttachment) {
//                    GuessAttachment guess = (GuessAttachment) attachment;
//                    return guess.getValue().getDesc();
//                } else if (attachment instanceof RTSAttachment) {
//                    return "[白板]";
//                } else if (attachment instanceof StickerAttachment) {
//                    return "[贴图]";
//                } else if (attachment instanceof SnapChatAttachment) {
//                    return "[阅后即焚]";
//                } else if (attachment instanceof RedPacketAttachment) {
//                    return "[红包]";
//                } else if (attachment instanceof RedPacketOpenedAttachment) {
//                    return ((RedPacketOpenedAttachment) attachment).tipContent;
//                }
//
//                return null;
//            }
//
//            @Override
//            public String getDigestOfTipMsg(RecentContact recent) {
//                String msgId = recent.getRecentMessageId();
//                List<String> uuids = new ArrayList<>(1);
//                uuids.add(msgId);
//                List<IMMessage> msgs = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuids);
//                if (msgs != null && !msgs.isEmpty()) {
//                    IMMessage msg = msgs.get(0);
//                    Map<String, Object> content = msg.getRemoteExtension();
//                    if (content != null && !content.isEmpty()) {
//                        return (String) content.get("content");
//                    }
//                }
//
//                return null;
//            }
//        });
//    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void getRefreshData(List<SupplyDetail> list) {

    }

    @Override
    public void getLoadMoreData(List<SupplyDetail> list) {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }


    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected PublishContract.IPublishPresenter presenter() {
        return null;
    }

    @Override
    public void showCollectView(String msg) {

    }

    @Override
    public void showCollectError(String error) {

    }
}
