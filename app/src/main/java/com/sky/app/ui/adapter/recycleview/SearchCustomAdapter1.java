package com.sky.app.ui.adapter.recycleview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.bean.UserBeanList1;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.L;

/**
 * 通过区域查找,用户的
 * Created by hongbang on 2017/4/25.
 */

public class SearchCustomAdapter1 extends BaseAdapter {

    private UserBeanList1 userBeanList;

    private Context context;


    public SearchCustomAdapter1(UserBeanList1 userBeanList, Context context) {
        this.userBeanList = userBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userBeanList.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return userBeanList.getList().get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        UserBeanDetail userBean=userBeanList.getList().get(position);
        UserBeanList1.ListBean userBean = userBeanList.getList().get(position);
        UserHodler userHodler = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_second_search, null);
            userHodler = new UserHodler();

            userHodler.userHead = (ImageView) convertView.findViewById(R.id.imageView);
            userHodler.userName = (TextView) convertView.findViewById(R.id.search_name);
            userHodler.userPrice = (TextView) convertView.findViewById(R.id.search_price);
            convertView.setTag(userHodler);
        } else {
            userHodler = (UserHodler) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(userBean.getProduct_image_url(), userHodler.userHead,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        userHodler.userName.setText(userBean.getProduct_name());
        userHodler.userPrice.setText("¥" + AppUtils.parseDouble(userBean.getProduct_price_now()/100));

//        double aDouble = Double.valueOf(userBean.getNum_collect());
//        int aDouble1 = (int) aDouble;
//        userHodler.starNumber.setText(aDouble1 + "");
//        if (0 == userBean.getOther_flag()) {
//            userHodler.app_recommend_icon.setVisibility(View.GONE);
//        } else {
//            userHodler.app_recommend_icon.setVisibility(View.VISIBLE);
//        }
        return convertView;

    }

    class UserHodler {

        ImageView userHead;
        TextView userName;
        TextView userPrice;
    }


    //刷新适配器,追加
    public void appendData(UserBeanList1 userBeanList) {
        this.userBeanList.getList().addAll(userBeanList.getList());
        L.msg("数据==>" + this.userBeanList.getList().size());
        notifyDataSetChanged();
    }

    //刷新适配器,重新填充
    public void refreshData(UserBeanList1 userBeanList) {
        this.userBeanList.getList().clear();
        this.userBeanList.getList().addAll(userBeanList.getList());
        notifyDataSetChanged();
    }
}
