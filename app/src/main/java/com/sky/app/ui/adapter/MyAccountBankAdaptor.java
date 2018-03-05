package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.AccountBean;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import java.util.List;

/**
 * 提现账户
 * RecyclerView通用适配器
 */
public class MyAccountBankAdaptor extends BaseRecyclerAdapter<AccountBean> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<AccountBean> list;

    public MyAccountBankAdaptor(Context context, List<AccountBean> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_account_list_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, AccountBean item) {
        holder.setText(R.id.app_username_tv, item.getBank_user_name());
        holder.setText(R.id.app_type_tv, "1".equals(item.getBank_type()) ? "银行提现" : "未知");
        holder.setText(R.id.app_mode_tv, "提现类型：" + item.getBank_name());
        holder.setText(R.id.app_bank_name_tv, "提现方式：" + item.getBank_name_sub());
        holder.setText(R.id.app_bank_no_tv, "支行名称：" + item.getBank_no());
        holder.setText(R.id.app_bank_no_tv, "银行账号：" + item.getBank_no());
        holder.setText(R.id.app_idcard_tv, "身份证号：" + item.getBank_id());
        holder.setText(R.id.app_mode_tv, "备注：" + "搭嘎嘎大哥");
        holder.setClickListener(R.id.app_del_tv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });

        if (0 == item.getState()){//审核通过
            holder.setText(R.id.app_status_tv, "审核通过");
            holder.getTextView(R.id.app_status_tv).setTextColor(
                    AppUtils.getSystemColor(mContext, R.color.sky_color_87d395));
        }else{
            holder.setText(R.id.app_status_tv, "审核未通过");
            holder.getTextView(R.id.app_status_tv).setTextColor(
                    AppUtils.getSystemColor(mContext, R.color.sky_color_808080));
        }
    }
}