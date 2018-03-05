package com.sky.shop.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.shop.bean.AreaList;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.CollectGoods;
import com.sky.shop.bean.CollectGoodsList;
import com.sky.shop.bean.DecorationCityList;
import com.sky.shop.bean.FirstCategoryOut;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductCategoryOut;
import com.sky.shop.bean.ProductIn;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.ProductList;
import com.sky.shop.bean.ProductResponse;
import com.sky.shop.bean.ProductUpAndDownIn;
import com.sky.shop.bean.SearchDecorationCity;
import com.sky.shop.bean.SearchProductRequest;
import com.sky.shop.bean.SellMessageComplete;
import com.sky.shop.bean.ShopProductDetail;
import com.sky.shop.bean.ShopProductIn;
import com.sky.shop.bean.ShopProductOut;
import com.sky.shop.bean.UserBean;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 商品契约类
 */

public class ShopContract {

    /**
     * 我的卖家中心商铺视图
     */
    public interface IMineShopCenterView extends IBaseView {
        void responseUserInfo(UserBean userBean);
    }

    /**
     * 我的卖家中心商铺控制器
     */
    public interface IMineShopCenterPresenter extends IBasePresenter {
        void queryUserInfo();

        void responseUserInfo(UserBean userBean);
    }

    /**
     * 我的卖家中心商铺数据请求
     */
    public interface IMineShopCenterModel extends IBaseModel {
        void queryUserInfo();
    }

    /**
     * 商铺装潢视图
     */
    public interface IShopDecorateView extends IBaseView {
    }

    /**
     * 商铺装潢控制器
     */
    public interface IShopDecoratePresenter extends IBasePresenter {
    }

    /**
     * 商铺装潢数据请求
     */
    public interface IShopDecorateModel extends IBaseModel {
    }

    /**
     * 首页更新UI方法
     */
    public interface ISellerSecondCategory extends IBaseView {
        void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut);

        void areaSuccess(AreaList areaList);

        void successDecrationCity(DecorationCityList categoryList);

