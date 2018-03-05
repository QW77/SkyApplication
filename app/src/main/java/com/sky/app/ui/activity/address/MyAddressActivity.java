package com.sky.app.ui.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.sky.app.R;
import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.AddressIn;
import com.sky.app.contract.MineContract;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.MyAddressPresenter;
import com.sky.app.ui.adapter.MyAddressAdaptor;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的收货地址
 */
public class MyAddressActivity extends BaseViewActivity<MineContract.IMyAddressPresenter>
        implements MineContract.IMyAddressView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_address_list)
    RecyclerView recyclerView;

    MyAddressAdaptor myAddressAdaptor;
    List<AddressDetail> addressDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_mine_my_address);
    }

    @Override
    protected MineContract.IMyAddressPresenter presenter() {
        return new MyAddressPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_my_address_string);
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
                new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 12),
                        AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        myAddressAdaptor = new MyAddressAdaptor(context, addressDetails);
        myAddressAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
                switch (itemView.getId()){
                    case R.id.app_del://删除
                        AddressIn addressIn = new AddressIn();
                        addressIn.setAddress_id(addressDetails.get(pos).getAddress_id());
                        getPresenter().del(addressIn);
                        break;
                    case R.id.app_edit://编辑
                        Intent i = new Intent(context, MyAddressDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("address", addressDetails.get(pos));
                        i.putExtra("flag", "3");
                        i.putExtras(bundle);
                        startActivity(i);
                        break;
                    case R.id.app_check://设置收货地址
                        AddressIn a = new AddressIn();
                        a.setAddress_id(addressDetails.get(pos).getAddress_id());
                        getPresenter().setDefaultAddress(a);
                        break;
                }
            }
        });
        myAddressAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Intent i = new Intent(context, MyAddressDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address", addressDetails.get(pos));
                i.putExtra("flag", "2");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recyclerView.setAdapter(myAddressAdaptor);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void reFreshData(List<AddressDetail> addressDetailList) {
        myAddressAdaptor.add(addressDetailList);
        myAddressAdaptor.notifyDataSetChanged();
    }

    @Override
    public void showSetAddressSuccess(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void showDelAddressSuccess(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    /**
     * 刷新
     */
    private void onRefresh(){
        getPresenter().loadData();
    }

    @OnClick(R.id.app_add_address)
    void addAddress(){
        startActivity(new Intent(context, MyAddressDetailActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }
}
