package com.sky.app.contract;

import com.sky.app.bean.AreaList;
import com.sky.app.bean.AskItem;
import com.sky.app.bean.AskOne;
import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.BindBean;
import com.sky.app.bean.BindIn;
import com.sky.app.bean.BindOut;
import com.sky.app.bean.Category;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.DecorationPingMian;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.bean.ForgetIn;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.Message;
import com.sky.app.bean.MessageAskTable;
import com.sky.app.bean.MessageIn;
import com.sky.app.bean.MessageList;
import com.sky.app.bean.MyCollect;
import com.sky.app.bean.MyCollectIn;
import com.sky.app.bean.MyCollectOut;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.SearshDecorationLongAndlatitude;
import com.sky.app.bean.ThidBindIn;
import com.sky.app.bean.UpdatePwdIn;
import com.sky.app.bean.UserBean;
import com.sky.app.bean.UserBeanList;
import com.sky.app.bean.UserBeanList1;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.ui.activity.search.SearchShopUser;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 主页契约类
 */

public class UserContract {

    /**
     * 主页更新UI方法
     */
    public interface IMineView extends IBaseView {
        /**
         * 刷新用户界面
         */
        void showUserInfo();

        void showMobile(String msg);
    }

    /**
     * 主页更新UI方法
     */
    public interface ILoginView extends IBaseView {
        /**
         * 展示用户
         */
        void showUserInfo();

        void responseIsBindMobile(boolean isBind, String mobile);
    }

    /**
     * UI方法注册
     */
    public interface IRegisterView extends IBaseView {
        void showResult();
    }

    /**
     * UI方法
     */
    public interface IForgetView extends IBaseView {
        void showSuccess();
    }

    /**
     * UI方法
     */
    public interface ISearchSecond extends IBaseView {
        void success(CategoryList categoryList);

        void userDataSuccess(UserBeanList categoryList);

        void userDataSuccess1(UserBeanList1 userBeanList);

        void showMoreGoodShop(List<GoodShop.ListBean> goodList);
    }

    /**
     * 搜索地址业务逻辑
     */
    public interface ISearchSecondPresent extends IBasePresenter {
        void getData(Category category);

        void success(CategoryList categoryList);

        void getUserData(SearchUser searchUser);

        void getUserData1(String id);

        void getUserDataMore(int i);

        void showMoreGoodShop(GoodShop goodShop);

        void userDataSuccess(UserBeanList categoryList);

        void userDataSuccess1(UserBeanList1 userBeanList1);
    }

    /**
     * 搜索地址
     */
    public interface ISearchSecondModel extends IBaseModel {
        void getData(Category category);

        void getUserData1(String id);

        void getUserDataMore(int i);

        void getUserData(SearchUser searchUser);
    }

    /**
     * UI方法
     */
    public interface ISearchByDecorationCity extends IBaseView {

        void showBannerSuccess(List<BannerInfo> list);

        void success(DecorationCityList categoryList);

        void userDataSuccess(UserBeanList categoryList);

        void showDecorationTwoLeft(SearchDecorationTwoLeft list);

        void showBoutiquesuccess(List<DecorationTwoButique.ListBean> butiqueList);

        void showPingPic(List<DecorationPingMian.ListBean> pinglist);

    }

    public interface ISearchByDecorationCityTwo extends IBaseView {

        void showBannerSuccess(List<BannerInfo> list);

        void success(DecorationCityList categoryList);

        void userDataSuccess(UserBeanList categoryList);

        void showDecorationTwoLeft(SearchDecorationTwoLeft list);

        void showBoutiquesuccess(List<DecorationTwoButique.ListBean> butiqueList);

        void showDecorationHeadSuccess(DecorationHeadlinearDetai[] headlinears);

        void showDecorationLongAndLatitude(SearshDecorationLongAndlatitude longAndlatitude);
    }

    /**
     * 搜索地址业务逻辑
     */
    public interface ISearchByDecorationCityPresenter extends IBasePresenter {
        void getData(String id);

