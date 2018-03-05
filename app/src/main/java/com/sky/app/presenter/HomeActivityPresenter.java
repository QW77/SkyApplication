package com.sky.app.presenter;

import android.content.Context;
import android.util.Log;

import com.sky.app.bean.BannerDetail;
import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.Goods;
import com.sky.app.bean.Headlinears;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SupplyIn;
import com.sky.app.bean.SupplyOut;
import com.sky.app.contract.HomeContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 */
public class HomeActivityPresenter extends BasePresenter<HomeContract.IHomeView> implements HomeContract.IHomePresenter {
    private HomeContract.IHomeModel iHomeShopModel;

    /**
     * 构造
     */
    public HomeActivityPresenter(Context context, HomeContract.IHomeView iHomeView) {
        super(context, iHomeView);
        iHomeShopModel = new HomeModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iHomeShopModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void requestBanner() {
        BannerIn bannerIn = new BannerIn();
        bannerIn.setStart_type("11");
        bannerIn.setEnd_type("14");
        iHomeShopModel.requestBanner(bannerIn, 1);
    }

    @Override
    public void showBannerSuccess(BannerOut bannerOut) {
        getView().showBannerSuccess(renderBanner(bannerOut), 1);
    }

    @Override
    public void showBanner2Success(BannerOut bannerOut) {
        getView().showBannerSuccess(renderBanner(bannerOut), 2);
    }

    @Override
    public void showFirstCategorySuccess(FirstCategoryOut firstCategoryOut) {
        List<FirstCategoryDetail> categoryList = new ArrayList<>();
        for (FirstCategoryDetail f : firstCategoryOut.getList()) {
            categoryList.add(f);
        }
        getView().showFirstCategorySuccess(categoryList);
//        Log.i("123456HomeActivity",categoryList.toString());
    }

    @Override
    public void requestSupply() {
        SupplyIn supplyIn = new SupplyIn();
        supplyIn.setProduct_type("0");
        iHomeShopModel.requestSupply(supplyIn, 1);
    }

    @Override
    public void requestHeadlines() {
        iHomeShopModel.requestHeadlines();
    }

    @Override
    public void requestBuy() {
        SupplyIn supplyIn = new SupplyIn();
        supplyIn.setProduct_type("1");
        iHomeShopModel.requestSupply(supplyIn, 2);
    }

    @Override
    public void showSupplySuccess(SupplyOut supplyOut) {
        getView().showSupplySuccess(supplyOut.getList());
    }

    @Override
    public void showBuySuccess(SupplyOut supplyOut) {
        getView().showBuySuccess(supplyOut.getList());
    }

    @Override
    public void showHeadlinearsSuccess(HeadlinearsDetail[] headlinearsDetail) {
        getView().showHeadlinearsSuccess(headlinearsDetail);
    }

    @Override
    public void showGoodShop(GoodShop goodShop) {
        List<GoodShop.ListBean> goodList = new ArrayList<>();
        for (GoodShop.ListBean f : goodShop.getList()) {
           goodList.add(f);
        }
        getView().showGoodShop(goodList);
    }


    @Override
    public void requestGoodShop(int i) {
        iHomeShopModel.requestGoodShop(i);
    }

    @Override
    public void requestGoods(int i) {
        iHomeShopModel.requestGoods(i);
    }

    @Override
    public void showGoodsSuccess(Goods goods) {
        List<Goods.ListBean>goods1ist=new ArrayList<>();
        for (Goods.ListBean f:goods.getList()){
            goods1ist.add(f);
        }
        getView().showGoodsSuccess(goods1ist);
    }
    /**
     * 渲染广告
     *
     * @return
     */
    private List<BannerInfo> renderBanner(BannerOut bannerOut) {
        List<BannerDetail> detail = bannerOut.getList();
        List<BannerInfo> bannerInfo = new ArrayList<>();
        for (BannerDetail b : detail) {
            bannerInfo.add(new BannerInfo(b.getNews_title_image_url(), b.getNews_title(), b.getNews_title_image_jump_url()));
        }
        return bannerInfo;
    }

    @Override
    public void requestBanner2() {
        BannerIn bannerIn = new BannerIn();
        bannerIn.setStart_type("15");
        bannerIn.setEnd_type("18");
        iHomeShopModel.requestBanner(bannerIn, 2);
    }

    @Override
    public void requestFirstCategory() {
        iHomeShopModel.requestFirstCategory();
    }
}