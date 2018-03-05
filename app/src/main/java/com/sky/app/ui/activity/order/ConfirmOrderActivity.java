package com.sky.app.ui.activity.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.OrderDetail;
import com.sky.app.bean.SingleOrderIn;
import com.sky.app.bean.SingleOrderOut;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.ConfirmOrderPresenter;
import com.sky.app.ui.activity.address.MyAddressActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseViewActivity<OrderContract.IConfirmOrderPresenter>
        implements OrderContract.IConfirmOrderView {

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
    @BindView(R.id.app_change_num)
    TextView changeNum;
    @BindView(R.id.app_money)
    TextView money;
    @BindView(R.id.app_remark)
    EditText remark;

    @BindView(R.id.app_sell_name)
    TextView sellName;
    @BindView(R.id.app_img)
    ImageView productImage;
    @BindView(R.id.app_product_title)
    TextView productTitle;
    @BindView(R.id.app_attr_name)
    TextView attrName;
    @BindView(R.id.app_price)
    TextView price;
    @BindView(R.id.app_num)
    TextView productNum;

    private int num;
    private AddressDetail addressDetail;
    private OrderDetail orderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_confirm_order);
    }

    @Override
    protected OrderContract.IConfirmOrderPresenter presenter() {
        return new ConfirmOrderPresenter(this, this);
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
        this.orderDetail = (OrderDetail) getIntent().getSerializableExtra("detail");
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
        showPage();
        countMoney();
    }

    /**
     * 展示商品界面
     */
    private void showPage() {
        if (null != orderDetail) {
            this.num = orderDetail.getProduct_num();
            ImageHelper.getInstance().displayDefinedImage(orderDetail.getProduct_image_url(),
                    productImage, R.mipmap.app_default_icon, R.mipmap.app_default_icon);
            productTitle.setText(orderDetail.getProduct_name());
            attrName.setText("选择分类：" + orderDetail.getAttr_name());
            price.setText("¥" + AppUtils.parseDouble(orderDetail.getAttr_price_now() / 100));
            productNum.setText("x" + this.num);
            changeNum.setText(this.num + "");
            sellName.setText(orderDetail.getSeller_name());
        }
    }

    @Override
    public void confirmOrderResult(SingleOrderOut singleOrderOut) {
        T.showShort(context, "下单成功");
        startActivity(new Intent(context, MyOrderActivity.class));
        finish();
    }

    @Override
    public void queryDefaultAddrResult(AddressDetail addressDetail) {
        this.addressDetail = addressDetail;
        if (null != addressDetail) {
            mobile.setText(addressDetail.getContact_mobile());
            address.setText(addressDetail.getContact_address());
            userName.setText("收货人：" + addressDetail.getContact_name());
        }
    }

    @OnClick(R.id.app_default_addr)
    void clickAddr() {
        startActivity(new Intent(context, MyAddressActivity.class));
    }

    @OnClick(R.id.app_add)
    void add() {
        int n = Integer.parseInt(changeNum.getText().toString());
        this.num = ++n;
        changeNum.setText(this.num + "");
        productNum.setText("x" + this.num);
        countMoney();
    }

    @OnClick(R.id.app_del)
    void del() {
        int n = Integer.parseInt(changeNum.getText().toString());
        if (n < 2) {
            T.showShort(context, "购买商品数量不能少于1件");
            return;
        }
        this.num = --n;
        changeNum.setText(this.num + "");
        productNum.setText("x" + this.num);
        countMoney();
    }

    @OnClick(R.id.app_submit_tv)
    void submit() {
        if (null == addressDetail) {
            T.showShort(context, "请选择默认收货地址");
            return;
        }
        if (null == orderDetail) {
            T.showShort(context, "请选择商品");
            return;
        }
        new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题
                .setMessage("商品价格可能为商家参考价格，请联系商家确认价格无误后再确认购买！51工匠推荐您到店付款购买！")//设置显示的内容
                .setPositiveButton("取消购买", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                        finish();
                    }
                }).setNegativeButton("确定购买", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件
                SingleOrderIn singleOrderIn = new SingleOrderIn();
                singleOrderIn.setAttr_id(orderDetail.getAttr_id());
                singleOrderIn.setAddress_id(addressDetail.getAddress_id());
                singleOrderIn.setProduct_id(orderDetail.getProduct_id());
                singleOrderIn.setProduct_num(Integer.parseInt(changeNum.getText().toString()));
                singleOrderIn.setRemark(remark.getText().toString());
                getPresenter().confirmOrder(singleOrderIn);

            }

        }).show();//在按键响应事件中显示此对话框

    }

    /**
     * 计算金额
     */
    private void countMoney() {
        if (null == orderDetail) return;
        double h = (orderDetail.getAttr_price_now() / 100) * num;
        money.setText(AppUtils.parseDouble(h));
    }
}
