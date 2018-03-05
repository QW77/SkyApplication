package com.sky.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.library.component.scroll_txt.BaseBannerAdapter;
import com.sky.app.library.component.scroll_txt.VerticalBannerView;

import java.util.List;

public class ScrollTextCityAdaptor extends BaseBannerAdapter<DecorationHeadlinearDetai> {

    private OnItemClick onItemClick;
    private List<DecorationHeadlinearDetai> mDatas;

    public ScrollTextCityAdaptor(List<DecorationHeadlinearDetai> datas) {
        super(datas);
        mDatas=datas;
    }

    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.app_home_headlears_item, null);
    }


    @Override
    public void setItem(View view, final DecorationHeadlinearDetai data) {
//       ((LinearLayout)view).setText(data.getNoticeContent());
        ((TextView)view).setText(data.getNoticeTitle());

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