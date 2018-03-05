package com.sky.app.ui.activity.ask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.GoodShop;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

/**
 * 查找适配器-装饰城,生产厂家,区域
 * Created by hongbang on 2017/4/25.
 */

public class MyAskAdapter extends BaseAdapter {
    private List<MyAsk.ListBean> acountList;
    private Context context;
    private LayoutInflater mInflater = null;

    public MyAskAdapter(List<MyAsk.ListBean> acountList, Context context) {
        this.acountList = acountList;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return acountList.size();
    }

    @Override
    public Object getItem(int position) {
        return acountList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_my_ask, null);
            viewHolder.img1 = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.name1 = (TextView) convertView.findViewById(R.id.my_ask_username);
            viewHolder.time = (TextView) convertView.findViewById(R.id.my_ask_time);
            viewHolder.content = (TextView) convertView.findViewById(R.id.my_ask_content);
            viewHolder.prise = (TextView) convertView.findViewById(R.id.my_ask_prise);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageHelper.getInstance().displayDefinedImage(acountList.get(position).getPic_url(), viewHolder.img1,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        viewHolder.name1.setText(acountList.get(position).getUser_id());
        viewHolder.time.setText(acountList.get(position).getCreate_time());
        viewHolder.content.setText(acountList.get(position).getEval_comments());
        viewHolder.prise.setText(acountList.get(position).getPraise_num());
        return convertView;
    }


    /**
     * 视图
     */
    static class ViewHolder {
        public ImageView img1;
        public TextView name1;
        public TextView time;
        public TextView content;
        public TextView prise;
    }
}
