package com.sky.app.ui.activity.messageandnotice;


import android.support.v4.app.Fragment;

/**
 * Created by bao on 2017/8/17.
 */

public class ContactListFragment extends Fragment {

//    @BindView(R.id.tv_new_friend_num)
//    TextView tvNewFriendNum;
//
//    private List<UserBean> listApply;
//
//    private int newFriendNum;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_contacts_list;
//    }
//
//    @Override
//    protected void setupViews(View view, Bundle savedInstanceState) {
//
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//
//        return rootView;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        //检查是否有新的好友
//        ReminderManager.getInstance().registerUnreadNumChangedCallback(new ReminderManager.UnreadNumChangedCallback() {
//            @Override
//            public void onUnreadNumChanged(ReminderItem item) {
//                if (item.getId() != ReminderId.CONTACT) {
//                   // return;
//                }
//            }
//        });
//        checkNewFriend(true);
//    }
//
//    @OnClick({R.id.item_my_family, R.id.item_all_family, R.id.item_my_fans, R.id.item_my_friends})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.item_my_family:
//                MyFamilyActivity.show(getActivity());
//                break;
//            case R.id.item_all_family:
//                FansFamilyActivity.show(getActivity());
//                break;
//            case R.id.item_my_fans:
//                startActivity(new Intent(getContext(), MyFansActivity.class));
//                break;
//            case R.id.item_my_friends:
//                Intent intent = new Intent(getContext(), FriendListActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEvent messageEvent) {
//        Logger.d("收到通知ContactListFragment:" + messageEvent.toString());
//        int type = messageEvent.getType();
//        String message = messageEvent.getMessage();
//         if (type == MessageEvent.TYPE_ADD_NEW_FRIEND) {
//             newFriendNum += Integer.parseInt(message);
//             tvNewFriendNum.setVisibility(View.VISIBLE);
//             tvNewFriendNum.setText(newFriendNum + "");
//             // checkNewFriend(false);
//        }
//
//    }
//
//    public void checkNewFriend(boolean isPost) {
//        final Map<String, String> params = new HashMap<>();
//        HttpUtils.post(getActivity(), Constants.URL_FRIEND_BEAPPLY, params, new CheckCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Logger.d("查询新的好友：" + response);
//                if (response != null) {
//                    listApply = JSONArray.parseArray(response, UserBean.class);
//                    int num = listApply.size();
//                    newFriendNum = num;
//                    if (listApply.size() == 0) {
//                        tvNewFriendNum.setVisibility(View.INVISIBLE);
//
//                    } else {
//                        tvNewFriendNum.setVisibility(View.VISIBLE);
//                        tvNewFriendNum.setText(num + "");
//
//                    }
//
//                    if (isPost)
//                        EventBus.getDefault().post(new MessageEvent(num + "", MessageEvent.TYPE_NEW_FRIEND));
//
//                }
//            }
//        });
//    }
}
