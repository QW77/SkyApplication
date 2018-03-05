package com.sky.app.contract;

import com.sky.app.bean.AreaList;
import com.sky.app.bean.Case;
import com.sky.app.bean.CaseIn;
import com.sky.app.bean.CaseOut;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.CommentList;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.CommentResponse;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.SellMessageComplete;
import com.sky.app.bean.StatusUPdata;
import com.sky.app.bean.UserBean;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;

import java.util.List;

/**
 * Created by hongbang on 2017/5/11.
 */

public class SellerContract {


    /**
     * 个人中心处理业务逻辑
     */
    public interface IUploadPresenter extends IBasePresenter {
        void uploadFirstFile(String url);
        void uploadSecondFile(String url);
        void sellRegister(SellMessageComplete  sellMessageComplete);
        void registerSuccess(UserBean  userBean);
    }
    public interface IUploadModel extends IBaseModel{
        void sellRegister(SellMessageComplete  sellMessageComplete);
    }
    /**
     * UI方法
     */
    public interface IUploadView extends IBaseView {

        void getFirstImageUrl(String url);
        void getSecondImageUrl(String url);
        void registerSuccess(UserBean  userBean);
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
        void getSecondCatogoryData(String  firstId);
        void secondCatogoryDataSuccess(CategoryList categoryList);
        void getDecrationCity(String  id);
        void successDecrationCity(DecorationCityList categoryList);
    }

    /**
     * 首页网络请求
     */
    public interface ISellerSecondCategoryModel extends IBaseModel {
        void getArea();
        void getSecondCatogoryData(String  firstId);
        void getDecrationCity(String  id);
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
     * 商户个人主页视图
     */
    public interface ISellerCenterView extends IBaseView{
        void showSuccess(ProductIntroduceOut productIntroduceOut);
        void responseComment(CommentResponse commentResponse);
        void getRefreshData(List<Case> list);
        void getLoadMoreData(List<Case> list);
    }

    /**
     * 商户个人主页控制器
     */
    public interface ISellerCenterPresenter extends IBasePresenter{
        void requestDescData(ProductIntroduceIn productIntroduceIn);
        void responseDescData(ProductIntroduceOut productIntroduceOut);
        void requestComment(CommentRequest commentRequest);
        void responseComment(CommentList commentList);
        void loadMore(CaseIn caseIn);
        void loadData(CaseIn caseIn);
        boolean hasMore();
        void refreshData(CaseOut caseOut);
        void loadMoreData(CaseOut caseOut);
    }

    /**
     * 商户个人主页数据请求
     */
    public interface ISellerCenterModel extends IBaseModel{
        void requestDescData(ProductIntroduceIn productIntroduceIn);
        void requestComment(CommentRequest commentRequest);
        void getCaseList(CaseIn caseIn, int flag);
    }

    /**
     * 名片视图
     */
    public interface ICardView extends IBaseView{
        void responseCardInfo(ProductIntroduceOut productIntroduceOut);
        void updataStatusSuccess(StatusUPdata statusUPdata);
    }

    /**
     * 名片控制器
     */
    public interface ICardPresenter extends IBasePresenter{
        void responseCardInfo(ProductIntroduceOut productIntroduceOut);
        void requestCardInfo(ProductIntroduceIn productIntroduceIn);
        void updataStatus(String user_id);
        void updataStatusSuccess(StatusUPdata statusUPdata);
    }

    /**
     * 名片请求
     */
    public interface ICardModel extends IBaseModel{
        void requestCardInfo(ProductIntroduceIn productIntroduceIn);
        void updataStatus(String user_id);
    }
}
