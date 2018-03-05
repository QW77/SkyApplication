package com.sky.app.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.presenter.ProductIntroducePresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 店铺详细信息
 */
public class ProductIntroduceActivity extends BaseViewActivity<ShopContract.IProductIntroducePresenter>
        implements ShopContract.IProductIntroduceView, ShopContract.ICollectionView {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;
    @BindView(R.id.app_image)
    ImageView image;
    @BindView(R.id.app_seller_name)
    TextView sellerName;
    @BindView(R.id.app_collect_num)
    TextView collectNum;
    @BindView(R.id.shop_collect)
    Button collect;
    @BindView(R.id.app_desc)
    TextView desc;
    @BindView(R.id.app_content)
    TextView content;
    @BindView(R.id.app_addr)
    TextView addr;
//    @BindView(R.id.app_mobile)
//    TextView mobile;
//    @BindView(R.id.app_qq)
//    TextView qq;
//    @BindView(R.id.app_weixin)
//    TextView weixin;

    private String seller_id;

    private ShopContract.ICollectionPresenter iCollectionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_product_introduce);
    }

    @Override
    protected ShopContract.IProductIntroducePresenter presenter() {
        iCollectionPresenter = new CollectPresenter(context, this);
        return new ProductIntroducePresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ProductIntroduceIn productIntroduceIn = new ProductIntroduceIn();
        productIntroduceIn.setUser_id(seller_id);
        getPresenter().requestDescData(productIntroduceIn);
    }

    @Override
    protected void init() {
        addr.setMovementMethod(ScrollingMovementMethod.getInstance());
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        desc.setMovementMethod(ScrollingMovementMethod.getInstance());
        seller_id = getIntent().getStringExtra("seller_id");
        title.setText(R.string.app_product_introduce_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
    }

    @Override
    public void showSuccess(ProductIntroduceOut productIntroduceOut) {
        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(), image,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        sellerName.setText(productIntroduceOut.getNick_name());
        collectNum.setText(productIntroduceOut.getNum_collect() + "");
        desc.setText(productIntroduceOut.getShoper_desc());
        content.setText(productIntroduceOut.getMain_business_desc());
        addr.setText(productIntroduceOut.getAddress());
//        mobile.setText(productIntroduceOut.getMobile());
//        qq.setText(productIntroduceOut.getQq());
//        weixin.setText(productIntroduceOut.getWeixin());

        if (productIntroduceOut.getIs_collect() == 1){
            collect.setText("已收藏");
        }else{
            collect.setText("收藏店铺");
        }
    }

    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
        if (tag == 1){
            collect.setText("已收藏");
        } else if (tag == 2){
            collect.setText("收藏店铺");
        }
    }

    @Override
    public void showCollectError(String error) {
        if (tag == 1){
            collect.setText("收藏店铺");
        } else if (tag == 2){
            collect.setText("已收藏");
        }
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    private int tag = -1;
    @OnClick(R.id.shop_collect)
    void clickCollect(){
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("2");
        collectPubIn.setCollect_value(seller_id);
        if ("收藏店铺".equals(collect.getText().toString())){
            tag = 1;
            iCollectionPresenter.requestCollect(collectPubIn);
        } else {
            tag = 2;
            iCollectionPresenter.cancelCollect(collectPubIn);
        }
    }

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(this);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }
}
