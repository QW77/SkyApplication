package com.sky.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.SearchDecorationTwoLeft;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class PublishDetailHeadlinearAdapter extends BaseAdapter implements View.OnClickListener {
   private int selectItem = -1;

    private List<HeadlinearsDetail>acountList;
    private Context context;
    private LayoutInflater mInflater=null;

    public PublishDetailHeadlinearAdapter(List<HeadlinearsDetail> acountList, Context context) {
        this.acountList = acountList;
        this.context = context;
        this.mInflater=LayoutInflater.from(context);
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
       ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.app_headlinear_publish_item, null);
            holder.publishHeadlinearTitle = (TextView) convertView.findViewById(R.id.app_title_tv);
            holder.publishHeadlinearTime = (TextView) convertView.findViewById(R.id.app_time);
            holder.publishHeadlinearDesc = (TextView) convertView.findViewById(R.id.app_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.publishHeadlinearTitle.setText(acountList.get(position).getNoticetitle());
        holder.publishHeadlinearTime.setText(acountList.get(position).getCreateTime());
        holder.publishHeadlinearDesc.setText(acountList.get(position).getNoticeContent());

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }
    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }


    static class ViewHolder {
        public TextView publishHeadlinearTitle;
        public TextView publishHeadlinearTime;
        public TextView publishHeadlinearDesc;

    }



}