        void requestBanner();

        void showBannersuccess(BannerOut bannerOut);

        void success(DecorationCityList categoryList);

        //        void getUserData(String id,String  key);
        void getUserData(SearchUser searchUser);

        //请求精品店的数据
        void requestBoutique(String id, int i);

        void requestZSCPic(String id);

        void showPingPic(DecorationPingMian decorationPingMian);

        void showBoutiquesuccess(DecorationTwoButique decorationTwoButique);

        void userDataSuccess(UserBeanList categoryList);

        void getSearchTwo();

        void showDecorationTwoLeft(SearchDecorationTwoLeft list);
    }

    public interface ISearchByDecorationCityTwoPresenter extends IBasePresenter {
        void getData(String id);

        void requestBanner(String id);

        void requestZSCAction(String id);

        void requestDecorationHeadlines(String id);

        void requestLatitudeAndLong(String id);

        void showDecorationLongAndLatitude(SearshDecorationLongAndlatitude longAndlatitude);

        void showDecorationHeadSuccess(DecorationHeadlinearDetai[] headlinears);

        void showBannersuccess(BannerOut bannerOut);

        void success(DecorationCityList categoryList);

        //        void getUserData(String id,String  key);
        void getUserData(SearchShopUser searchUser);

        //请求精品店的数据
        void requestBoutique(SearchUser searchUser);

        void showBoutiquesuccess(DecorationTwoButique decorationTwoButique);

        void userDataSuccess(UserBeanList categoryList);

        void getSearchTwo(String decorative_id);


        void showDecorationTwoLeft(SearchDecorationTwoLeft list);
    }

    /**
     * 装饰城中的第二界面中的精品店
     */
    public interface IDecorationBoutiquePresenter extends IBasePresenter {
        void getData(String id);

        void success(DecorationCityList categoryList);

        void getUserData(SearchUser searchUser);

        void userDataSuccess(UserBeanList categoryList);

    }

    /**
     * 装饰城中的第二界面中的精品店
     */
    public interface IDecorationBoutiqueModel extends IBaseModel {
        void getData(String d);


        void getUserData(SearchUser searchUser);
    }


    /**
     * 搜索地址
     */
    public interface ISearchByDecorationCityModel extends IBaseModel {
        void getData(String d);

        void requestZSCPic(String id);

        void requestBanner(BannerIn bannerIn);

        //void getUserData(String id,String  key);
        void getSearchTwo();

        void requestBoutique(String id, int i);

        void getUserData(SearchUser searchUser);
    }

    public interface ISearchByDecorationCityTwoModel extends IBaseModel {
        void getData(String id);

        void requestZSCAction(String id);

        void requestBanner(String id);

        void requestDecorationHeadlines(String id);

        void requestLatitudeAndLong(String id);

        //void getUserData(String id,String  key);
        void getSearchTwo(String decorative_id);

        void requestBoutique(SearchUser searchUser);

        void getUserData(SearchShopUser searchUser);
    }


    /**
     * UI方法
     */
    public interface IMessageView extends IBaseView {
        void showAskTable(MessageAskTable messageAskTable);

        void showAskUp();

        void showAskComment();

        void getRefreshData(List<Message> list);

        void getRefreshData1(List<AskItem.ListBean> list);

        void showAskList(List<AskItem.ListBean> list);

        void getLoadMoreData(List<Message> list);

        void deleteSuccess(String msg);
    }

    /**
     * 搜索地址业务逻辑
     */
    public interface IMessagePresenter extends IBasePresenter {

        //请求问一问的tablie
        void requestAskTable();

        void showAskTable(MessageAskTable messageAskTable);

        //请求问一问的下面的数据
        void requestAskList(int i, String eval_id);


        void loadData(int i, String eval_id);

        void loadData();

        //返回问一问的下面的数据
        void showAskList(List<AskItem.ListBean> list);

        //点赞
        void requestAskUp(String id);

