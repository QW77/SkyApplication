package com.sky.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.Headlinears;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.RetDataBean;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.library.component.scroll_txt.BaseBannerAdapter;
import com.sky.app.library.component.scroll_txt.VerticalBannerView;

import java.util.List;

public class ScrollTextAdaptor1 extends BaseBannerAdapter<HeadlinearsDetail> {

    private OnItemClick onItemClick;
    private List<HeadlinearsDetail> mDatas;

    public ScrollTextAdaptor1(List<HeadlinearsDetail> datas) {
        super(datas);
        mDatas=datas;
    }

    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.app_home_headlears_item, null);
    }


    @Override
    public void setItem(View view, final HeadlinearsDetail data) {
//       ((LinearLayout)view).setText(data.getNoticeContent());
        ((TextView)view).setText(data.getNoticetitle());

        //你可以增加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClick){
                    onItemClick.click(data.getId());
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