        void secondCatogoryDataSuccess(CategoryList categoryList);
    }

    /**
     * 首页处理业务逻辑
     */
    public interface ISellerSecondCategoryPresenter extends IBasePresenter {
        void getArea();

        void areaSuccess(AreaList areaList);

        void getSecondCatogoryData(String firstId);

        void secondCatogoryDataSuccess(CategoryList categoryList);

        void getDecrationCity(String id);

        void successDecrationCity(DecorationCityList categoryList);
    }

    /**
     * 首页网络请求
     */
    public interface ISellerSecondCategoryModel extends IBaseModel {
        void getArea();

        void getSecondCatogoryData(String firstId);

        void getDecrationCity(String id);
    }


    /**
     * 首页更新UI方法
     */
    public interface ISellerFirstCategory extends IBaseView {
        void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut);
    }

    /**
     * 首页处理业务逻辑
     */
    public interface ISellerFirstCategoryPresenter extends IBasePresenter {
        void getFirstCatogoryData();

        void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut);
    }

    /**
     * 首页网络请求
     */
    public interface ISellerFirstCategoryModel extends IBaseModel {
        void getFirstCatogoryData();
    }

    /**
     * UI方法
     */
    public interface ISearchByPlace extends IBaseView {
        void success(AreaList areaList);

        void successDecrationCity(DecorationCityList categoryList);

        void secondCatogoryDataSuccess(CategoryList categoryList);

        void responseCompanyType(CategoryList categoryList);

        void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut);

    }

    /**
     * 搜索地址业务逻辑
     */
    public interface ISearchByPlacePresenter extends IBasePresenter {
        void getDecrationCity(String id);

        void successDecrationCity(DecorationCityList categoryList);

        void getData();

        void success(AreaList areaList);

        void getSecondCatogoryData(String firstId);

        void secondCatogoryDataSuccess(CategoryList categoryList);

        void requestCompanyType();

        void responseCompanyType(CategoryList categoryList);

        void getFirstCatogoryData();

        void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut);
    }

    /**
     * 搜索地址
     */
    public interface ISearchByPlaceModel extends IBaseModel {
        void getFirstCatogoryData();

        void getDecrationCity(String id);

        void getData();

        void getSecondCatogoryData(String firstId);

        void requestCompanyType();
    }

    /**
     * UI方法
     */
    public interface IUploadView extends IBaseView {

        void getFirstImageUrl(String url);

        void getSecondImageUrl(String url);

        void registerSuccess(UserBean userBean);
    }

    /**
     * 个人中心处理业务逻辑
     */
    public interface IUploadPresenter extends IBasePresenter {
        void uploadFirstFile(String url);

        void uploadSecondFile(String url);

        void sellRegister(SellMessageComplete sellMessageComplete);

        void registerSuccess(UserBean userBean);
    }

    public interface IUploadModel extends IBaseModel {
        void sellRegister(SellMessageComplete sellMessageComplete);
    }

    /**
     * 所有商品
     */
    public interface ISearchShopView extends IBaseView {
        void getRefreshData(List<CollectGoods> list);

        void getLoadMoreData(List<CollectGoods> list);
    }

    /**
     * demo处理业务逻辑
     */
    public interface ISearchShopPresenter extends IBasePresenter {
        void loadMore(SearchProductRequest searchProductRequest);

        void loadData(SearchProductRequest searchProductRequest);

        boolean hasMore();

        void refreshData(CollectGoodsList collectGoodsList);

        void loadMoreData(CollectGoodsList collectGoodsList);
    }

    /**
     * 网络请求
     */
    public interface ISearchShopModel extends IBaseModel {
        void getProducts(SearchProductRequest searchProductRequest, int flag);

    }

    /**
     * 商铺视图
     */
    public interface IShopCenterView extends IBaseView {
        void showHomeResponse(List<ShopProductDetail> list);

        void getRefreshData(List<ProductResponse> list);

        void getLoadMoreData(List<ProductResponse> list);

        void showDelSuccess(String msg);

        void responseUpAndDownProduct(String msg);

        void responseProductCategory(List<ProductCategory> productCategories);
    }

    /**
     * 商铺控制器
     */
    public interface IShopCenterPresenter extends IBasePresenter {
        void requestHomeData(ShopProductIn shopProductIn);

        void responseHomeData(ShopProductOut shopProductOut);

        void loadMore(SearchProductRequest searchProductRequest);

        void loadData(SearchProductRequest searchProductRequest);

        boolean hasMore();

        void refreshData(ProductList productList);

        void loadMoreData(ProductList productList);

        void delProduct(ProductIn productIn);

        void responseDelProduct(String msg);

        void upAndDownProduct(ProductUpAndDownIn productUpAndDownIn);

        void responseUpAndDownProduct(String msg);

        void requestProductCategory(ProductCategoryIn productCategoryIn);

        void responseProductCategory(ProductCategoryOut productCategoryOut);
    }

    /**
     * 商铺数据请求
     */
    public interface IShopCenterModel extends IBaseModel {
        void requestHomeData(ShopProductIn shopProductIn);

        void requestAllProduct(SearchProductRequest searchProductRequest, int flag);

        void delProduct(ProductIn productIn);

        void upAndDownProduct(ProductUpAndDownIn productUpAndDownIn);

        void requestProductCategory(ProductCategoryIn productCategoryIn);
    }

    /**
     * 商铺简介视图
     */
    public interface IProductIntroduceView extends IBaseView {
        void showSuccess(ProductIntroduceOut productIntroduceOut);

        void responseShopInfo(String msg);
    }

    /**
     * 商铺简介控制器
     */
    public interface IProductIntroducePresenter extends IBasePresenter {
        void requestDescData();

        void responseDescData(ProductIntroduceOut productIntroduceOut);

        void requestShopIntroduce(ProductIntroduceOut productIntroduceOut);

        void responseShopInfo(String msg);

       void postlonlat(double longitude, double latitude);

    }

    /**
     * 商铺简介数据请求
     */
    public interface IProductIntroduceModel extends IBaseModel {
        void requestDescData();

        void requestShopIntroduce(ProductIntroduceOut productIntroduceOut);

        void postlonlat(double longitude, double latitude);
    }

    /**
     * 目录视图
     */
    public interface ICategoryView extends IBaseView {
        void responseProductCategory(List<ProductCategory> productCategories);

        void responseAddFirstCategory(String msg);

        void responseDelSuccess(String msg);

        void responseUpdateSuccess(String msg);
    }

    /**
     * 目录控制器
     */
    public interface ICategoryPresenter extends IBasePresenter {
        void requestProductCategory(ProductCategoryIn productCategoryIn);

        void responseProductCategory(ProductCategoryOut productCategoryOut);

        void responseAddFirstCategory(String msg);

        void requestAddFirstCategory(ProductCategory productCategory);

        void responseDelSuccess(String msg);

        void requestDelCategory(ProductCategory productCategory);

        void responseUpdateSuccess(String msg);

        void requestUpdateCategory(ProductCategory productCategory);
    }

    /**
     * 目录数据请求
     */
    public interface ICategoryModel extends IBaseModel {
        void requestProductCategory(ProductCategoryIn productCategoryIn);

        void requestAddFirstCategory(ProductCategory productCategory);

        void requestDelCategory(ProductCategory productCategory);

        void requestUpdateCategory(ProductCategory productCategory);
    }

    /**
     * 商户资料视图
     */
    public interface IShopDataView extends IBaseView {
        void responseUserInfo(UserBean userBean);

        void responseUpdateShopInfo(String msg);

        void responseAreaList(AreaList areaList);

        void responseDecorateCity(DecorationCityList decorationCityList);

        void responseCompanyType(CategoryList categoryList);
    }

    /**
     * 商户资料控制器
     */
    public interface IShopDataPresenter extends IBasePresenter {
        void queryUserInfo();

        void responseUserInfo(UserBean userBean);

        void updateShopInfo(ProductIntroduceOut productIntroduceOut);

        void responseUpdateShopInfo(String msg);

        void responseAreaList(AreaList areaList);

        void requestAreaList();

        void requestDecorateCity(SearchDecorationCity searchDecorationCity);

        void responseDecorateCity(DecorationCityList decorationCityList);

        void responseCompanyType(CategoryList categoryList);

        void requestCompanyType();
    }

    /**
     * 商户资料数据请求
     */
    public interface IShopDataModel extends IBaseModel {
        void queryUserInfo();

        void updateShopInfo(ProductIntroduceOut productIntroduceOut);

        void requestAreaList();

        void requestDecorateCity(SearchDecorationCity searchDecorationCity);

        void requestCompanyType();
    }
}