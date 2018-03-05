package com.sky.app.contract;

import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.DecorationCityHeadlinearDetai;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.PublishIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.bean.SupplyFilter;
import com.sky.app.bean.SupplyList;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.app.library.component.banner.modle.BannerInfo;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 发布契约类
 */

public class PublishContract {
    /**
     * 发布更新UI方法
     */
    public interface IPublishView extends IBaseView {
        void getRefreshData(List<SupplyDetail> list);

        void getLoadMoreData(List<SupplyDetail> list);
    }

    /**
     * 发布处理业务逻辑
     */
    public interface IPublishPresenter extends IBasePresenter {
        void loadMore(SupplyFilter supplyFilter);

        void loadData(SupplyFilter supplyFilter);

        boolean hasMore();

        void refreshData(SupplyList supplyList);

        void loadMoreData(SupplyList supplyList);
    }

    /**
     * 发布网络请求
     */
    public interface IPublishModel extends IBaseModel {
        void requestPublishList(SupplyFilter supplyFilter, int flag);
    }

    /**
     * 发布更新UI方法
     */
    public interface IPublishContentView extends IBaseView {
        void showSuccess(String msg);

        void responseDetail(SupplyDetail supplyDetail);
    }

    /**
     * 发布处理业务逻辑
     */
    public interface IPublishContentPresenter extends IBasePresenter {
        void publish(PublishIn publishIn);

        void response(String msg);

        void requestDetail(ProductIn productIn);

        void responseDetail(SupplyDetail supplyDetail);
    }

    /**
     * 发布网络请求
     */
    public interface IPublishContentModel extends IBaseModel {
        void publish(PublishIn publishIn);

        void requestDetail(ProductIn productIn);

        void edit(PublishIn publishIn);
    }

    /**
     * 发布更新UI方法
     */
    public interface IPublishDetailView extends IBaseView {
        void showPublishCityBannerSuccess(List<BannerInfo> list);

        void showPublishCityHeadlinearsSuccess(DecorationCityHeadlinearDetai[] headlinears);

        void showPublishsuccess(List<BannerInfo> list);

        void showPublishHeadlinearsSuccess(HeadlinearsDetail[] headlinearsDetail);

        void responseDetail(SupplyDetail supplyDetail);

        void responseCollect(String msg);
    }

    /**
     * 发布处理业务逻辑
     */
    public interface IPublishDetailPresenter extends IBasePresenter {
        void requestPublishBanner();

        void requestPublishCityBanner(String id);

        void requestPublishCityHeadlines(String id);

        void showPublishCityHeadlinearsSuccess( DecorationCityHeadlinearDetai[] headlinears);

        void showPublishCityBannerSuccess(BannerOut bannerOut);

        void showPublishsuccess(BannerOut bannerOut);

        void requestPublishHeadlines();

        void showPublishHeadlinearsSuccess(HeadlinearsDetail[] headlinearsDetail);

        void requestDetail(ProductIn productIn);

        void responseDetail(SupplyDetail supplyDetail);

        void requestCollect(CollectPubIn collectPubIn);

        void responseCollect(String msg);
    }

    /**
     * 发布网络请求
     */
    public interface IPublishDetailModel extends IBaseModel {
        void requestPublishCityBanner(String id);

        void requestPublishBanner(BannerIn bannerIn);

        void requestPublishCityHeadlines(String id);

        void requestPublishHeadlines();

        void requestDetail(ProductIn productIn);

        void requestCollect(CollectPubIn collectPubIn);
    }

    /**
     * 我的发布更新UI方法
     */
    public interface IMyPublishlView extends IBaseView {
        void getRefreshData(List<SupplyDetail> list);

        void getLoadMoreData(List<SupplyDetail> list);

        void showDelSuccess(String msg);
    }

    /**
     * 我的发布处理业务逻辑
     */
    public interface IMyPublishPresenter extends IBasePresenter {
        void loadMore();

        void loadData();

        boolean hasMore();

        void refreshData(SupplyList supplyList);

        void loadMoreData(SupplyList supplyList);

        void del(ProductIn productIn);

        void showDelSuccess(String msg);
    }

    /**
     * 我的发布网络请求
     */
    public interface IMyPublishModel extends IBaseModel {
        void requestList(int flag);

        void del(ProductIn productIn);
    }
}