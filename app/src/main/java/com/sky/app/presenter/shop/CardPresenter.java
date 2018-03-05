package com.sky.app.presenter.shop;

import android.content.Context;

import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.StatusUPdata;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.SellerContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.shop.CardModel;

/**
 * Created by sky on 2017/2/10.
 * 名片
 */
public class CardPresenter extends BasePresenter<SellerContract.ICardView>
        implements SellerContract.ICardPresenter {
    private SellerContract.ICardModel iCardModel;

    /**
     * 构造
     */
    public CardPresenter(Context context, SellerContract.ICardView iCardView) {
        super(context, iCardView);
        iCardModel = new CardModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iCardModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void responseCardInfo(ProductIntroduceOut productIntroduceOut) {
        getView().responseCardInfo(productIntroduceOut);
        getView().hideProgress();
    }

    @Override
    public void requestCardInfo(ProductIntroduceIn productIntroduceIn) {
        getView().showProgress();
        productIntroduceIn.setLogin_user_id(UserBean.getInstance().getCacheUid());
        productIntroduceIn.setLogin_user_name(UserBean.getInstance().getUserCache().getNick_name());
        iCardModel.requestCardInfo(productIntroduceIn);
    }

    @Override
    public void updataStatus(String user_id) {
        iCardModel.updataStatus(user_id);
    }

    @Override
    public void updataStatusSuccess(StatusUPdata statusUPdata) {
        getView().updataStatusSuccess(statusUPdata);
    }
}