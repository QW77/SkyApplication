package com.sky.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.ProductDeatilResponse;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.ui.activity.product.ProductDetailActivity;
import com.sky.app.ui.adapter.SelectCategoryAdaptor;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 进入购买商品选择页
 * Created by sky on 2017/3/18.
 */

public class BuyDialog extends Dialog{
    private Activity activity = null;

    private RecyclerView recyclerView;
    private ImageView productImage;
    private TextView productPrice;
    private TextView plus;
    private TextView minus;
    private TextView num;
    private TextView joinCar;
    private TextView buy;
    private ProductDeatilResponse productDeatilResponse;
    private SelectCategoryAdaptor selectCategoryAdaptor;

    private ProductDetailActivity.ISelectCallBack iSelectCallBack;

    private int count = 1;
    private int position = -1;

    public BuyDialog(@NonNull Activity activity, ProductDeatilResponse productDeatilResponse,
                     ProductDetailActivity.ISelectCallBack iSelectCallBack) {
        super(activity, R.style.CustomDialog);
        this.activity = activity;
        this.productDeatilResponse = productDeatilResponse;
        this.iSelectCallBack = iSelectCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_select_product_category);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = AppUtils.getScreenWidth(activity);//设置宽度
        getWindow().setAttributes(lp);

        setCanceledOnTouchOutside(true);
        setCancelable(true);

        //初始化
        initView();
        initClick();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ImageHelper.getInstance().displayDefinedImage(productDeatilResponse.getProduct_image_url(),
                productImage, R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        productPrice.setText("¥" + AppUtils.parseDouble(productDeatilResponse.getProduct_price_now()/100));
        num.setText(count + "");
    }

    /**
     * 初始化事件
     */
    private void initClick() {
        plus.setOnClickListener(onClick);
        minus.setOnClickListener(onClick);
        joinCar.setOnClickListener(onClick);
        buy.setOnClickListener(onClick);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        productImage = (ImageView) findViewById(R.id.app_product_image);
        productPrice = (TextView) findViewById(R.id.product_size_price);
        recyclerView = (RecyclerView) findViewById(R.id.app_product_category_list);
        plus = (TextView) findViewById(R.id.app_plus);
        minus = (TextView) findViewById(R.id.app_minus);
        num = (TextView) findViewById(R.id.app_num);
        joinCar = (TextView) findViewById(R.id.product_size_join_car);
        buy = (TextView) findViewById(R.id.product_size_buynow);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        selectCategoryAdaptor = new SelectCategoryAdaptor(activity, productDeatilResponse.getAttrs());
        selectCategoryAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                position = pos;
                selectCategoryAdaptor.setSelectedPos(pos);
                selectCategoryAdaptor.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(selectCategoryAdaptor);
    }

    /**
     * 点击事件
     */
    private View.OnClickListener onClick = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.app_plus) {
                if (position == -1){
                    T.showShort(activity, "请选择商品分类");
                    return;
                }
                if (productDeatilResponse.getAttrs().get(position).getInventory_num() < count + 1){
                    T.showShort(activity, "库存不足");
                    return;
                }
                count++;
                num.setText(count + "");
            } else if (i == R.id.app_minus) {
                if (count < 2){
                    T.showShort(activity, "购买商品数量不能少于1件");
                    return;
                }
                count--;
                num.setText(count + "");
            } else if (i == R.id.product_size_join_car) {
                if (position == -1){
                    T.showShort(activity, "请选择商品分类");
                    return;
                }
                if (null != iSelectCallBack){
                    iSelectCallBack.data(1, position, count);
                }
            } else if (i == R.id.product_size_buynow){
                if (position == -1){
                    T.showShort(activity, "请选择商品分类");
                    return;
                }
                if (null != iSelectCallBack){
                    iSelectCallBack.data(2, position, count);
                }
            }
        }
    };

}
