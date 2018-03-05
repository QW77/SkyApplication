package com.sky.shop.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sky.shop.R;
import com.sky.shop.bean.Category;

import java.util.List;

/**
 * Created by hongbang on 2017/4/25.
 */

public class SecondCategoryGridviewAdapter extends BaseAdapter {

    private List<Category> acountList;

    private Context context;

    public SecondCategoryGridviewAdapter(List<Category> acountList, Context context) {
        this.acountList = acountList;
        this.context = context;
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

        View pic = View.inflate(context, R.layout.item_firstcategory, null);
        TextView category_name1 = (TextView) pic.findViewById(R.id.category_name1);
//        ImageView pic_1 = (ImageView) pic.findViewById(R.id.pic_1);
//
//        ImageHelper.getInstance().displayDefinedImage(acountList.get(position).getIcon_image_url(), pic_1,
//                R.mipmap.category_default_icon, R.mipmap.category_default_icon);
        category_name1.setText(acountList.get(position).getTwo_dir_name());
        return pic;
    }
}
