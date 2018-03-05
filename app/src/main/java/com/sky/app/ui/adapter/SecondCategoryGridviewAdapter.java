package com.sky.app.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.Category;
import com.sky.app.library.utils.ImageHelper;

import java.util.List;

import static com.umeng.socialize.utils.Log.TAG;

/**
 * 二级界面目录
 * Created by hongbang on 2017/4/25.
 */

public class SecondCategoryGridviewAdapter extends BaseAdapter implements View.OnClickListener {
    private int selectItem = -1;
    private List<Category> acountList;

    private Context context;
    private LayoutInflater mInflater = null;
    private LinearLayout linear;


    public SecondCategoryGridviewAdapter(List<Category> acountList, Context context) {
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.item_firstcategory, null);
            holder.category_name1 = (TextView) convertView.findViewById(R.id.category_name1);
            holder.onell = (LinearLayout) convertView.findViewById(R.id.one_ll);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.category_name1.setText(acountList.get(position).getTwo_dir_name());

        if (position == selectItem) {
            holder.category_name1.setTextColor(Color.rgb(35, 154, 237));
            holder.onell.setBackgroundColor(Color.WHITE);
        } else {
            holder.category_name1.setTextColor(Color.BLACK);
            holder.onell.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 视图
     */
    static class ViewHolder {
        public TextView category_name1;
        public LinearLayout onell;
    }
}
