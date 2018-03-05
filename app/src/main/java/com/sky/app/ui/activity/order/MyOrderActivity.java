package com.sky.app.ui.activity.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.sky.app.R;
import com.sky.app.bean.AlipayOut;
import com.sky.app.bean.CancelOrderIn;
import com.sky.app.bean.OrderDetail;
import com.sky.app.bean.PayResult;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.app.library.utils.ThreadPoolManager;
import com.sky.app.presenter.MyOrderPresenter;
import com.sky.app.ui.adapter.MyOrderAdaptor;
import com.sky.app.utils.AppDialogUtils;
import com.sky.app.utils.PayDialog;
import com.sky.app.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 我的订单
 */
public class MyOrderActivity extends BaseViewActivity<OrderContract.IMyOrderPresenter>
        implements OrderContract.IMyOrderView, SwipeRefreshLayout.OnRefreshListener{

    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI api;

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.my_order_list)
    LuRecyclerView mRecyclerView;

    MyOrderAdaptor myOrderAdaptor;
    List<OrderDetail> orderDetailList = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_mine_my_order);
    }

    @Override
    protected OrderContract.IMyOrderPresenter presenter() {
        return new MyOrderPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_my_order_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        api = WXAPIFactory.createWXAPI(this, WXPayEntryActivity.APP_ID);
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 12),
                        AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        myOrderAdaptor = new MyOrderAdaptor(context, orderDetailList);
        myOrderAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, final int pos) {
                switch (itemView.getId()){
                    case R.id.cancel:
                        CancelOrderIn a = new CancelOrderIn();
                        a.setOrder_id(orderDetailList.get(pos).getOrder_id());
                        getPresenter().cancel(a);
                        break;
                    case R.id.confirm:
                        switch (orderDetailList.get(pos).getPay_state()){
                            case 0://待支付
                            case 2://支付失败
                                switch (orderDetailList.get(pos).getState()){
                                    case 2://交易关闭
                                        CancelOrderIn c = new CancelOrderIn();
                                        c.setOrder_id(orderDetailList.get(pos).getOrder_id());
                                        getPresenter().del(c);
                                        break;
                                    default:
                                        AppDialogUtils.showPayDialog(MyOrderActivity.this, new PayDialog.IPayCallback() {
                                            @Override
                                            public void pay(int flag) {
                                                switch (flag){
                                                    case 0://支付宝
                                                        CancelOrderIn alipay = new CancelOrderIn();
                                                        alipay.setOrder_id(orderDetailList.get(pos).getOrder_id());
                                                        getPresenter().alipay(alipay);
                                                        break;
                                                    case 1://微信
                                                        CancelOrderIn weixin = new CancelOrderIn();
                                                        weixin.setOrder_id(orderDetailList.get(pos).getOrder_id());
                                                        getPresenter().weixinPay(weixin);
                                                        break;
                                                }
                                            }
                                        });
                                        break;
                                }
                                break;
                            case 1://支付成功
                                switch (orderDetailList.get(pos).getState()){
                                    case 1:
                                    case 5:
                                        Intent k = new Intent(context, CommentActivity.class);
                                        k.putExtra("image", orderDetailList.get(pos).getProduct_image_url());
                                        k.putExtra("orderId", orderDetailList.get(pos).getOrder_id());
                                        startActivity(k);
                                        break;
                                    case 4://确认收货
                                        CancelOrderIn b = new CancelOrderIn();
                                        b.setOrder_id(orderDetailList.get(pos).getOrder_id());
                                        getPresenter().confirm(b);
                                        break;
                                }
                                break;
                        }
                }
            }
        });
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(myOrderAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getPresenter().hasMore()){
                    getPresenter().loadMore();
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent ,android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部加载完", "网络不给力啊，点击再试一次吧");
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void getRefreshData(List<OrderDetail> list) {
        myOrderAdaptor.add(list);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<OrderDetail> list) {
        myOrderAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void responseCancel(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void responseConfirm(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void responseDel(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void responseWeixinPay(JSONObject jsonObject) {
        AppDialogUtils.closePayDialog();
        PayReq req = new PayReq();
        try {
            req.appId = jsonObject.has("appId") ? jsonObject.getString("appId") : "";
            req.partnerId = jsonObject.has("partnerId") ? jsonObject.getString("partnerId") : "";
            req.prepayId = jsonObject.has("prepayId") ? jsonObject.getString("prepayId") : "";
            req.nonceStr = jsonObject.has("nonceStr") ? jsonObject.getString("nonceStr") : "";
            req.timeStamp = jsonObject.has("timeStamp") ? jsonObject.getString("timeStamp") : "";
            req.packageValue = jsonObject.has("package") ? jsonObject.getString("package") : "";
            req.sign = jsonObject.has("sign") ? jsonObject.getString("sign") : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        api.sendReq(req);
    }

    @Override
    public void responseAlipay(final AlipayOut alipayOut) {
        AppDialogUtils.closePayDialog();
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(MyOrderActivity.this);
                L.msg("支付宝返回==>" + alipayOut.getReturn_info());
                Map<String, String> result = alipay.payV2(alipayOut.getReturn_info(), true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData();
    }

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(this);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        T.showShort(context, "支付成功");
                    } else {
                        T.showShort(context, "支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }
}
