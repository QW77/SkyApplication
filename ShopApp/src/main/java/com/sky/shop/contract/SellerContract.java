package com.sky.shop.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.shop.bean.Case;
import com.sky.shop.bean.CaseIn;
import com.sky.shop.bean.CaseOut;
import com.sky.shop.bean.ProductIntroduceOut;

import java.util.List;

/**
 * 商家处理
 */

public class SellerContract {

    /**
     * 名片视图
     */
    public interface ICardView extends IBaseView {
        void responseCardInfo(ProductIntroduceOut productIntroduceOut);
        void responseAddCard(String msg);
        void setStatus(String status_code);
    }

    /**
     * 名片控制器
     */
    public interface ICardPresenter extends IBasePresenter {
        void responseCardInfo(ProductIntroduceOut productIntroduceOut);
        void requestCardInfo();
        void requestAddCard(ProductIntroduceOut productIntroduceOut);
        void responseAddCard(String msg);
        void setStatus(String status_code);
        void postlonlat(double longitude, double latitude);
    }

    /**
     * 名片请求
     */
    public interface ICardModel extends IBaseModel {
        void requestCardInfo();
        void requestAddCard(ProductIntroduceOut productIntroduceOut);
        void setStatus(String status_code);
        void postlonlat(double longitude, double latitude);


    }

    /**
     * 商户个人主页视图
     */
    public interface ISellerCenterView extends IBaseView{
        void showSuccess(ProductIntroduceOut productIntroduceOut);
        void getRefreshData(List<Case> list);

        //修改店铺主页
        void responseEditShopInfo(String msg);

        //发布案例
        void responseAddCase(String msg);
        //编辑案例
        void responseEditCase(String msg);
        //删除案例
        void responseDelCase(String msg);
    }

    /**
     * 商户个人主页控制器
     */
    public interface ISellerCenterPresenter extends IBasePresenter{
        void requestDescData();
        void responseDescData(ProductIntroduceOut productIntroduceOut);
        void loadData(CaseIn caseIn);
        void refreshData(CaseOut caseOut);

        //修改店铺主页
        void requestEditShopInfo(ProductIntroduceOut productIntroduceOut);
        void responseEditShopInfo(String msg);

        //发布案例
        void requestAddCase(Case caseIn);
        void responseAddCase(String msg);

        //编辑案例
        void requestEditCase(Case caseIn);
        void responseEditCase(String msg);

        //删除案例
        void requestDelCase(Case caseIn);
        void responseDelCase(String msg);
    }

    /**
     * 商户个人主页数据请求
     */
    public interface ISellerCenterModel extends IBaseModel{
        void requestDescData();
        void getCaseList(CaseIn caseIn);
        void requestEditShopInfo(ProductIntroduceOut productIntroduceOut);

        //发布案例
        void requestAddCase(Case caseIn);

        //编辑案例
        void requestEditCase(Case caseIn);

        //删除案例
        void requestDelCase(Case caseIn);
    }
}
