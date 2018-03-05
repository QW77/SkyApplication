package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.BannerDetail;
import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationPingMian;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.model.SearchByDecorationCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 根据装饰城来搜索
 */

public class SearchByDecorationCityPresenter extends BasePresenter<UserContract.ISearchByDecorationCity>
        implements UserContract.ISearchByDecorationCityPresenter {

    private UserContract.ISearchByDecorationCityModel iSearchByDecorationCityModel;

    /**
     * 构造
     */
    public SearchByDecorationCityPresenter(Context context,
                                           UserContract.ISearchByDecorationCity mIsearch) {
        super(context, mIsearch);
        this.iSearchByDecorationCityModel = new SearchByDecorationCityModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iSearchByDecorationCityModel) {
            iSearchByDecorationCityModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void getData(String id) {
        iSearchByDecorationCityModel.getData(id);
    }

    @Override
    public void requestBanner() {
        BannerIn bannerIn = new BannerIn();
        bannerIn.setStart_type("11");
        bannerIn.setEnd_type("14");
        iSearchByDecorationCityModel.requestBanner(bannerIn);
    }

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
    public void getUserData(SearchUser searchUser) {
        getView().showProgress();
        iSearchByDecorationCityModel.getUserData(searchUser);

    }

    //获取精品店的数据
    @Override
    public void requestBoutique(String id, int i) {
        iSearchByDecorationCityModel.requestBoutique(id, i);
    }

    @Override
    public void requestZSCPic(String id) {
        iSearchByDecorationCityModel.requestZSCPic(id);
    }

    @Override
    public void showPingPic(DecorationPingMian decorationPingMian) {
        List<DecorationPingMian.ListBean> pingList = new ArrayList<>();
        for (DecorationPingMian.ListBean f : decorationPingMian.getList()) {
            pingList.add(f);
        }
        getView().showPingPic(pingList);
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
    public void getSearchTwo() {
        iSearchByDecorationCityModel.getSearchTwo();

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