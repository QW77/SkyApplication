package com.sky.app.contract;

import com.sky.app.bean.AskFilter;
import com.sky.app.bean.CollectGoods;
import com.sky.app.bean.CollectGoodsList;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.CommentList;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.CommentResponse;
import com.sky.app.bean.ProductCategory;
import com.sky.app.bean.ProductCategoryIn;
import com.sky.app.bean.ProductCategoryOut;
import com.sky.app.bean.ProductDeatilResponse;
import com.sky.app.bean.ProductDetailRequest;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.ProductList;
import com.sky.app.bean.ProductResponse;
import com.sky.app.bean.PubCommentIn;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.bean.ShopProductDetail;
import com.sky.app.bean.ShopProductIn;
import com.sky.app.bean.ShopProductOut;
import com.sky.app.bean.SingleOrderIn;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.app.ui.activity.ask.AskSecondComent;
import com.sky.app.ui.activity.ask.MyAnser;
import com.sky.app.ui.activity.ask.MyAsk;
import com.sky.app.ui.activity.seller.UserGPS;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 商品契约类
 */

public class ShopContract {

    /**
     * 商品详情
     */
    public interface IProductDetailView extends IBaseView {
        void productDetailSuccess(ProductDeatilResponse productList);

        void ProductCommentSuccess(CommentResponse commentResponse);

        void showAddResult(String msg);
    }

    /**
     * 商品详情
     */
    public interface IProductDetailPresenter extends IBasePresenter {
        void getProductDetail(ProductDetailRequest productDetailRequest);

        void productDetailSuccess(ProductDeatilResponse productList);

        void getProductComment(CommentRequest commentRequest);

        void ProductCommentSuccess(CommentList commentResponse);

        void addShoppingCar(SingleOrderIn singleOrderIn);

        void showAddResult(String msg);
    }

    /**
     * 商品详情
     */
    public interface IProductDetailModel extends IBaseModel {

        void getProductDetail(ProductDetailRequest productDetailRequest);

        void getProductComment(CommentRequest commentRequest);

        void addShoppingCar(SingleOrderIn singleOrderIn);
    }


    /**
     * 搜索商品更新UI方法
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
     * 收藏视图
     */
    public interface ICollectionView extends IBaseView {
        void showCollectView(String msg);

        void showCollectError(String error);
    }

    /**
     * 收藏控制器
     */
    public interface ICollectionPresenter extends IBasePresenter {
        void showCollectError(String error);

        void responseCollect(String msg);

        void requestCollect(CollectPubIn collectPubIn);

        void cancelCollect(CollectPubIn collectPubIn);


    }

    /**
     * 收藏
     */
    public interface ICollectionModel extends IBaseModel {
        void requestCollect(CollectPubIn collectPubIn);

        void cancelCollect(CollectPubIn collectPubIn);


    }

    /**
     * 商铺视图
     */
    public interface IShopCenterView extends IBaseView {
        void showHomeResponse(List<ShopProductDetail> list);

        void getRefreshData(List<ProductResponse> list);

        void getLoadMoreData(List<ProductResponse> list);

        void responseProductCategory(List<ProductCategory> productCategories);

        void responseDescData(ProductIntroduceOut productIntroduceOut);

//        void setLonglat(UserGPS userGPS);
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

        void requestProductCategory(ProductCategoryIn productCategoryIn);

        void responseProductCategory(ProductCategoryOut productCategoryOut);

        void requestDescData(ProductIntroduceIn productIntroduceIn);

        void responseDescData(ProductIntroduceOut productIntroduceOut);

//        void getLonglat(String id);
//
//        void setLonglat(UserGPS userGPS);
    }

    /**
     * 商铺数据请求
     */
    public interface IShopCenterModel extends IBaseModel {
        void requestHomeData(ShopProductIn shopProductIn);

        void requestAllProduct(SearchProductRequest searchProductRequest, int flag);

        void requestProductCategory(ProductCategoryIn productCategoryIn);

        void requestDescData(ProductIntroduceIn productIntroduceIn);

//        void getLonglat(String id);

    }

    /**
     * 商铺简介视图
     */
    public interface IProductIntroduceView extends IBaseView {
        void showSuccess(ProductIntroduceOut productIntroduceOut);
    }

    /**
     * 商铺简介控制器
     */
    public interface IProductIntroducePresenter extends IBasePresenter {
        void requestDescData(ProductIntroduceIn productIntroduceIn);

        void responseDescData(ProductIntroduceOut productIntroduceOut);
    }

    /**
     * 商铺简介数据请求
     */
    public interface IProductIntroduceModel extends IBaseModel {
        void requestDescData(ProductIntroduceIn productIntroduceIn);
    }

    /**
     * 全部评价视图
     */
    public interface IAllCommentView extends IBaseView {
        void getRefreshData(List<CommentResponse> list, int tag, int num);

        void getLoadMoreData(List<CommentResponse> list, int tag, int num);

