package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.L;

import java.util.List;

/**
 * 通过区域查找,用户的
 * Created by hongbang on 2017/4/25.
 */

public class SearchCustomButiqueAdapter extends BaseAdapter {

    private List<DecorationTwoButique.ListBean> list;

    private Context context;


    public SearchCustomButiqueAdapter(List<DecorationTwoButique.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DecorationTwoButique.ListBean listBean = list.get(position);
        ViewHolder ViewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_search_by_space_bottom, null);
            ViewHolder = new ViewHolder();
            ViewHolder.userHead = (ImageView) convertView.findViewById(R.id.imageView);
            ViewHolder.userName = (TextView) convertView.findViewById(R.id.name);
            ViewHolder.zhuYing = (TextView) convertView.findViewById(R.id.zhuying);
            ViewHolder.address = (TextView) convertView.findViewById(R.id.address);
//            ViewHolder.starNumber = (TextView) convertView.findViewById(R.id.starnumber);
//            ViewHolder.app_recommend_icon = (ImageView) convertView.findViewById(R.id.app_recommend_icon);
            convertView.setTag(ViewHolder);
        } else {
            ViewHolder = (ViewHolder) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(list.get(position).getPic_url(), ViewHolder.userHead,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);

        ViewHolder.userName.setText(list.get(position).getNick_name());
        ViewHolder.zhuYing.setText(list.get(position).getMain_business_desc());
        ViewHolder.address.setText(list.get(position).getAddress());
//        double aDouble = Double.valueOf(list.get(position).getNum_collect());
//        int aDouble1 = (int) aDouble;
//        ViewHolder.starNumber.setText(aDouble1 + "");
//
//        if (0 == list.get(position).getOther_flag()) {
//            ViewHolder.app_recommend_icon.setVisibility(View.GONE);
//        } else {
//            ViewHolder.app_recommend_icon.setVisibility(View.VISIBLE);
//        }
        return convertView;

    }

    static class ViewHolder {

        ImageView userHead;
        TextView userName;
        TextView zhuYing;
        TextView address;
//        TextView starNumber;
//        ImageView app_recommend_icon;
    }

    //刷新适配器,追加
    public void appendData(DecorationTwoButique decorationTwoButique) {

       this.list.addAll(decorationTwoButique.getList());
        L.msg("数据==>" + list.size());
        notifyDataSetChanged();
    }

    //刷新适配器,重新填充
    public void refreshData(DecorationTwoButique decorationTwoButique) {
        this.list.clear();
        this.list.addAll(decorationTwoButique.getList());
        notifyDataSetChanged();
    }

}
