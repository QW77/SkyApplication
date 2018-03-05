package com.sky.app.ui.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.sky.app.R;
import com.sky.app.bean.AreaDetail;
import com.sky.app.bean.AreaIn;
import com.sky.app.bean.ProvCityArea;
import com.sky.app.contract.MineContract;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.AreaPresenter;
import com.sky.app.ui.adapter.SelectAreaAdaptor;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 省
 */
public class ProvActivity extends BaseViewActivity<MineContract.IAreaPresenter>
        implements MineContract.IAreaView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_area_list)
    RecyclerView recyclerView;

    SelectAreaAdaptor selectAreaAdaptor;
    List<AreaDetail> areaDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_select_area);
    }

    @Override
    protected MineContract.IAreaPresenter presenter() {
        return new AreaPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_area_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 1),
                        AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        selectAreaAdaptor = new SelectAreaAdaptor(context, areaDetails);
        selectAreaAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                ProvCityArea provCityArea = new ProvCityArea();
                Intent i = new Intent(context, CityActivity.class);
                Bundle bundle = new Bundle();
                provCityArea.setPro_id(areaDetails.get(pos).getId());
                provCityArea.setPro_name(areaDetails.get(pos).getName());
                bundle.putSerializable("city", provCityArea);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recyclerView.setAdapter(selectAreaAdaptor);
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
    private void onRefresh(){
        AreaIn areaIn = new AreaIn();
        areaIn.setLevel("0");
        getPresenter().get(areaIn);
    }

    @Override
    public void showSuccess(List<AreaDetail> list) {
        selectAreaAdaptor.add(list);
        selectAreaAdaptor.notifyDataSetChanged();
    }
}
