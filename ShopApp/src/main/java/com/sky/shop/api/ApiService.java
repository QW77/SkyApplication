package com.sky.shop.api;

import com.sky.app.library.base.bean.Result;
import com.sky.shop.bean.Add_Account_ApplyBean;
import com.sky.shop.bean.ApplyAccountIn;
import com.sky.shop.bean.ApplyRecordIn;
import com.sky.shop.bean.BankIn;
import com.sky.shop.bean.BaseUser;
import com.sky.shop.bean.BindIn;
import com.sky.shop.bean.Case;
import com.sky.shop.bean.CaseIn;
import com.sky.shop.bean.Category;
import com.sky.shop.bean.Empty;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.Message;
import com.sky.shop.bean.MessageIn;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductDeatilResponse;
import com.sky.shop.bean.ProductDetailRequest;
import com.sky.shop.bean.ProductIn;
import com.sky.shop.bean.ProductIntroduceIn;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.ProductUpAndDownIn;
import com.sky.shop.bean.QQWeixinIn;
import com.sky.shop.bean.SearchDecorationCity;
import com.sky.shop.bean.SearchProductRequest;
import com.sky.shop.bean.SearchUser;
import com.sky.shop.bean.SellMessageComplete;
import com.sky.shop.bean.ShopProductIn;
import com.sky.shop.bean.UpdateIn;
import com.sky.shop.bean.UpdatePwdIn;
import com.sky.shop.ui.activity.OpenIMShop.OpenIMUserBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 请求接口列表
 */
public interface ApiService {

    /**
     * open登录
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/openImControl/selectUserInfo")
    Observable<Result> openIMLogin(@Query("user_id") String user_id, @Body OpenIMUserBean openIMUserBean);

    /**
     * open失败登录
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/openImControl/insertUserInfo")
    Observable<Result> openIMRegister(@Query("user_id") String user_id, @Body OpenIMUserBean openIMUserBean);

    /**
     * open修改账户信息
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/openImControl/updateUserInfo")
    Observable<Result> openIMUpDate(@Query("user_id") String user_id, @Body OpenIMUserBean openIMUserBean);

    /**
     * open删除账户信息
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/openImControl/deleteUserInfo")
    Observable<Result> openIMDelete(@Query("user_id") String user_id, @Body OpenIMUserBean openIMUserBean);

    /**
     * 商户注册
     *
     * @return
     */
    @POST("user/update_info")
    Observable<Result> sellRegister(@Query("user_id") String user_id, @Body SellMessageComplete sellMessageComplete);

    /**
     * 获取消息
     */
    @POST("msg/show")
    Observable<Result> getMessage(@Query("user_id") String user_id, @Body MessageIn messageIn);

    /**
     * 删除消息
     */
    @POST("msg/delete")
    Observable<Result> deleteMessage(@Query("user_id") String user_id, @Body Message message);

    /**
     * 忘记密码
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/change_pwd")
    Observable<Result> requestForget(@Body ForgetIn forgetIn);

    /**
     * 注册
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/register")
    Observable<Result> requestRegister(@Body ForgetIn forgetIn);

    /**
     * 登录
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/login")
    Observable<Result> requestLogin(@Body ForgetIn forgetIn);

    /**
     * 发送验证码
     *
     * @return 回调监听
     */
    @FormUrlEncoded
    @POST("sms/send.do")
    Observable<String> requestCode(@FieldMap Map<String, String> map);

    /**
     * 联系客服
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/msg/un/get_customer_phone")
    Observable<Result> requestMobile(@Body Empty empty);

    /**
     * 账户中心金额
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/account_center")
    Observable<Result> requestAccountMoney(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 申请提现
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/withdraw_add")
    Observable<Result> requestApplyAccount(@Query("user_id") String user_id, @Body ApplyAccountIn applyAccountIn);

    /**
     * 我的提现记录
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/my_withdraw_list")
    Observable<Result> queryRecord(@Query("user_id") String user_id, @Body ApplyRecordIn applyRecordIn);

    /**
     * 提现账户
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/my_bank_list")
    Observable<Result> requestMyAccountBank(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 删除提现账户
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/bank_del")
    Observable<Result> delRecord(@Query("user_id") String user_id, @Body BankIn bankIn);

    /**
     * 店铺-首页商品查询
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/un/get_user_home_products")
    Observable<Result> requestShopHomeData(@Body ShopProductIn shopProductIn);

    /**
     * 查询商户商品分类
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/diction/un/get_user_dir")
    Observable<Result> requestProductCategory(@Body ProductCategoryIn productCategoryIn);

    /**
     * 搜索商品
     *
     * @return
     */
    @POST("product/un/get_all_products")
    Observable<Result> searchProduct(@Body SearchProductRequest searchProductRequest);

    /**
     * 查找一级分类
     */
    @POST("diction/un/get_one_dir")
    Observable<Result> searchFirstCatogory(@Body Empty empty);


    /**
     * 查找二级分类
     */
    @POST("diction/un/get_two_dir")
    Observable<Result> searchSecondCatogory(@Body Category category);


