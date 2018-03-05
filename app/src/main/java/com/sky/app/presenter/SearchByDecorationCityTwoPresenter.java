package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.BannerDetail;
import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.DecorationCity;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.SearshDecorationLongAndlatitude;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.model.SearchByDecorationCityTwoModel;
import com.sky.app.ui.activity.search.SearchShopUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 根据装饰城来搜索
 */

public class SearchByDecorationCityTwoPresenter extends BasePresenter<UserContract.ISearchByDecorationCityTwo>
        implements UserContract.ISearchByDecorationCityTwoPresenter {

    private UserContract.ISearchByDecorationCityTwoModel iSearchByDecorationCityTwoModel;

    /**
     * 构造
     */
    public SearchByDecorationCityTwoPresenter(Context context,
                                              UserContract.ISearchByDecorationCityTwo mIsearch) {
        super(context, mIsearch);
        this.iSearchByDecorationCityTwoModel = new SearchByDecorationCityTwoModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iSearchByDecorationCityTwoModel) {
            iSearchByDecorationCityTwoModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void getData(String id) {
        iSearchByDecorationCityTwoModel.getData(id);
    }

    //请求装饰城中的轮播图
    @Override
    public void requestBanner(String id) {
        iSearchByDecorationCityTwoModel.requestBanner(id);
    }

    //请求装饰城中的活动
    @Override
    public void requestZSCAction(String id) {
        iSearchByDecorationCityTwoModel.requestZSCAction(id);
    }

    //请求装饰城中的商家头条
    @Override
    public void requestDecorationHeadlines(String id) {
        iSearchByDecorationCityTwoModel.requestDecorationHeadlines(id);
    }

    //请求装饰城的经纬度
    @Override
    public void requestLatitudeAndLong(String id) {
        iSearchByDecorationCityTwoModel.requestLatitudeAndLong(id);

    }

    //展示装饰城的经纬度
    @Override
    public void showDecorationLongAndLatitude(SearshDecorationLongAndlatitude longAndlatitude) {
        getView().showDecorationLongAndLatitude(longAndlatitude);
    }

    //展示装饰城中的商家头条
    @Override
    public void showDecorationHeadSuccess(DecorationHeadlinearDetai[] headlinears) {
        getView().showDecorationHeadSuccess(headlinears);
    }
//
//    @Override
//    public void requestBanner(DecorationCity decorateCity) {
////        BannerIn bannerIn = new BannerIn();
////        bannerIn.setStart_type("11");
////        bannerIn.setEnd_type("14");
//        iSearchByDecorationCityTwoModel.requestBanner(decorateCity);
//    }

    @Override
    public void showBannersuccess(BannerOut bannerOut) {
        getView().showBannerSuccess(renderBanner(bannerOut));
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
    public void success(DecorationCityList categoryList) {
        getView().success(categoryList);
    }


    @Override
    public void getUserData(SearchShopUser searchUser) {
        getView().showProgress();
        iSearchByDecorationCityTwoModel.getUserData(searchUser);

    }

    //获取精品店的数据
    @Override
    public void requestBoutique(SearchUser searchUser) {
        iSearchByDecorationCityTwoModel.requestBoutique(searchUser);
    }

    //返回精品店的数据
    @Override
    public void showBoutiquesuccess(DecorationTwoButique decorationTwoButique) {
        List<DecorationTwoButique.ListBean> butiqueList = new ArrayList<>();
        for (DecorationTwoButique.ListBean f : decorationTwoButique.getList()) {
            butiqueList.add(f);
        }
        getView().showBoutiquesuccess(butiqueList);
    }

    @Override
    public void userDataSuccess(UserBeanList categoryList) {
        getView().userDataSuccess(categoryList);
        getView().hideProgress();
    }

    @Override
    public void getSearchTwo(String decorative_id) {
        iSearchByDecorationCityTwoModel.getSearchTwo(decorative_id);

    }

    @Override
    public void showDecorationTwoLeft(SearchDecorationTwoLeft list) {
        getView().showDecorationTwoLeft(list);

    }

    @Override
    public void showError(String error) {
        super.showError(error);
        getView().showError(error);
        getView().hideProgress();
    }
}