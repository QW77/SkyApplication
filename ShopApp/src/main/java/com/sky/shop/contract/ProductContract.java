package com.sky.shop.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductCategoryOut;
import com.sky.shop.bean.ProductDeatilResponse;
import com.sky.shop.bean.ProductDetailRequest;
import com.sky.shop.bean.PropertyList;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 商品契约类
 */

public class ProductContract {

    /**
     * 商品详情更新UI方法
     */
    public interface IProDetailView extends IBaseView {
        void productDetailSuccess(ProductDeatilResponse productList);
    }

    /**
     * 商品详情处理业务逻辑
     */
    public interface IProDetailPresenter extends IBasePresenter {
        void productDetailSuccess(ProductDeatilResponse  productList);
        void getProductDetail(ProductDetailRequest productDetailRequest);
    }

    /**
     * 商品详情网络请求
     */
    public interface IProDetailModel extends IBaseModel{
        void getProductDetail(ProductDetailRequest productDetailRequest);
    }

    /**
     * 添加商品更新UI方法
     */
    public interface IAddProView extends IBaseView {
        void showAddSuccess(String msg);
        void responseProductCategory(List<ProductCategory> productCategories);
        void responseEditSuccess(String msg);
        void getPropertySuccess(CategoryList categoryList);
    }

    /**
     * 添加商品处理业务逻辑
     */
    public interface IAddProPresenter extends IBasePresenter {
        void requestAddProduct(ProductDeatilResponse productDeatilResponse);
        void responseAddProduct(String msg);
        void requestProductCategory(ProductCategoryIn productCategoryIn);
        void responseProductCategory(ProductCategoryOut productCategoryOut);
        void requestEditProduct(ProductDeatilResponse productDeatilResponse);
        void responseEditProduct(String msg);
        void getPropertySuccess(CategoryList categoryList);
        void requestProductProperty();

    }

    /**
     * 添加商品网络请求
     */
    public interface IAddProModel extends IBaseModel{
        void requestAddProduct(ProductDeatilResponse productDeatilResponse);
        void requestProductCategory(ProductCategoryIn productCategoryIn);
        void requestEditProduct(ProductDeatilResponse productDeatilResponse);
        void requestProductProperty();
    }

    /**
     * 编辑商品更新UI方法
     */
    public interface IEditProView extends IBaseView {
        void showEditSuccess(String msg);
        void responseProductCategory(List<ProductCategory> productCategories);
        void productDetailSuccess(ProductDeatilResponse productList);
    }

    /**
     * 编辑商品处理业务逻辑
     */
    public interface IEditProPresenter extends IBasePresenter {
        /**
         * 展示错误信息
         */
        void requestEditProduct(ProductDeatilResponse productDeatilResponse);
        void responseEditProduct(String msg);
        void requestProductCategory(ProductCategoryIn productCategoryIn);
        void responseProductCategory(ProductCategoryOut productCategoryOut);

        void productDetailSuccess(ProductDeatilResponse  productList);
        void getProductDetail(ProductDetailRequest productDetailRequest);
    }

    /**
     * 编辑商品网络请求
     */
    public interface IEditProModel extends IBaseModel{
        void requestEditProduct(ProductDeatilResponse productDeatilResponse);
        void requestProductCategory(ProductCategoryIn productCategoryIn);
        void getProductDetail(ProductDetailRequest productDetailRequest);
    }
}