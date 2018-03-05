package com.sky.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.SearchDecorationTwoLeft;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class SearchDecortionTwoAdapter extends BaseAdapter implements View.OnClickListener {
   private int selectItem = -1;

    private List<SearchDecorationTwoLeft.ListBean>acountList;
    private Context context;
    private LayoutInflater mInflater=null;

    public SearchDecortionTwoAdapter(List<SearchDecorationTwoLeft.ListBean> acountList, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_searchdecortiontwo, null);
            holder.decoration_name1 = (TextView) convertView.findViewById(R.id.decortiontwo_name1);
            holder.onel = (LinearLayout) convertView.findViewById(R.id.decortiontwo_l);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.decoration_name1.setText(acountList.get(position).getTwo_dir_name());
        if (position == selectItem) {
            holder.decoration_name1.setTextColor(Color.rgb(35, 154, 237));
            holder.onel.setBackgroundColor(Color.WHITE);
        } else {
            holder.decoration_name1.setTextColor(Color.BLACK);
            holder.onel.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {

    }
    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }


    static class ViewHolder {
        public TextView decoration_name1;
        public LinearLayout onel;
    }



}