        void responseAllCommentNum(int all);

        void responsePicNum(int pic);
    }

    /**
     * 问一问全部评价视图
     */
    public interface IAskAllCommentView extends IBaseView {
        void showCollectView(String msg);

        void getRefreshData1(List<AskSecondComent.ListBean> list, int num);

        void getLoadMoreData1(List<AskSecondComent.ListBean> list);

        void showAskComent(List<AskSecondComent.ListBean> list);

        void showMyAsk(List<MyAsk.ListBean> list);

        void showMyAnser(List<MyAnser.ListBean> list);

    }

    /**
     * 全部评价控制器
     */
    public interface IAllCommentPresenter extends IBasePresenter {
        void loadMore(CommentRequest commentRequest, int tag);

        void loadData(CommentRequest commentRequest, int tag);

        boolean hasMore();

        void refreshData(CommentList commentList, int tag);

        void loadMoreData(CommentList commentList, int tag);

        void requestAllCommentNum(CommentRequest commentRequest);

        void responseAllCommentNum(CommentList commentList, int flag);
    }

    /**
     * 问一问全部评价控制器
     */
    public interface IAskAllComentsPresenter extends IBasePresenter {
        void responsePrise(String msg);

        boolean hasMore();

        void loadMore(String i, String eval_id, String id, AskFilter askFilter);

        void loadData(String i, String eval_id, String id, AskFilter askFilter);

        void refreshData1(AskSecondComent askSecondComent);

        void loadMoreData1(AskSecondComent askSecondComent);

        //取消点赞
        void requestAskCancelPrise(String id, String cacheUid);

        void AskrequestComment(String i, String eval_id, String id);

        void showAskComent(AskSecondComent askSecondComent);

        void showMyAsk(MyAsk ask);

        void showMyAnser(MyAnser anser);

        void requestMyAsk(String cacheUid, int i);

        void requestMyAnser(String cacheUid, int i);

    }

    /**
     * 全部评价数据请求
     */
    public interface IAllCommentModel extends IBaseModel {
        void requestAllComment(CommentRequest commentRequest, int flag, int tag);

        void requestAllCommentNum(CommentRequest commentRequest, int flag);
    }

    /**
     * 问一问全部评价数据请求
     */
    public interface IAskAllCommentModel extends IBaseModel {

        void AskrequestComment(String i, String eval_id, String id, AskFilter askFilter, final int flag);
//        void AskrequestComment(String i, String eval_id, int flag, String id);

        void requestMyAsk(String cacheUid, int i);

        void requestMyAnser(String cacheUid, int i);

        void requestAskPrise(String id, String cacheUid);

        void requestAskCancelPrise(String id, String cacheUid);

        void requestAllCommentNum(CommentRequest commentRequest, final int flag);
    }

    /**
     * 发布评价视图
     */
    public interface IPubCommentView extends IBaseView {
        void showSuccess(String msg);
    }

    /**
     * 发布评价控制器
     */
    public interface IPubCommentPresenter extends IBasePresenter {
        void showSuccess(String msg);

        void publish(PubCommentIn pubCommentIn);
    }

    /**
     * 发布评价数据请求
     */
    public interface IPubCommentModel extends IBaseModel {
        void publish(PubCommentIn pubCommentIn);
    }

    /**
     * 我的卖家中心商铺视图
     */
    public interface IMineShopCenterView extends IBaseView {
    }

    /**
     * 我的卖家中心商铺控制器
     */
    public interface IMineShopCenterPresenter extends IBasePresenter {
    }

    /**
     * 我的卖家中心商铺数据请求
     */
    public interface IMineShopCenterModel extends IBaseModel {
    }

    /**
     * 商铺装潢视图
     */
    public interface IShopDecorateView extends IBaseView {
        void showHomeResponse(List<ShopProductDetail> list);

        void getRefreshData(List<ProductResponse> list);

        void getLoadMoreData(List<ProductResponse> list);

        void responseProductCategory(List<ProductCategory> productCategories);
    }

    /**
     * 商铺装潢控制器
     */
    public interface IShopDecoratePresenter extends IBasePresenter {
        void requestHomeData(ShopProductIn shopProductIn);

        void responseHomeData(ShopProductOut shopProductOut);

        void loadMore(SearchProductRequest searchProductRequest);

        void loadData(SearchProductRequest searchProductRequest);

        boolean hasMore();

        void refreshData(ProductList productList);

        void loadMoreData(ProductList productList);

        void requestProductCategory(ProductCategoryIn productCategoryIn);

        void responseProductCategory(ProductCategoryOut productCategoryOut);
    }

    /**
     * 商铺装潢数据请求
     */
    public interface IShopDecorateModel extends IBaseModel {
        void requestHomeData(ShopProductIn shopProductIn);

        void requestAllProduct(SearchProductRequest searchProductRequest, int flag);

        void requestProductCategory(ProductCategoryIn productCategoryIn);
    }

}