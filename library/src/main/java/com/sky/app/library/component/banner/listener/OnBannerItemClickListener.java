package com.sky.app.library.component.banner.listener;

import com.sky.app.library.component.banner.modle.BannerInfo;
import java.util.ArrayList;

/**
 * Banner Click
 *
 * @author Edwin.Wu
 * @version 2016/12/6 15:38
 * @since JDK1.8
 */
public interface OnBannerItemClickListener {
    /**
     * banner click
     *
     * @param index  subscript
     * @param banner bean
     */
    void onBannerClick(int index, ArrayList<BannerInfo> banner);
}
