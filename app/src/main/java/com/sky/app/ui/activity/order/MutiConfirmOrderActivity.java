package com.sky.app.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.MutiOrderIn;
import com.sky.app.bean.MutiShopCarIn;
import com.sky.app.bean.ShopCarDetail;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.MutiConfirmOrderPresenter;
import com.sky.app.ui.activity.address.MyAddressActivity;
import com.sky.app.ui.adapter.MyConfirmOrderAdaptor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 确认订单(购物车)
 */
public class MutiConfirmOrderActivity extends BaseViewActivity<OrderContract.IMutiConfirmOrderPresenter>
        implements OrderContract.IMutiConfirmOrderView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_user_name)
    TextView userName;
    @BindView(R.id.app_mobile)
    TextView mobile;
    @BindView(R.id.app_address)
    TextView address;
    @BindView(R.id.app_money)
    TextView money;
    @BindView(R.id.app_remark)
    EditText remark;
    @BindView(R.id.app_product_list)
    RecyclerView recyclerView;
    MyConfirmOrderAdaptor myConfirmOrderAdaptor;
    List<ShopCarDetail> shopCarDetails = new ArrayList<>();
    List<String> ids;

    private AddressDetail addressDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_muti_confirm_order);
    }

    @Override
    protected OrderContract.IMutiConfirmOrderPresenter presenter() {
        return new MutiConfirmOrderPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_confirm_order_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myConfirmOrderAdaptor = new MyConfirmOrderAdaptor(context, shopCarDetails);
        recyclerView.setAdapter(myConfirmOrderAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 12),
                        AppUtils.getSystemColor(context, R.color.sky_color_white)));
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().queryDefaultAddr();
        MutiShopCarIn mutiShopCarIn = new MutiShopCarIn();
        this.ids = getIntent().getStringArrayListExtra("ids");
        mutiShopCarIn.setIds(this.ids);
        getPresenter().queryProductList(mutiShopCarIn);
    }

    @Override
    public void confirmOrderResult(String msg) {
        T.showShort(context, msg);
        startActivity(new Intent(context, MyOrderActivity.class));
        finish();
    }

    @Override
    public void queryProductListResult(List<ShopCarDetail> shopCarDetailList) {
        myConfirmOrderAdaptor.add(shopCarDetailList);
        DecimalFormat df = new DecimalFormat("0.00");
        double m = 0;
        for (ShopCarDetail shopCarDetail : shopCarDetailList){
            m += (shopCarDetail.getAttr_price_now()/100 * shopCarDetail.getProduct_num());
        }
        money.setText("¥" + df.format(m));
    }

    @Override
    public void queryDefaultAddrResult(AddressDetail addressDetail) {
        this.addressDetail = addressDetail;
        if (null != addressDetail){
            mobile.setText(addressDetail.getContact_mobile());
            address.setText(addressDetail.getContact_address());
            userName.setText("收货人：" + addressDetail.getContact_name());
        }
    }

    @OnClick(R.id.app_default_addr)
    void clickAddr(){
        startActivity(new Intent(context, MyAddressActivity.class));
    }

    @OnClick(R.id.app_submit_tv)
    void submit(){
        if (null == addressDetail){
            T.showShort(context, "请选择默认收货地址");
            return;
        }
        if (null == ids || ids.isEmpty()){
            T.showShort(context, "没有购买的商品");
            return;
        }
        MutiOrderIn mutiOrderIn = new MutiOrderIn();
        mutiOrderIn.setAddress_id(addressDetail.getAddress_id());
        mutiOrderIn.setIds(ids);
        mutiOrderIn.setRemark(remark.getText().toString());
        getPresenter().confirmOrder(mutiOrderIn);
    }
}
