package com.sky.app.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.OrderDetail;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 我的订单列表适配器
 */
public class MyOrderAdaptor extends BaseRecyclerAdapter<OrderDetail> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<OrderDetail> list;

    public MyOrderAdaptor(Context context, List<OrderDetail> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_mine_my_order_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, OrderDetail item) {
        holder.setText(R.id.app_order, "订单编号：" + item.getOrder_id());
        holder.setText(R.id.app_time, item.getCreate_time());
        holder.setText(R.id.app_shop, item.getSeller_name());

        switch (item.getPay_state()){
            case 0://待支付
            case 2://支付失败
                switch (item.getState()){
                    case 2://交易关闭
                        holder.setText(R.id.app_status, "交易关闭");
                        holder.setText(R.id.confirm, "删除订单");
                        holder.getTextView(R.id.cancel).setVisibility(View.GONE);
                        break;
                    default:
                        holder.setText(R.id.app_status, "待支付");
                        holder.setText(R.id.confirm, "立即支付");
                        holder.setText(R.id.cancel, "取消订单");
                        break;
                }
                break;
            case 1://支付成功
                switch (item.getState()){
                    case 0://支付成功
                        holder.setText(R.id.app_status, "支付成功");
                        holder.getTextView(R.id.confirm).setVisibility(View.GONE);
                        break;
                    case 1:
                        holder.setText(R.id.app_status, "交易完成");
                        holder.setText(R.id.confirm, "立即评价");
                        break;
                    case 4:
                        holder.setText(R.id.app_status, "已发货");
                        holder.setText(R.id.confirm, "确认收货");
                        break;
                    case 5:
                        holder.setText(R.id.app_status, "确认收货");
                        holder.setText(R.id.confirm, "立即评价");
                        break;
                    default:
                        holder.getTextView(R.id.confirm).setVisibility(View.GONE);
                        break;
                }
                holder.getTextView(R.id.cancel).setVisibility(View.GONE);
                break;
        }
        ImageHelper.getInstance().displayDefinedImage(item.getProduct_image_url(), holder.getImageView(R.id.app_img),
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        holder.setText(R.id.app_title, item.getProduct_name());
        holder.setText(R.id.app_attr_name, "选择分类：" + item.getAttr_name());
        holder.setText(R.id.app_price, "¥" + AppUtils.parseDouble(item.getOrder_price()/100));
        holder.getTextView(R.id.app_no_price).getPaint()
                .setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰
        holder.setText(R.id.app_no_price, "¥" + AppUtils.parseDouble(item.getAttr_price_old()/100));
        holder.setText(R.id.app_num, "x" + item.getProduct_num());
        holder.setText(R.id.app_address, item.getProvince_name() + " " + item.getCity_name() + " " +
        item.getArea_name() + " " + item.getAddress());

        holder.setClickListener(R.id.confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
        holder.setClickListener(R.id.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
    }
}