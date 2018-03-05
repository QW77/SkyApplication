package com.sky.app.contract;

import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.AddressIn;
import com.sky.app.bean.AddressOut;
import com.sky.app.bean.AreaDetail;
import com.sky.app.bean.AreaIn;
import com.sky.app.bean.AreaOut;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;

import java.util.List;

/**
 * Created by hongbang on 2017/5/6.
 */

public class MineContract {

    /**
     * 我的收货地址更新UI方法
     */
    public interface IMyAddressView extends IBaseView {
        void reFreshData(List<AddressDetail> addressDetailList);
        void showSetAddressSuccess(String msg);
        void showDelAddressSuccess(String msg);
    }

    /**
     * 我的收货地址处理业务逻辑
     */
    public interface IMyAddressPresenter extends IBasePresenter {
        void loadData();
        void reFreshData(AddressOut addressOut);
        void showSetAddressSuccess(String msg);
        void showDelAddressSuccess(String msg);
        void del(AddressIn addressIn);
        void setDefaultAddress(AddressIn addressIn);
    }

    /**
     * 我的收货地址网络请求
     */
    public interface IMyAddressModel extends IBaseModel {
        void requestMyAddress();
        void setMyAddress(AddressIn addressIn);
        void delMyAddress(AddressIn addressIn);
    }

    /**
     * 我的收货地址详情更新UI方法
     */
    public interface IMyAddressDetailView extends IBaseView {
        void showSuccess(String msg);
    }

    /**
     * 我的收货地址详情处理业务逻辑
     */
    public interface IMyAddressDetailPresenter extends IBasePresenter {
        void add(AddressDetail addressDetail);
        void edit(AddressDetail addressDetail);
        void showSuccess(String msg);
    }

    /**
     * 我的收货地址详情网络请求
     */
    public interface IMyAddressDetailModel extends IBaseModel {
        void add(AddressDetail addressDetail);
        void edit(AddressDetail addressDetail);
    }

    /**
     * 区域更新UI方法
     */
    public interface IAreaView extends IBaseView {
        void showSuccess(List<AreaDetail> list);
    }

    /**
     * 区域处理业务逻辑
     */
    public interface IAreaPresenter extends IBasePresenter {
        void get(AreaIn areaIn);
        void showSuccess(AreaOut areaOut);
    }

    /**
     * 区域网络请求
     */
    public interface IAreaModel extends IBaseModel {
        void get(AreaIn areaIn);
    }

    /**
     * 安全中心UI方法
     */
    public interface ISafeCenterView extends IBaseView {
    }

    /**
     * 安全中心处理业务逻辑
     */
    public interface ISafeCenterPresenter extends IBasePresenter {
    }

    /**
     * 安全中心网络请求
     */
    public interface ISafeCenterModel extends IBaseModel {
    }
}
