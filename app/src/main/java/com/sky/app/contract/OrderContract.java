package com.sky.app.contract;

import com.sky.app.bean.AddCommentInfo;
import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.AlipayOut;
import com.sky.app.bean.AskOne;
import com.sky.app.bean.CancelOrderIn;
import com.sky.app.bean.MutiOrderIn;
import com.sky.app.bean.MutiShopCarIn;
import com.sky.app.bean.MutiShopCarOut;
import com.sky.app.bean.OrderDetail;
import com.sky.app.bean.OrderIn;
import com.sky.app.bean.OrderOut;
import com.sky.app.bean.ShopCarDetail;
import com.sky.app.bean.ShopCarIn;
import com.sky.app.bean.ShopCarList;
import com.sky.app.bean.ShopCarOut;
import com.sky.app.bean.SingleOrderIn;
import com.sky.app.bean.SingleOrderOut;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.app.ui.activity.ask.AskAddCommentInfo;

import org.json.JSONObject;

import java.util.List;

import retrofit2.http.Query;

/**
 * Created by sky on 2017/2/10.
 * 订单契约类
 */

public class OrderContract {
    /**
     * 我的订单更新UI方法
     */
    public interface IMyOrderView extends IBaseView {
        void getRefreshData(List<OrderDetail> list);

        void getLoadMoreData(List<OrderDetail> list);

        void responseCancel(String msg);

        void responseConfirm(String msg);

        void responseDel(String msg);

        void responseWeixinPay(JSONObject jsonObject);

        void responseAlipay(AlipayOut alipayOut);
    }

    /**
     * 我的订单处理业务逻辑
     */
    public interface IMyOrderPresenter extends IBasePresenter {
        void loadMore();

        void loadData();

        boolean hasMore();

        void refreshData(OrderOut orderOut);

        void loadMoreData(OrderOut orderOut);

        void cancel(CancelOrderIn cancelOrderIn);

        void confirm(CancelOrderIn cancelOrderIn);

        void responseCancel(String msg);

        void responseConfirm(String msg);

        void del(CancelOrderIn cancelOrderIn);

        void responseDel(String msg);

        void weixinPay(CancelOrderIn catchOrder);

        void responseWeixinPay(JSONObject jsonObject);

        void alipay(CancelOrderIn catchOrder);

        void responseAlipay(AlipayOut alipayOut);
    }

    /**
     * 我的订单网络请求
     */
    public interface IMyOrderModel extends IBaseModel {
        void requestMyOrder(OrderIn orderIn, int flag);

        void cancelMyOrder(CancelOrderIn cancelOrderIn);

        void confirmMyOrder(CancelOrderIn cancelOrderIn);

        void delMyOrder(CancelOrderIn cancelOrderIn);

        void weixinPay(CancelOrderIn catchOrder);

        void alipay(CancelOrderIn catchOrder);
    }

    /**
     * 我的购物车更新UI方法
     */
    public interface IMyCarView extends IBaseView {
        void queryShoppingCarResult(List<ShopCarList> shopCarLists);

        void delResult(String msg);

        void updateResult(String msg);
    }

    /**
     * 我的购物车处理业务逻辑
     */
    public interface IMyCarPresenter extends IBasePresenter {
        void queryShoppingCar();

        void queryShoppingCarResult(ShopCarOut shopCarOut);

        void del(ShopCarIn shopCarIn);

        void delResult(String msg);

        void update(ShopCarIn shopCarIn);

        void updateResult(String msg);
    }

    /**
     * 我的购物车网络请求
     */
    public interface IMyCarModel extends IBaseModel {
        void queryShoppingCar();

        void del(ShopCarIn shopCarIn);

        void update(ShopCarIn shopCarIn);
    }

    /**
     * 确认订单更新UI方法
     */
    public interface IConfirmOrderView extends IBaseView {
        void confirmOrderResult(SingleOrderOut singleOrderOut);

        void queryDefaultAddrResult(AddressDetail addressDetail);
    }

    /**
     * 确认订单处理业务逻辑
     */
    public interface IConfirmOrderPresenter extends IBasePresenter {
        void confirmOrder(SingleOrderIn singleOrderIn);

        void confirmOrderResult(SingleOrderOut singleOrderOut);

        void queryDefaultAddrResult(AddressDetail addressDetail);

        void queryDefaultAddr();
    }

    /**
     * 确认订单网络请求
     */
    public interface IConfirmOrderModel extends IBaseModel {
        void confirmOrder(SingleOrderIn singleOrderIn);

        void queryDefaultAddr();
    }

    /**
     * 确认订单更新UI方法
     */
    public interface IMutiConfirmOrderView extends IBaseView {
        void confirmOrderResult(String msg);

        void queryProductListResult(List<ShopCarDetail> shopCarDetailList);

        void queryDefaultAddrResult(AddressDetail addressDetail);
    }

    /**
     * 确认订单处理业务逻辑
     */
    public interface IMutiConfirmOrderPresenter extends IBasePresenter {
        void confirmOrder(MutiOrderIn mutiOrderIn);

        void confirmOrderResult(String msg);

        void queryDefaultAddrResult(AddressDetail addressDetail);

        void queryDefaultAddr();

        void queryProductList(MutiShopCarIn mutiShopCarIn);

        void queryProductListResult(MutiShopCarOut mutiShopCarOut);
    }

    /**
     * 确认订单网络请求
     */
    public interface IMutiConfirmOrderModel extends IBaseModel {
        void confirmOrder(MutiOrderIn mutiOrderIn);

        void queryDefaultAddr();

        void queryProductList(MutiShopCarIn mutiShopCarIn);
    }

    /**
     * 确认订单更新UI方法
     */
    public interface CommentView extends IBaseView {
        void Succec();

        void getAskResult();
    }

    /**
     * 确认订单处理业务逻辑
     */
    public interface CommentPresenter extends IBasePresenter {
        void getData(AddCommentInfo info);

        void getAskData(String user_id, String id, String layer, String eval_id, int i, String eval_comments, AskAddCommentInfo addCommentInfo);

        void requestCenterAsk(String two_dir_id, int i, String askcoment, AskOne addCommentInfo);

        void getResult();

        void getAskResult();
    }

    /**
     * 确认订单网络请求
     */
    public interface CommentModel extends IBaseModel {
        void getData(AddCommentInfo info);

        void getAskData(String user_id, String id, String layer, String eval_id, int i, String eval_comment, AskAddCommentInfo addCommentInfo);

        void requestCenterAsk(String cacheUid, String two_dir_id, int i, String askcoment,AskOne askOne);
    }

    /**
     * 支付界面更新UI方法
     */
    public interface IPayView extends IBaseView {

    }

    /**
     * 支付界面处理业务逻辑
     */
    public interface IPayPresenter extends IBasePresenter {
    }

    /**
     * 支付界面网络请求
     */
    public interface IPayModel extends IBaseModel {
    }
}