package com.sky.shop.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.shop.R;

import java.util.List;

/**
 * 商品图片
 */

public class ProductImageGridviewAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public ProductImageGridviewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size() + 1;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View pic = View.inflate(context, R.layout.app_product_image_item, null);
        ImageView addIcon = (ImageView) pic.findViewById(R.id.add_icon);
        TextView delIcon = (TextView) pic.findViewById(R.id.del_icon);
        if (getCount() == 1) {
            addIcon.setBackgroundResource(R.mipmap.app_default_icon);//最后一个显示加号图片
            delIcon.setText("上传");
            delIcon.setTextColor(AppUtils.getSystemColor(context, R.color.main_colorPrimary));
            AppUtils.changeTextViewIcon(context, delIcon, R.mipmap.app_upload_icon, 1);
        } else {
            if (getCount() - 1 == position) {
                addIcon.setBackgroundResource(R.mipmap.app_default_icon);//最后一个显示加号图片
                delIcon.setText("上传");
                delIcon.setTextColor(AppUtils.getSystemColor(context, R.color.main_colorPrimary));
                AppUtils.changeTextViewIcon(context, delIcon, R.mipmap.app_upload_icon, 1);
            }else{
                ImageHelper.getInstance().displayDefinedImage(list.get(position).toString(),
                        addIcon, R.mipmap.app_default_icon, R.mipmap.app_default_icon);
                delIcon.setText("删除");
                delIcon.setTextColor(AppUtils.getSystemColor(context, R.color.sky_color_79cf89));
                AppUtils.changeTextViewIcon(context, delIcon, R.mipmap.app_product_del_icon, 1);
            }
        }
        return pic;
    }
}
