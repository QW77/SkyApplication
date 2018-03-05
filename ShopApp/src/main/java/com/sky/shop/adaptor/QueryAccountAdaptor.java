package com.sky.shop.adaptor;

import android.content.Context;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.shop.R;
import com.sky.shop.bean.ApplyRecordBean;

import java.util.List;

/**
 * RecyclerView通用适配器
 */
public class QueryAccountAdaptor extends BaseRecyclerAdapter<ApplyRecordBean> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<ApplyRecordBean> list;

    public QueryAccountAdaptor(Context context, List<ApplyRecordBean> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_account_query_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, ApplyRecordBean item) {
        holder.getTextView(R.id.driver_order_no_tv).setText("提现单号：" + item.getWithdrawal_id());
        holder.getTextView(R.id.driver_date_tv).setText(item.getCreate_time());
        holder.getTextView(R.id.driver_username_tv).setText(item.getBank_user_name());

        String state = "";
        switch (item.getState()){
            case 0:
                state = "待处理";
                break;
            case 1:
                state = "交易完成";
                break;
            case 2:
                state = "交易失败";
                break;
        }
        holder.getTextView(R.id.driver_status_tv).setText(state);
        holder.getTextView(R.id.driver_with_money).setText("提现金额：" + AppUtils.parseDouble(item.getWithdrawal_money()));
        holder.getTextView(R.id.driver_actual_money).setText("到账金额：" + AppUtils.parseDouble(item.getActual_money()));
        holder.getTextView(R.id.driver_bank_name).setText("银行名称：" +
                (null == item.getBank_name() ? "" : item.getBank_name()));
        holder.getTextView(R.id.driver_poundage_money).setText("手续费：" + AppUtils.parseDouble(item.getPoundage_money()));
        holder.getTextView(R.id.remark).setText("备注：" + (null == item.getRemark() ? "" : item.getRemark()));
    }
}