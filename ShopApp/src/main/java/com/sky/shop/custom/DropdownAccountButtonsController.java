package com.sky.shop.custom;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sky.shop.R;
/**
 * 账户下拉框
 */
public class DropdownAccountButtonsController {
    public Context context = null;
    private static DropdownAccountButtonsController instance = null;
    private View driverBgLayout;//背景
    private DropdownButton statusBtn, queryBtn;//按钮
    private ListView driverListSortDv;//排序
    OnClickItem onClickItem;
    private int currentTab = -1;

    //默认动画
    Animation driverDropdownIn, driverDropdownOut, driverDropdownBgOut;

    public static DropdownAccountButtonsController getInstance(){
        if (null == instance){
            synchronized (DropdownAccountButtonsController.class){
                if (null == instance){
                    instance = new DropdownAccountButtonsController();
                }
            }
        }
        return instance;
    }

    public DropdownAccountButtonsController(){
    }

    /**
     * 设置排序文字
     */
    public void setSortTxt(String word){
        statusBtn.setText(word);
    }

    /**
     * 重置窗口效果
     */
    private void reset() {
        //设置文字
        statusBtn.setText("所有交易状态");
        queryBtn.setText("时间查询");

        //按钮恢复
        statusBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_account_unselected_arrow_icon), false, false);
        statusBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        queryBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_date_icon), false, false);
        queryBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        //背景
        driverBgLayout.setVisibility(View.GONE);

        //排序下拉框
        driverListSortDv.setVisibility(View.GONE);

        //清除动画
        driverListSortDv.clearAnimation();
        driverBgLayout.clearAnimation();
    }

    /**
     * 显示下拉框
     */
    public void show(int i) {
        if (currentTab == i) return;

        //隐藏当前的控件
        hide();

        currentTab = i;
        switch (i){
            case 0://排序
                driverBgLayout.clearAnimation();
                driverBgLayout.setVisibility(View.VISIBLE);

                driverListSortDv.clearAnimation();
                driverListSortDv.startAnimation(driverDropdownIn);
                driverListSortDv.setVisibility(View.VISIBLE);
                statusBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_account_unselected_arrow_icon), true, false);
                queryBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_date_icon), false, false);
                break;
            case 1://时间
                statusBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_account_unselected_arrow_icon), false, false);
                queryBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_date_icon), true, false);
                break;
        }
    }

    /**
     * 隐藏下拉框
     */
    public void hide() {
        if (-1 == currentTab) return;
        switch (currentTab){
            case 0://排序
                driverListSortDv.clearAnimation();
                driverListSortDv.startAnimation(driverDropdownOut);
                driverListSortDv.setVisibility(View.GONE);

                //背景
                driverBgLayout.clearAnimation();
                driverBgLayout.startAnimation(driverDropdownBgOut);
                driverBgLayout.setVisibility(View.GONE);
                break;
            case 1://
//                queryBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.driver_date_icon), false, false);
                break;
        }
        currentTab = -1;
    }

    /**
     * 初始化窗口动画
     */
    public void initAnim(){
        driverDropdownIn = AnimationUtils.loadAnimation(context, R.anim.app_dropdown_in);
        driverDropdownOut = AnimationUtils.loadAnimation(context, R.anim.app_dropdown_out);
        driverDropdownBgOut = AnimationUtils.loadAnimation(context, R.anim.app_dropdown_mask_out);
    }

    /**
     * 初始化操作
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        showListView();

        //重置视图
        reset();

        //初始化动画
        initAnim();
    }

    /**
     * 展示默认排序列表
     */
    private void showListView(){
        final String[] arrays = {"所有交易状态", "交易完成", "交易关闭"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.app_dropdown_status_item, arrays);
        driverListSortDv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != onClickItem){
                    onClickItem.item(arrays, position);
                }
            }
        });
        driverListSortDv.setAdapter(adapter);
    }

    public View getDriverBgLayout() {
        return driverBgLayout;
    }

    public void setDriverBgLayout(View driverBgLayout) {
        this.driverBgLayout = driverBgLayout;
    }

    public DropdownButton getStatusBtn() {
        return statusBtn;
    }

    public void setStatusBtn(DropdownButton statusBtn) {
        this.statusBtn = statusBtn;
    }

    public DropdownButton getQueryBtn() {
        return queryBtn;
    }

    public void setQueryBtn(DropdownButton queryBtn) {
        this.queryBtn = queryBtn;
    }

    public ListView getDriverListSortDv() {
        return driverListSortDv;
    }

    public void setDriverListSortDv(ListView driverListSortDv) {
        this.driverListSortDv = driverListSortDv;
    }

    /**
     * 回调接口
     */
    public interface OnClickItem{
        void item(String[] arrays, int position);
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }
}