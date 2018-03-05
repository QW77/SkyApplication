package com.sky.shop.adaptor;

import android.content.Context;
import android.view.View;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.shop.R;
import com.sky.shop.bean.AccountBean;

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
        holder.setText(R.id.driver_username_tv, item.getBank_id());
        holder.setText(R.id.driver_type_tv, "提现类型：" + ("1".equals(item.getBank_type()) ? "银行提现" : "未知"));
        holder.setText(R.id.driver_mode_tv, "提现方式：" + item.getBank_name());
        holder.setText(R.id.driver_bank_name_tv, "支行名称：" + item.getBank_name_sub());
        holder.setText(R.id.drvier_bank_no_tv, "银行账号：" + item.getBank_no());
        holder.setText(R.id.drvier_user_name, "用户姓名：" + item.getBank_user_name());
        holder.setText(R.id.driver_remark_tv, "备注：" + (null == item.getRemark() ? "" : item.getRemark()));
        holder.setClickListener(R.id.driver_del_tv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });

        if (1 == item.getState()){//审核通过
            holder.setText(R.id.driver_status_tv, "审核通过");
            holder.getTextView(R.id.driver_status_tv).setTextColor(
                    AppUtils.getSystemColor(mContext, R.color.sky_color_87d395));
        }else{
            holder.setText(R.id.driver_status_tv, "审核未通过");
            holder.getTextView(R.id.driver_status_tv).setTextColor(
                    AppUtils.getSystemColor(mContext, R.color.sky_color_808080));
        }
    }
}