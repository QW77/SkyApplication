package com.sky.app.ui.adapter;

import android.content.Context;

import com.sky.app.R;
import com.sky.app.bean.ApplyRecordBean;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;

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
        holder.getTextView(R.id.app_order_no_tv).setText("提现单号：" + item.getWithdrawal_id());
        holder.getTextView(R.id.app_date_tv).setText(item.getCreate_time());
        holder.getTextView(R.id.app_username_tv).setText(item.getBank_user_name());

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
        holder.getTextView(R.id.app_status_tv).setText(state);
        holder.getTextView(R.id.app_with_money).setText("提现金额：" + item.getWithdrawal_money());
        holder.getTextView(R.id.app_actual_money).setText("到账金额：" + item.getActual_money());
        holder.getTextView(R.id.app_bank_name).setText("银行名称：" + item.getBank_name());
    }
}