    /**
     * 查找装饰城
     */
    @POST("diction/un/get_three_dir")
    Observable<Result> searchDecorationCity(@Body SearchDecorationCity empty);

    /**
     * 查找区域
     */
    @POST("diction/un/get_area_dir")
    Observable<Result> searchPlace(@Body Empty empty);

    /**
     * 查询用户综合接口
     */
    @POST("diction/un/get_user_by_group")
    Observable<Result> searchUser(@Body SearchUser empty);

    /**
     * 商家个人名片、店铺简介、名片简介
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/un/personal_info")
    Observable<Result> requestDescData(@Body ProductIntroduceIn productIntroduceIn);

    /**
     * 修改商家个人名片、店铺简介、名片简介
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/update_shop_info")
    Observable<Result> updateShopIntroduce(@Query("user_id") String user_id, @Body ProductIntroduceOut productIntroduceOut);

    /**
     * 修改工匠状态
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("h5_userController/setUserStatus")
    Observable<Result> setStatus(@Query("user_id") String user_id, @Query("status_code") String status_code);

    /**
     * 添加分类
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/diction/un/add_user_dir")
    Observable<Result> addFirstCategory(@Query("user_id") String user_id, @Body ProductCategory productCategory);

    /**
     * 删除分类
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/diction/un/delete_user_dir")
    Observable<Result> delFirstCategory(@Query("user_id") String user_id, @Body ProductCategory productCategory);

    /**
     * 修改分类
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/diction/un/update_user_dir")
    Observable<Result> updateFirstCategory(@Query("user_id") String user_id, @Body ProductCategory productCategory);

    /**
     * 商品详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("product/un/product_detail")
    Observable<Result> productDetail(@Body ProductDetailRequest productDetailRequest);

    /**
     * 发布商品
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("h5_productController/add_product")
    Observable<Result> publishProduct(@Body ProductDeatilResponse productDeatilResponse);

//    @POST("h5_productController/add_product")
//    Observable<Result> publishProduct(@Query("user_id") String user_id, @Query("product_name") String product_name, @Query("product_image_url") String product_image_url,
//                                      @Query("product_type_two") String product_type_two, @Query("one_dir_id") String one_dir_id,
//                                      @Query("two_dir_id") String two_dir_id, @Query("other_flag") int other_flag, @Query("product_desc") String product_desc,
//                                      @Query("attrs") List<ProductAttribute> attrs, @Query("product_price_now") double product_price_now);

    /**
     * 修改商品
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("h5_productController/update_product")
    Observable<Result> updateProduct(@Body ProductDeatilResponse productDeatilResponse);


    /**
     * 删除商品
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/delete_product")
    Observable<Result> delProduct(@Query("user_id") String user_id, @Body ProductIn productIn);

    /**
     * 下架上架商品
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/up_down_product")
    Observable<Result> upAndDownProduct(@Query("user_id") String user_id, @Body ProductUpAndDownIn productUpAndDownIn);

    /**
     * 获取绑定手机号 微信 qq
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/get_bind_info")
    Observable<Result> queryBindInfo(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 修改登录密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/modify_password")
    Observable<Result> updateUserPwd(@Query("user_id") String user_id, @Body UpdatePwdIn updatePwdIn);

    /**
     * 绑定手机号 微信 qq
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/bind_info")
    Observable<Result> updateUserPwd(@Query("user_id") String user_id, @Body BindIn bindIn);

    /**
     * 获取用户信息
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/percenter")
    Observable<Result> queryUserInfo(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 查找工厂
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("diction/un/get_companys")
    Observable<Result> searchFactory(@Body Empty empty);

    /**
     * 添加提现账户
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/bank_add")
    Observable<Result> AddRecord(@Query("user_id") String user_id, @Body Add_Account_ApplyBean info);

    /**
     * 分页查看用户的案例
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/un/get_cases_list")
    Observable<Result> requestUserCaseList(@Body CaseIn caseIn);

    /**
     * 发布案例
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/add_case")
    Observable<Result> requestAddCase(@Query("user_id") String user_id, @Body Case caseIn);

    /**
     * 编辑案例
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/update_case")
    Observable<Result> requestEditCase(@Query("user_id") String user_id, @Body Case caseIn);

    /**
     * 删除案例
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/delete_case")
    Observable<Result> requestDelCase(@Query("user_id") String user_id, @Body Case caseIn);

    /**
     * 升级
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("app/un/get_version")
    Observable<Result> getVersion(@Body UpdateIn updateIn);

    /**
     * 获取体现手续费
     *
     * @param empty
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("account/get_withdraw_rate")
    Observable<Result> getWithdrawRate(@Query("user_id") String user_id, @Body Empty empty);

    /**
     * QQ登录
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/qq_login")
    Observable<Result> requestQQLogin(@Body QQWeixinIn qqWeixinIn);

    /**
     * 微信登录
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/wx_login")
    Observable<Result> requestWeixinLogin(@Body QQWeixinIn qqWeixinIn);

    /**
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("h5_userController/setUserGps")
    Observable<Result> postlonlat(@Query("user_id") String user_id, @Query("longitude") String longitude, @Query("latitude") String latitude );
}
