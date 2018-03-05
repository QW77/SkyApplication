package com.sky.shop.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.shop.R;
import com.sky.shop.bean.ProductAttribute;

import java.util.List;

/**
 * 商品价格列表
 */
public class PriceAttrAdapter extends BaseAdapter {
    private List<ProductAttribute> productAttributes;

    private OnClickListener onClickListener;
    private Context context;

    public PriceAttrAdapter(Context context, List<ProductAttribute> productAttribute, OnClickListener onClickListener) {
        this.productAttributes = productAttribute;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return productAttributes.size();
    }

    @Override
    public Object getItem(int position) {
        return productAttributes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();

            //根据自定义的Item布局加载布局
            convertView = LayoutInflater.from(context).inflate(R.layout.app_add_product_item, null);
            holder.app_attr_name = (EditText) convertView.findViewById(R.id.app_attr_name);
            holder.app_price = (EditText) convertView.findViewById(R.id.app_price);
            holder.app_num = (EditText) convertView.findViewById(R.id.app_num);
            holder.del = (TextView) convertView.findViewById(R.id.del);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
        holder.app_attr_name.setText(productAttributes.get(position).getAttr_name());
        holder.app_price.setText(productAttributes.get(position).getAttr_price_now() + "");
        holder.app_num.setText(productAttributes.get(position).getInventory_num() + "");
        return convertView;
    }

    /**
     * 视图
     */
    static class ViewHolder {
        public EditText app_attr_name;
        public EditText app_price;
        public EditText app_num;
        public TextView del;
    }

    public interface OnClickListener{
        void onClick(View v, int pos);
    }
}
