package com.sky.shop.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.Category;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductCategoryOut;
import com.sky.shop.bean.ProductDeatilResponse;
import com.sky.shop.bean.PropertyList;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ProductContract;
import com.sky.shop.presenter.AddProductPresenter;

/**
 * 添加商品
 * Created by sky on 2017/2/18.
 */

public class AddProductModel extends BaseModel<AddProductPresenter> implements ProductContract.IAddProModel {

    public AddProductModel(Context context, AddProductPresenter addProductPresenter) {
        super(context, addProductPresenter);
    }

    /**
     * @param productDeatilResponse
     * @Query("user_id") String user_id,
     * @Query("product_name") String product_name,
     * @Query("product_image_url") String product_image_url,
     * @Query("product_type_two") String product_type_two,
     * @Query("one_dir_id") String one_dir_id,
     * @Query("two_dir_id") String two_dir_id,
     * @Query("other_flag") String other_flag,
     * @Query("product_desc") String product_desc,
     * @Query("attrs") String attrs,
     * @Query("product_price_now") String product_price_now
     */

    @Override
    public void requestAddProduct(ProductDeatilResponse productDeatilResponse) {
        productDeatilResponse.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .publishProduct(productDeatilResponse),
//                                UserBean.getInstance().getCacheUid(),
//                                productDeatilResponse.getProduct_name(),
//                                productDeatilResponse.getProduct_image_url(), productDeatilResponse.getProduct_type_two(),
//                                productDeatilResponse.getOne_dir_id(), productDeatilResponse.getTwo_dir_id(),
//                                productDeatilResponse.getOther_flag(), productDeatilResponse.getProduct_desc(),
//                                productDeatilResponse.getAttrs(), productDeatilResponse.getProduct_price_now()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseAddProduct("操作商品成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("123456_error1",error);
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestProductCategory(ProductCategoryIn productCategoryIn) {
        productCategoryIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestProductCategory(productCategoryIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseProductCategory(new Gson().fromJson(success, ProductCategoryOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestEditProduct(ProductDeatilResponse productDeatilResponse) {
        productDeatilResponse.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .updateProduct(productDeatilResponse),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        ProductDeatilResponse productDeatilResponse1 = new Gson().fromJson(success, ProductDeatilResponse.class);
                        getPresenter().responseEditProduct(productDeatilResponse1.toString());
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("123456_error2",error);

                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestProductProperty() {
        Category category = new Category();
        category.setOne_dir_id("FW500001");
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchSecondCatogory(category),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        CategoryList categoryList = new Gson().fromJson(success, CategoryList.class);

                        getPresenter().getPropertySuccess(categoryList);

                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
