package com.sky.shop.custom;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.shop.R;
import com.sky.shop.adaptor.ProductEditCategoryAdaptor;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.bean.ProductDeatilResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类
 */
public class CategoryMenuDialog extends Dialog{
    private Context mContext;
    private LayoutInflater inflater;
    private List<ProductCategory> productCategories;
    private ProductEditCategoryAdaptor productCategoryAdaptor, productCategory2Adaptor;
    private RecyclerView firstCategory, secondCategory;
    private List<ProductCategory> categories = new ArrayList<>();
    private List<ProductCategory> categories2 = new ArrayList<>();
    String one;
    String one_name;

    private CallBack callBack;

    /**
     * 构造器
     * @param context
     */
    public CategoryMenuDialog(Context context, List<ProductCategory> productCategories, CallBack callBack) {
        super(context, R.style.product_category_dialog);//样式
        this.mContext = context;
        this.productCategories = productCategories;
        this.callBack = callBack;

        /**
         * 初始化视图
         */
        initView();

        /**
         * 窗口初始化设置
         */
        windowDeploy();
        setCanceledOnTouchOutside(true);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        this.inflater = LayoutInflater.from(mContext);
        setContentView(inflater.inflate(R.layout.app_product_category, null));
        firstCategory = (RecyclerView) findViewById(R.id.first_category);
        firstCategory.setLayoutManager(new LinearLayoutManager(mContext));
        firstCategory.setHasFixedSize(true);
        secondCategory = (RecyclerView) findViewById(R.id.second_category);
        secondCategory.setLayoutManager(new LinearLayoutManager(mContext));
        secondCategory.setHasFixedSize(true);

        productCategoryAdaptor = new ProductEditCategoryAdaptor(mContext, categories, 1);
        productCategoryAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                one = productCategories.get(pos).getOne_dir_id();
                one_name = productCategories.get(pos).getOne_dir_name();
                productCategoryAdaptor.setSelectedPos(pos);
                productCategoryAdaptor.notifyDataSetChanged();
                final List<ProductCategory> productCategories = getSecondCategory(categories.get(pos).getOne_dir_id());
                if (null == productCategories || productCategories.isEmpty()){
                    callBack.data(one, "",one_name,"");
                    dismiss();
                    if (null != productCategory2Adaptor){
                        productCategory2Adaptor.clear();
                    }
                    return;
                }
                if (null == productCategory2Adaptor){
                    productCategory2Adaptor = new ProductEditCategoryAdaptor(mContext, categories2, 2);
                    productCategory2Adaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int pos) {
                            productCategoryAdaptor.setSelectedPos2(pos);
                            callBack.data(one, categories2.get(pos).getTwo_dir_id(),one_name,categories2.get(pos).getTwo_dir_name());
                            dismiss();
                        }
                    });
                    secondCategory.setAdapter(productCategory2Adaptor);
                }
                productCategory2Adaptor.add(productCategories);
                productCategory2Adaptor.notifyDataSetChanged();
            }
        });
        firstCategory.setAdapter(productCategoryAdaptor);
        productCategoryAdaptor.add(productCategories);
    }

    /**
     * 设置窗口显示
     */
    public void windowDeploy() {
        Window window = getWindow(); // 得到对话框
        window.setWindowAnimations(R.style.product_category_animation); // 设置窗口弹出动画效果
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.x = 0; // x小于0左移，大于0右移
        windowAttributes.y = 0; // y小于0上移，大于0下移
        windowAttributes.height = AppUtils.dip2px(mContext, 446);
        windowAttributes.width = LinearLayout.LayoutParams.MATCH_PARENT;
//        windowAttributes.alpha = 0.6f; //设置透明度
        windowAttributes.gravity = Gravity.BOTTOM; // 设置重力，对齐方式
        window.setAttributes(windowAttributes);
    }

    @Override
    public void show() {
        if (!isShowing()){
            super.show();
        }
    }

    @Override
    public void hide() {
        if (isShowing()){
            super.dismiss();
        }
    }

    /**
     * 获取二级分类
     * @return
     */
    public List<ProductCategory> getSecondCategory(String id){
        if (null == productCategories || productCategories.isEmpty()){
            return null;
        }
        for (ProductCategory productCategory : productCategories){
            if (id.equals(productCategory.getOne_dir_id())){
                return productCategory.getTwo_dirs();
            }
        }
        return null;
    }

    public interface CallBack{
        void data(String one, String two,String one_name,String two_name);
    }
}
