package com.sky.app.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.ShopCarDetail;
import com.sky.app.bean.ShopCarIn;
import com.sky.app.bean.ShopCarList;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.MyCarPresenter;
import com.sky.app.ui.activity.product.ProductDetailActivity;
import com.sky.app.ui.adapter.MyShoppingExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的购物车
 */
public class MyShoppingActivity extends BaseViewActivity<OrderContract.IMyCarPresenter>
        implements OrderContract.IMyCarView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_car_list)
    ExpandableListView expandableListView;
    @BindView(R.id.app_all_money)
    TextView allMoney;
    @BindView(R.id.app_count)
    TextView allCount;
    @BindView(R.id.app_all_select)
    CheckBox allSelect;

    List<ShopCarList> carList = new ArrayList<>();
    private MyShoppingExpandableListAdapter myShoppingExpandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_mine_my_shopping);
    }

    @Override
    protected OrderContract.IMyCarPresenter presenter() {
        return new MyCarPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_my_car_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        myShoppingExpandableListAdapter = new MyShoppingExpandableListAdapter(this, carList);
        expandableListView.setAdapter(myShoppingExpandableListAdapter);
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myShoppingExpandableListAdapter.setOnGoodsCheckedChangeListener(new MyShoppingExpandableListAdapter.OnGoodsCheckedChangeListener() {
            @Override
            public void onGoodsCheckedChange(int totalCount, String totalPrice) {
                allMoney.setText("¥" + totalPrice);
                allCount.setText("结算（" + totalCount + "）");
            }
        });

        myShoppingExpandableListAdapter.setOnAllCheckedBoxNeedChangeListener(new MyShoppingExpandableListAdapter.OnAllCheckedBoxNeedChangeListener() {
            @Override
            public void onCheckedBoxNeedChange(boolean allParentIsChecked) {
                allSelect.setChecked(allParentIsChecked);
            }
        });

        myShoppingExpandableListAdapter.setOnDeleteGoodsListener(new MyShoppingExpandableListAdapter.OnDeleteGoodsListener() {
            @Override
            public void delete(String cardId) {
                ShopCarIn shopCarIn = new ShopCarIn();
                shopCarIn.setCart_id(cardId);
                getPresenter().del(shopCarIn);
            }
        });

        myShoppingExpandableListAdapter.setOnGoodsNumChangeListener(new MyShoppingExpandableListAdapter.OnGoodsNumChangeListener() {
            @Override
            public void onChange(String cardId, int num) {
                ShopCarIn shopCarIn = new ShopCarIn();
                shopCarIn.setProduct_num(num);
                shopCarIn.setCart_id(cardId);
                getPresenter().update(shopCarIn);
            }
        });

        myShoppingExpandableListAdapter.setOnItemClickListener(new MyShoppingExpandableListAdapter.OnItemClickListener() {
            @Override
            public void item(int first, int second) {
                Intent p = new Intent(context, ProductDetailActivity.class);
                p.putExtra("product_id", carList.get(first).getProducts().get(second).getProduct_id());
                startActivity(p);
            }
        });

        onRefresh();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    /**
     * 刷新
     */
    private void onRefresh() {
        getPresenter().queryShoppingCar();
    }

    @Override
    public void queryShoppingCarResult(List<ShopCarList> shopCarLists) {
        carList.clear();
        carList.addAll(shopCarLists);

        for (int i = 0; i < carList.size(); i++) {
            expandableListView.expandGroup(i);
        }
        myShoppingExpandableListAdapter.notifyDataSetChanged();
    }

    @Override
    public void delResult(String msg) {
        T.showShort(context, msg);
    }

    @Override
    public void updateResult(String msg) {
        T.showShort(context, msg);
    }

    @OnClick(R.id.app_all_select)
    void clickAllSelect(CheckBox checkBox){
        myShoppingExpandableListAdapter.setupAllChecked(checkBox.isChecked());
    }

    @OnClick(R.id.app_count)
    void clickCount(){
        List<String> ids = new ArrayList<>();
        for (ShopCarList shopCarList : carList){
            for (ShopCarDetail shopCarDetail : shopCarList.getProducts()){
                if (shopCarDetail.isSelected()){
                    ids.add(shopCarDetail.getCart_id());
                }
            }
        }
        if (null == ids || ids.isEmpty()){
            T.showShort(context, "请选择购买的商品");
            return;
        }
        Intent intent = new Intent(context, MutiConfirmOrderActivity.class);
        intent.putStringArrayListExtra("ids", (ArrayList<String>) ids);
        startActivity(intent);
    }
}
