package com.sky.app.contract;

import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.Goods;
import com.sky.app.bean.HeadLinearsIn;
import com.sky.app.bean.Headlinears;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.bean.SupplyIn;
import com.sky.app.bean.SupplyOut;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.app.library.component.banner.modle.BannerInfo;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 商品契约类
 */

public class HomeContract {
    /**
     * 首页更新UI方法
     */
    public interface IHomeView extends IBaseView {
        void showBannerSuccess(List<BannerInfo> list, int flag);

        void showFirstCategorySuccess(List<FirstCategoryDetail> list);

        void showSupplySuccess(List<SupplyDetail> list);

        void showBuySuccess(List<SupplyDetail> list);

        void showHeadlinearsSuccess(HeadlinearsDetail[] headlinearsDetail);

        void showGoodShop(List<GoodShop.ListBean> goodList);

        void showGoodsSuccess(List<Goods.ListBean> list);
    }

    /**
     * 首页处理业务逻辑
     */
    public interface IHomePresenter extends IBasePresenter {
        void requestBanner();

        void requestBanner2();

        void requestFirstCategory();

        void showBannerSuccess(BannerOut bannerOut);

        void showBanner2Success(BannerOut bannerOut);

        void showFirstCategorySuccess(FirstCategoryOut firstCategoryOut);

        void requestSupply();

        void requestHeadlines();

        void requestBuy();

        void showSupplySuccess(SupplyOut supplyOut);

        void showBuySuccess(SupplyOut supplyOut);

        void showHeadlinearsSuccess(HeadlinearsDetail[] headlinearsDetail);

        void showGoodShop(GoodShop goodShops);

        void requestGoodShop(int i);

        void requestGoods(int i);

        void showGoodsSuccess(Goods goods);
    }

    /**
     * 首页网络请求
     */
    public interface IHomeModel extends IBaseModel {
        void requestBanner(BannerIn bannerIn, int flag);

        void requestFirstCategory();

        void requestSupply(SupplyIn supplyIn, int flag);

        void requestHeadlines();

        void requestGoodShop(int i);

        void requestGoods(int i);
    }
}