        void showAskUp();

        void showAskComment();

        void requestCenterAsk( String two_dir_id, int i, String askcoment, AskOne askOne);

        void deleteMessage(Message message);

        void deleteSuccess(String msg);

        void loadMore();

        void loadMore(int i, String eval_id);


        boolean hasMore();

        void refreshData(MessageList messageList);

        void refreshData1(AskItem askItem);

        void loadMoreData(MessageList messageList);
    }

    /**
     * 搜索地址
     */
    public interface IMessageModel extends IBaseModel {
        void requestAskTable();

        void requestAskList(int i, String eval_id);

        void requestAskList(int i, String eval_id, AskOne askOne, final int flag);

        void requestAskUp(String id, String cacheUid);

        void requestCenterAsk(String cacheUid, String two_dir_id, int i, String askcoment,AskOne askOne);

        void deleteMessage(Message message);

        void getMessage(MessageIn messageIn, int flag);
    }


    /**
     * UI方法
     */
    public interface ISearchByFactory extends IBaseView {
        void success(CategoryList categoryList);

        void userDataSuccess(UserBeanList userBeanList);
    }

    /**
     * 搜索地址业务逻辑
     */
    public interface ISearchByFactoryPresenter extends IBasePresenter {
        void getData(String id);

        void success(CategoryList categoryList);

        //        void getUserData(String id,String  key);
        void getUserData(SearchUser searchUser);

        void userDataSuccess(UserBeanList userBeanList);
    }

    /**
     * 搜索地址
     */
    public interface ISearchByFactoryModel extends IBaseModel {
        void getData(String id);

        //        void getUserData(String id,String  key);
        void getUserData(SearchUser searchUser);

    }

    /**
     * UI方法
     */
    public interface ISearchByPlace extends IBaseView {
        void firstCatogoryDataSuccess(List<FirstCategoryDetail> list);

        void success(AreaList areaList);

        void successDecrationCity(DecorationCityList categoryList);

        void secondCatogoryDataSuccess(CategoryList categoryList);

        void userDataSuccess(UserBeanList userBeanList);
    }

    /**
     * 搜索地址业务逻辑
     */
    public interface ISearchByPlacePresenter extends IBasePresenter {
        void getDecrationCity(String id);

        void successDecrationCity(DecorationCityList categoryList);

        void getData(String id);

        void success(AreaList areaList);

        void getFirstCatogoryData();

        void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut);

        void getSecondCatogoryData(String firstId);

        void secondCatogoryDataSuccess(CategoryList categoryList);

        //        void getUserData(String manufacturer_type_id,String three_dir_id,String two_dir_id,String key);
        void getUserData(SearchUser searchUser);

