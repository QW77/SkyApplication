package com.sky.app.ui.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AreaList;
import com.sky.app.bean.Category;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.bean.SellMessageComplete;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.SearchByPlaceActivityPresenter;
import com.sky.app.ui.activity.search.AreaGridviewAdapter;
import com.sky.app.ui.adapter.SeconCategoryViewPagerAdapter;
import com.sky.app.ui.adapter.SecondCategoryGridviewAdapter;
import com.sky.app.ui.custom.AutoHeightGridView;
import com.sky.app.ui.custom.AutoHeightViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SellerSecondCategoryActivity extends BaseViewActivity<UserContract
        .ISearchByPlacePresenter>
        implements UserContract.ISearchByPlace {

    @BindView(R.id.second_category_viewpager)
    AutoHeightViewPager secondCategoryViewpager;

    ArrayList<View> mViewList = new ArrayList<View>();
    @BindView(R.id.dot_container)
    LinearLayout dotContainer;
    @BindView(R.id.seller_second_category_gridview_area)
    AutoHeightGridView sellerSecondCategoryGridviewArea;
    @BindView(R.id.seller_second_category_gridview_decoratio)
    AutoHeightGridView sellerSecondCategoryGridviewDecoratio;
    @BindView(R.id.seller_second_category_next)
    TextView sellerSecondCategoryNext;
    @BindView(R.id.app_title)
    TextView appTitle;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.activity_seller_second_category)
    LinearLayout activitySellerSecondCategory;
    @BindView(R.id.second_categroy_tv)
    TextView secondCategroyTv;
    @BindView(R.id.second_categroy_reala)
    RelativeLayout secondCategroyReala;
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.area_reala)
    RelativeLayout areaReala;
    @BindView(R.id.decoration_tv)
    TextView decorationTv;
    @BindView(R.id.decoration_reala)
    RelativeLayout decorationReala;

    private String one_dir_id;
    private String one_dir_id_name;
    private String two_dir_id;
    private String three_dir_id;
    private String decorationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_second_category);
    }

    @Override
    protected UserContract.ISearchByPlacePresenter presenter() {
        return new SearchByPlaceActivityPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void init() {
        appTitle.setText("选择行业分类");
        normalToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        normalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getPresenter().getData("");
        SellMessageComplete upload = (SellMessageComplete) getIntent().getSerializableExtra
                ("upload");
        getPresenter().getSecondCatogoryData(upload.getOne_dir_id());
        getPresenter().getDecrationCity("");

    }

    @Override
    public void firstCatogoryDataSuccess(List<FirstCategoryDetail> list) {

    }

    @Override
    public void success(final AreaList areaList) {
        ArrayList<String> areaName = new ArrayList<>();
        for (int i = 0; i < areaList.getList().size(); i++) {
            areaName.add(areaList.getList().get(i).getTwo_dir_name());
        }
        sellerSecondCategoryGridviewArea.setAdapter(new AreaGridviewAdapter(areaName,
                SellerSecondCategoryActivity.this));
        sellerSecondCategoryGridviewArea.setOnItemClickListener(new AdapterView
                .OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                one_dir_id = areaList.getList().get(position).getOne_dir_id();
                one_dir_id_name = areaList.getList().get(position).getTwo_dir_name();
                areaReala.setVisibility(View.VISIBLE);
                areaTv.setText(one_dir_id_name);

            }
        });
    }

    @Override
    public void successDecrationCity(final DecorationCityList categoryList) {
        ArrayList<String> areaName = new ArrayList<>();
        for (int i = 0; i < categoryList.getList().size(); i++) {
            areaName.add(categoryList.getList().get(i).getThree_dir_name());
        }
        sellerSecondCategoryGridviewDecoratio.setAdapter(new AreaGridviewAdapter(areaName,
                SellerSecondCategoryActivity.this));

        sellerSecondCategoryGridviewDecoratio.setOnItemClickListener(new AdapterView
                .OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                three_dir_id = categoryList.getList().get(position).getThree_dir_id();
                decorationName = categoryList.getList().get(position).getThree_dir_name();
                decorationReala.setVisibility(View.VISIBLE);
                decorationTv.setText(decorationName);
            }
        });
    }

    @Override
    public void secondCatogoryDataSuccess(final CategoryList categoryList) {
        ArrayList<Category> categories = null;
        int page = 0;
        int i = categoryList.getList().size() % 8;

        if (i == 0) {
            page = categoryList.getList().size() / 8;
        } else {
            page = categoryList.getList().size() / 8 + 1;
        }

        for (int x = 0; x < page; x++) {
            ImageView imageView = new ImageView(this);
            View inflate = getLayoutInflater().inflate(R.layout.item_gridview_second_category,
                    null);
            mViewList.add(inflate);

            if (page > 1) {
                dotContainer.setVisibility(View.VISIBLE);
                if (x == 0) {
                    imageView.setBackgroundResource(R.drawable.select_dot);
//                imageView.setBackgroundColor(getResources().getColor(R.color
// .design_textinput_error_color_light));
                } else {
                    imageView.setBackgroundResource(R.drawable.normal_dot);
//                imageView.setBackgroundColor(getResources().getColor(R.color
// .design_textinput_error_color_light));

                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
                layoutParams.setMargins(0, 0, 20, 0);
                imageView.setLayoutParams(layoutParams);
                dotContainer.addView(imageView);
            } else {
                dotContainer.setVisibility(View.GONE);
            }

        }


        for (int x = 0; x < page; x++) {
            final ArrayList<Category> categories1 = new ArrayList<>();
//            for (int y = x * 8; y < categoryList.getList().size(); y++) {
//                if (categories1.size() == 8) {
//                    break;
//                }
//                categories1.add(categoryList.getList().get(y));
//            }

            //ssss
            AutoHeightGridView view = (AutoHeightGridView) mViewList.get(x).findViewById(R.id.gridview);
            view.setAdapter(new SecondCategoryGridviewAdapter(categories1, SellerSecondCategoryActivity.this));
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    T.showShort(SellerSecondCategoryActivity.this, categories1.get(position).getTwo_dir_name());
                    two_dir_id = categories1.get(position).getTwo_dir_id();
                    secondCategroyReala.setVisibility(View.VISIBLE);
                    secondCategroyTv.setText(categories1.get(position).getTwo_dir_name());
                }
            });


            secondCategoryViewpager.setAdapter(new SeconCategoryViewPagerAdapter
                    (SellerSecondCategoryActivity.this, mViewList));


            secondCategoryViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int
                        positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 1; i < dotContainer.getChildCount(); i++) {
                        View childAt = dotContainer.getChildAt(i);
                        if (position == i) {
                            if (childAt != null) {
                                childAt.setBackgroundResource(R.drawable.select_dot);
                            }
                        } else {
                            childAt.setBackgroundResource(R.drawable.normal_dot);
                        }

                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    public void userDataSuccess(UserBeanList userBeanList) {

    }

    @OnClick(R.id.seller_second_category_next)
    public void onClick() {

        if (TextUtils.isEmpty(areaTv.getText()) || TextUtils.isEmpty(secondCategroyTv.getText())) {

            T.showShort(SellerSecondCategoryActivity.this, "请选择");
            return;
        } else {
            Intent intent = new Intent(this, SellerUploadShengFenZhenActivity.class);
            SellMessageComplete upload = (SellMessageComplete) getIntent().getSerializableExtra
                    ("upload");
            upload.setTwo_dir_id(two_dir_id);
            upload.setArea_id(one_dir_id);
            upload.setArea_name(one_dir_id_name);
            upload.setDecorative_id(three_dir_id);
            upload.setDecorative_name(decorationName);
            intent.putExtra("upload", upload);
            startActivity(intent);
        }


    }

    @OnClick({R.id.second_categroy_reala, R.id.area_reala, R.id.decoration_reala})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.second_categroy_reala:
                secondCategroyReala.setVisibility(View.GONE);
                break;
            case R.id.area_reala:
                areaReala.setVisibility(View.GONE);
                break;
            case R.id.decoration_reala:
                decorationReala.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onDestoryActivity() {

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
