package com.sky.shop.ui.activity.shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.AreaList;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.DecorationCityList;
import com.sky.shop.bean.FirstCategoryDetail;
import com.sky.shop.bean.FirstCategoryOut;
import com.sky.shop.bean.SellMessageComplete;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.SearchByPlaceActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SellerSecondCategoryActivity extends BaseViewActivity<ShopContract
        .ISearchByPlacePresenter>
        implements ShopContract.ISearchByPlace {
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_merchants_type)
    TextView tvType;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_decorative_city)
    TextView tvDecorytiveCity;
    @BindView(R.id.tv_factory)
    TextView tvFactory;
    @BindView(R.id.app_title)
    TextView appTitle;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.seller_second_category_next)
    TextView sellerSecondCategoryNext;
    @BindView(R.id.activity_seller_second_category)
    LinearLayout activitySellerSecondCategory;

    private String area_id;
    private String area_name;
    private String decorative_id;
    private String decorative_name;
    private String manufacturer_type_id;
    private String manufacturer_type_name;
    private String type;
    private String one_dir_id;
    private List<String> keysString = new ArrayList<>();
    private AreaList areaList;
    private DecorationCityList decorationCityList;
    private CategoryList categoryList;
    private CategoryList factoryList;
    private List<FirstCategoryDetail> firstCategoryDetailList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_second_category);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        if ("person".equals(type)) {//个人
            tvDecorytiveCity.setVisibility(View.GONE);
            tvFactory.setVisibility(View.GONE);
        }
    }

    @Override
    protected ShopContract.ISearchByPlacePresenter presenter() {
        return new SearchByPlaceActivityPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected void init() {
        appTitle.setText("选择行业属性");
        normalToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        normalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getPresenter().getData();
        getPresenter().getFirstCatogoryData();
        getPresenter().requestCompanyType();
    }

    @Override
    public void success(final AreaList areaList) {
        this.areaList = areaList;
    }

    @Override
    public void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut) {
//        firstCategoryDetailList.addAll(firstCategoryOut.getList());
        if ("company".equals(type)) {//企业
            for (int i = 0; i < firstCategoryOut.getList().size(); i++) {
                switch (firstCategoryOut.getList().get(i).getOne_dir_id()) {
                    case "FW500001":
                        firstCategoryDetailList.add(firstCategoryOut.getList().get(i));
                        break;
                    case "FW500002":
                        firstCategoryDetailList.add(firstCategoryOut.getList().get(i));
                        break;
                    case "FW500003":
                        firstCategoryDetailList.add(firstCategoryOut.getList().get(i));
                        break;
                    case "FW500006":
                        firstCategoryDetailList.add(firstCategoryOut.getList().get(i));
                        break;
                }
            }

        } else if ("person".equals(type)) {//个人
            for (int i = 0; i < firstCategoryOut.getList().size(); i++) {
                switch (firstCategoryOut.getList().get(i).getOne_dir_id()) {
                    case "FW500001":
                        break;
                    case "FW500002":
                        break;
                    case "FW500003":
                        break;
                    case "FW500006":
                        break;
                    default:
                        firstCategoryDetailList.add(firstCategoryOut.getList().get(i));
                        break;

                }
            }
        } else {
            firstCategoryDetailList.addAll(firstCategoryOut.getList());
        }
    }

    @Override
    public void successDecrationCity(DecorationCityList decorationCityList) {
        this.decorationCityList = decorationCityList;
    }

    @Override
    public void secondCatogoryDataSuccess(CategoryList categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public void responseCompanyType(CategoryList categoryList) {
        this.factoryList = categoryList;
    }


    @OnClick(R.id.tv_industry)
    public void onclickIndustry() {
        if (categoryList == null) {
            Toast.makeText(context, "请先选择商户类型！", Toast.LENGTH_SHORT).show();
            return;
        } else {

            final String items[] = new String[categoryList.getList().size()];
            for (int i = 0; i < categoryList.getList().size(); i++) {
                items[i] = categoryList.getList().get(i).getTwo_dir_name();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("选择行业分类");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (isRepeat(items[which])) {
                        T.showShort(context, "该分类已选择");
                        return;
                    }
                    keysString.add(items[which]);
                    tvIndustry.setText("行业分类：" + items[which]);
                }
            });
            builder.create().show();
        }
    }


    @OnClick(R.id.tv_merchants_type)
    public void onclickType() {
        final String items[] = new String[firstCategoryDetailList.size()];
        for (int i = 0; i < firstCategoryDetailList.size(); i++) {
            items[i] = firstCategoryDetailList.get(i).getOne_dir_name();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择商户类型");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                tvType.setText("商户类型：" + items[which]);
                one_dir_id = firstCategoryDetailList.get(which).getOne_dir_id();
                getPresenter().getSecondCatogoryData(firstCategoryDetailList.get(which).getOne_dir_id());

            }
        });
        builder.create().show();
    }

    @OnClick(R.id.tv_area)
    public void onclickArea() {
        final String items[] = new String[areaList.getList().size()];
        for (int i = 0; i < areaList.getList().size(); i++) {
            items[i] = areaList.getList().get(i).getTwo_dir_name();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择所在区域");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                area_id = areaList.getList().get(which).getTwo_dir_id();
                area_name = areaList.getList().get(which).getTwo_dir_name();
                getPresenter().getDecrationCity(areaList.getList().get(which).getTwo_dir_id());
                tvArea.setText("商户区域：" + items[which]);
            }
        });
        builder.create().show();
    }

    @OnClick(R.id.tv_decorative_city)
    public void onclickDecorativeCity() {
        if (decorationCityList != null && decorationCityList.getList().size() > 0) {
            final String items[] = new String[decorationCityList.getList().size()];
            for (int i = 0; i < decorationCityList.getList().size(); i++) {
                items[i] = decorationCityList.getList().get(i).getThree_dir_name();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("选择所属装饰城");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    decorative_id = decorationCityList.getList().get(which).getThree_dir_id();
                    decorative_name = decorationCityList.getList().get(which).getThree_dir_name();
                    tvDecorytiveCity.setText("装饰城：" + items[which]);
                }
            });
            builder.create().show();
            return;
        } else
            Toast.makeText(context, "请先选择区域或该区域没有装饰城！", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_factory)
    public void onclickFactory() {
        final String items[] = new String[factoryList.getList().size()];
        for (int i = 0; i < factoryList.getList().size(); i++) {
            items[i] = factoryList.getList().get(i).getOne_dir_name();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择工厂类型");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                manufacturer_type_id = factoryList.getList().get(which).getOne_dir_id();
                manufacturer_type_name = factoryList.getList().get(which).getOne_dir_name();
                tvFactory.setText("所属工厂：" + items[which]);
            }
        });
        builder.create().show();
    }

    /**
     * 逗号组合数据
     *
     * @return
     */
    private String group(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stringList) {
            stringBuilder.append(s + ",");
        }
        if (!stringList.isEmpty()) {
            return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
        }
        return "";
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

    /**
     * 类型添加是否重复
     *
     * @return
     */
    private boolean isRepeat(String s) {
        for (String key : keysString) {
            if (key.equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestoryActivity() {
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @OnClick(R.id.seller_second_category_next)
    public void onClick() {


        if ("company".equals(type)) {//企业
            if ("".equals(type)) {
                T.showShort(context, "请选择经营类型");
                return;
            }
            if (tvIndustry.getText().equals("选择行业分类")) {
                T.showShort(context, "请选择行业分类");
                return;
            }
            if (TextUtils.isEmpty(area_id)) {
                T.showShort(context, "请选择所属区域");
                return;
            }
            if (TextUtils.isEmpty(decorative_name)) {
                T.showShort(context, "请选择所属装饰城");
                return;
            }

            Intent intent = new Intent(this, SellerUploadYinYeZhiZhaoActivity.class);
            SellMessageComplete upload = new SellMessageComplete();
            upload.setKey_words(group(keysString));
            upload.setOne_dir_id(one_dir_id);
            upload.setArea_id(area_id);
            upload.setArea_name(area_name);
            upload.setDecorative_id(decorative_id);
            upload.setDecorative_name(decorative_name);
            upload.setManufacturer_type_id(manufacturer_type_id);
            upload.setManufacturer_type_name(manufacturer_type_name);
            intent.putExtra("upload", upload);
            startActivity(intent);
        } else if ("person".equals(type)) {//个人
            if ("".equals(type)) {
                T.showShort(context, "请选择经营类型");
                return;
            }
            if (tvIndustry.getText().equals("选择行业分类")) {
                T.showShort(context, "请选择行业分类");
                return;
            }
            if (TextUtils.isEmpty(area_id)) {
                T.showShort(context, "请选择所属区域");
                return;
            }
            Intent intent = new Intent(this, SellerUploadShengFenZhenActivity.class);
            SellMessageComplete upload = new SellMessageComplete();
//            upload.setKey_words(group(keysString));
            upload.setOne_dir_id(one_dir_id);
            upload.setArea_id(area_id);
            upload.setArea_name(area_name);
            upload.setDecorative_id(decorative_id);
            upload.setDecorative_name(decorative_name);
            upload.setManufacturer_type_id(manufacturer_type_id);
            upload.setManufacturer_type_name(manufacturer_type_name);
            intent.putExtra("upload", upload);
            startActivity(intent);
        }
    }
}
