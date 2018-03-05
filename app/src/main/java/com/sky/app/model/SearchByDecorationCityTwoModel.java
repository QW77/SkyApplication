package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.SearchDecorationCity;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.SearshDecorationLongAndlatitude;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.SearchByDecorationCityTwoPresenter;
import com.sky.app.ui.activity.search.SearchShopUser;



public class SearchByDecorationCityTwoModel extends BaseModel<SearchByDecorationCityTwoPresenter> implements UserContract.ISearchByDecorationCityTwoModel {


    public SearchByDecorationCityTwoModel(Context context, SearchByDecorationCityTwoPresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void getData(String id) {
        SearchDecorationCity searchDecorationCity = new SearchDecorationCity();
        searchDecorationCity.setTwo_dir_id(id);
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchDecorationCity(searchDecorationCity),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        DecorationCityList categoryList = new Gson().fromJson(success, DecorationCityList.class);
                        getPresenter().success(categoryList);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().getView().showError(error);
                    }
                }));
    }

    /**
     * 请求装饰城中的活动
     *
     * @param id
     */
    @Override
    public void requestZSCAction(String id) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestZSCAction(id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {

                        BannerOut bannerOut = new Gson().fromJson(success, BannerOut.class);
                        getPresenter().showBannersuccess(bannerOut);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));


    }


    //装饰城中的二级分类,一进入装饰城就会进行这个请求,获取左手边总的数据类型
    @Override
    public void getSearchTwo(String decorative_id) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).searchDecorationTwo(decorative_id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        SearchDecorationTwoLeft list = new Gson().fromJson(success, SearchDecorationTwoLeft.class);
                        getPresenter().showDecorationTwoLeft(list);

                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }


    //请求精品店的数据
    @Override
    public void requestBoutique(SearchUser searchUser) {
//        RequestBoutiqueBean requestBoutiqueBean = new RequestBoutiqueBean();
//        requestBoutiqueBean.setTp(searchUser.getTp());
//        requestBoutiqueBean.setDecorative_id(searchUser.getDecorative_id());
//        requestBoutiqueBean.setUser_level_recommend(searchUser.getUser_level_recommend());
//        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
//                HttpUtils.getInstance(context).createApi(ApiService.class)
//                        .searchUser(searchUser),
////                     .searchUserDecoration(requestBoutiqueBean.getDecorative_id(),requestBoutiqueBean.getUser_level_recommend()),
//                new HttpUtils.IHttpCallBackListener() {
//                    @Override
//                    public void onSuccess(String success) {
//                        Log.e("123456_requestBoutique", success);
//                        DecorationTwoButique decorationTwoButique = new Gson().fromJson(success, DecorationTwoButique.class);
//                        getPresenter().showBoutiquesuccess(decorationTwoButique);
//                    }
//
//                    @Override
//                    public void onFailure(String error) {
//                        getPresenter().showError(error);
//                    }
//                }));
    }

    @Override
    public void requestBanner(String id) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestZSCBanner(id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        BannerOut bannerOut = new Gson().fromJson(success, BannerOut.class);
                        getPresenter().showBannersuccess(bannerOut);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    //请求装饰城中的商家头条
    @Override
    public void requestDecorationHeadlines(String id) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestDecorationHeadlines(id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        DecorationHeadlinearDetai[] headlinears = new Gson().fromJson(success, DecorationHeadlinearDetai[].class);
                        getPresenter().showDecorationHeadSuccess(headlinears);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));


    }

    /**
     * 请求装饰城的经纬度
     *
     * @param id
     */
    @Override
    public void requestLatitudeAndLong(String id) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).searchLatitudeAndLong(id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        SearshDecorationLongAndlatitude longAndlatitude = new Gson().fromJson(success, SearshDecorationLongAndlatitude.class);
                        getPresenter().showDecorationLongAndLatitude(longAndlatitude);

                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));


    }

    //获取装饰城中左边的分类,点击左边的分类,出现数据集合
    @Override
    public void getUserData(final SearchShopUser searchUser) {
        String decorative_id = searchUser.getDecorative_id();
        final String two_dir_id = searchUser.getTwo_dir_id();
        int page = searchUser.getPage();
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchUserShopDecoration(decorative_id, two_dir_id, null, page),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBeanList categoryList = new Gson().fromJson(success, UserBeanList.class);
                        getPresenter().userDataSuccess(categoryList);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));


    }
}
