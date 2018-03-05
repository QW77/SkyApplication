package com.sky.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.library.component.scroll_txt.BaseBannerAdapter;
import com.sky.app.library.component.scroll_txt.VerticalBannerView;

import java.util.List;

public class ScrollTextAdaptor2 extends BaseBannerAdapter<SupplyDetail> {

    private OnItemClick onItemClick;
    private List<SupplyDetail> mDatas;

    public ScrollTextAdaptor2(List<SupplyDetail> datas) {
        super(datas);
    }

    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.app_home_supply_item, null);
    }

    @Override
    public void setItem(final View view, final SupplyDetail data) {
        ((TextView)view).setText(data.getProduct_name());
        //你可以增加点击事件
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != onItemClick){
                    onItemClick.click(data.getProduct_id());
                }
            }
        });
    }

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void click(String s);
    }
}