        void userDataSuccess(UserBeanList userBeanList);
    }

    /**
     * 搜索地址
     */
    public interface ISearchByPlaceModel extends IBaseModel {
        void getDecrationCity(String id);

        void getData(String id);

        void getFirstCatogoryData();

        void getSecondCatogoryData(String firstId);

        //        void getUserData(String manufacturer_type_id,String three_dir_id,String two_dir_id,String key);
        void getUserData(SearchUser searchUser);

    }

    /**
     * UI方法
     */
    public interface ICenterView extends IBaseView {
        /**
         * 刷新界面
         */
        void refresh(UserBean userBean);

        void showOnSuccess(String msg);

        void getImageUrl(String url);
    }

    /**
     * 主页处理业务逻辑
     */
    public interface ILoginPresenter extends IBasePresenter {
        /**
         * 登录
         */
        void login(ForgetIn forgetIn);

        /**
         * 刷新用户界面
         *
         * @param userBean
         */
        void refreshData(UserBean userBean);

        void queryUserIsBindMobile(ThidBindIn thidBindIn);

        void responseUserIsBindMobile(BindBean bindBean);
    }


    /**
     * 注册处理业务逻辑
     */
    public interface IRegisterPresenter extends IBasePresenter {
        /**
         * 注册
         */
        void register(ForgetIn forgetIn);

        void showSuccess();
    }

    /**
     * 主页处理业务逻辑
     */
    public interface IMinePresenter extends IBasePresenter {
        /**
         * 获取用户信息
         */
        void refreshUserInfo();

        /**
         * 刷新用户信息
         */
        void updateInfo();

        void requestMobile();

        void responseMobile(String msg);
    }

    /**
     * 忘记密码处理业务逻辑
     */
    public interface IForgetPresenter extends IBasePresenter {
        void forget(ForgetIn forgetIn);

        void showSuccess();
    }

    /**
     * 个人中心处理业务逻辑
     */
    public interface ICenterPresenter extends IBasePresenter {
        void getUserInfo();

        void setUserInfo();

        void uploadFile(String url);

        void showSuccess();

        void showOnSuccess(String msg);
    }

    /**
     * 验证码处理业务逻辑
     */
    public interface ICodePresenter extends IBasePresenter {
        /**
         * 发送验证码
         */
        void sendCode(String mobile);
    }

    /**
     * 网络请求
     */
    public interface ILoginModel extends IBaseModel {
        /**
         * 登录
         */
        void login(ForgetIn forgetIn);

        void queryUserIsBindMobile(ThidBindIn thidBindIn);
    }


    /**
     * 网络请求
     */
    public interface IMineModel extends IBaseModel {
        /**
         * 获取用户账户信息
         */
        void getUserData();

        void requestMobile();
    }

    /**
     * 网络请求
     */
    public interface IRegisterModel extends IBaseModel {
        /**
         * 注册
         */
        void register(ForgetIn forgetIn);
    }

    /**
     * 忘记密码
     */
    public interface IForgetModel extends IBaseModel {
        void forget(ForgetIn forgetIn);
    }

    /**
     * 验证码
     */
    public interface ICodeModel extends IBaseModel {
        /**
         * 发送验证码
         */
        void sendCode(String mobile);
    }

    /**
     * 个人中心
     */
    public interface ICenterModel extends IBaseModel {
        /**
         * 展示用户信息
         */
        void getUserInfo();

        /**
         * 设置用户信息
         */
        void setUserInfo();
    }

    /**
     * UI方法
     */
    public interface ISettingView extends IBaseView {
        void refreshView(BindOut bindOut);

        void responseUpdatePwd(String pwd);

        void responseBindData(String msg);
    }

    /**
     * 设置处理业务逻辑
     */
    public interface ISettingPresenter extends IBasePresenter {
        void queryUserDetail();

        void responseUserInfo(BindOut bindOut);

        void updatePwd(UpdatePwdIn updatePwdIn);

        void responseUpdatePwd(String pwd);

        void bindData(BindIn bindIn);

        void responseBindData(String msg);
    }

    /**
     * 设置
     */
    public interface ISettingModel extends IBaseModel {
        void queryUserDetail();

        void updatePwd(UpdatePwdIn updatePwdIn);

        void bindData(BindIn bindIn);
    }

    /**
     * 我的收藏UI方法
     */
    public interface IMyCollectView extends IBaseView {
        void getRefreshData(List<MyCollect> list);

        void getLoadMoreData(List<MyCollect> list);

        void showDelSuccess(String msg);
    }

    /**
     * 我的收藏处理业务逻辑
     */
    public interface IMyCollectPresenter extends IBasePresenter {
        void loadMore(MyCollectIn myCollectIn);

        void loadData(MyCollectIn myCollectIn);

        boolean hasMore();

        void refreshData(MyCollectOut myCollectOut);

        void loadMoreData(MyCollectOut myCollectOut);

        void del(ProductIn productIn);

        void showDelSuccess(String msg);
    }

    /**
     * 我的收藏
     */
    public interface IMyCollectModel extends IBaseModel {
        void queryMyCollect(MyCollectIn myCollectIn, int flag);

        void del(ProductIn productIn);
    }
}