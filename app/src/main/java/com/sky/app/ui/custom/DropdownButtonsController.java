package com.sky.app.ui.custom;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.sky.app.R;
import com.sky.app.library.utils.AppUtils;

public class DropdownButtonsController{
    public Context context = null;
    private static DropdownButtonsController instance = null;

    private View bgLayout;//背景
    private DropdownButton defaultSortBtn, sellBtn, filterBtn;//按钮
    private ListView listSortDv;//排序
    private RelativeLayout filterLayout;//筛选下拉框
    OnClickItem onClickItem;
    private int currentTab = -1;

    //默认动画
    Animation dropdownIn, dropdownOut, dropdownBgOut;

    public static DropdownButtonsController getInstance(){
        if (null == instance){
            synchronized (DropdownButtonsController.class){
                if (null == instance){
                    instance = new DropdownButtonsController();
                }
            }
        }
        return instance;
    }

    public DropdownButtonsController(){
    }

    /**
     * 设置排序文字
     */
    public void setSortTxt(String word){
        defaultSortBtn.setText(word);
    }

    /**
     * 重置窗口效果
     */
    private void reset() {
        //设置文字
        defaultSortBtn.setText("默认排序");
        sellBtn.setText("销量优先");
        filterBtn.setText("筛选");

        //按钮恢复
        sellBtn.setChecked(null, false, true);
        defaultSortBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_search_up_arrow_icon), false, true);
        filterBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_search_filter_icon), false, true);

        //背景
        bgLayout.setVisibility(View.GONE);

        //排序下拉框/筛选下拉框
        listSortDv.setVisibility(View.GONE);
        filterLayout.setVisibility(View.GONE);

        //清除动画
        defaultSortBtn.clearAnimation();
        filterBtn.clearAnimation();
        bgLayout.clearAnimation();
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
                bgLayout.clearAnimation();
                bgLayout.setVisibility(View.VISIBLE);
                defaultSortBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_search_up_arrow_icon), true, true);
                listSortDv.clearAnimation();
                listSortDv.startAnimation(dropdownIn);
                listSortDv.setVisibility(View.VISIBLE);
                filterBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_search_filter_icon), false, true);
                break;
            case 1://距离优先
                sellBtn.setChecked(null, true, true);
                defaultSortBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_search_up_arrow_icon), false, true);
                filterBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_search_filter_icon), false, true);
                break;
            case 2://筛选
                bgLayout.clearAnimation();
                bgLayout.setVisibility(View.VISIBLE);
                filterBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_search_filter_icon), true, true);
                filterLayout.clearAnimation();
                filterLayout.startAnimation(dropdownIn);
                filterLayout.setVisibility(View.VISIBLE);
                defaultSortBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.app_search_up_arrow_icon), false, true);
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
                //tab按钮
                listSortDv.clearAnimation();
                listSortDv.startAnimation(dropdownOut);
                listSortDv.setVisibility(View.GONE);

                //背景
                bgLayout.clearAnimation();
                bgLayout.startAnimation(dropdownBgOut);
                bgLayout.setVisibility(View.GONE);
                break;
            case 1://距离优先
                sellBtn.setChecked(null, false, true);
                break;
            case 2://筛选
                //按钮
                filterLayout.clearAnimation();
                filterLayout.startAnimation(dropdownOut);
                filterLayout.setVisibility(View.GONE);

                //背景
                bgLayout.clearAnimation();
                bgLayout.startAnimation(dropdownBgOut);
                bgLayout.setVisibility(View.GONE);
                break;
        }
        currentTab = -1;
        AppUtils.hideSoftInput(bgLayout);
    }

    /**
     * 初始化窗口动画
     */
    public void initAnim(){
        dropdownIn = AnimationUtils.loadAnimation(context, R.anim.app_dropdown_in);
        dropdownOut = AnimationUtils.loadAnimation(context, R.anim.app_dropdown_out);
        dropdownBgOut = AnimationUtils.loadAnimation(context, R.anim.app_dropdown_mask_out);
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
     * 回调接口
     */
    public interface OnClickItem{
        public void item(String[] arrays, int position);
    }
    /**
     * 展示默认排序列表
     */
    private void showListView(){
        final String[] arrays = {"默认排序", "价格最高", "价格最低"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.app_dropdown_tab_list_item, arrays);
        listSortDv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != onClickItem){
                    onClickItem.item(arrays, position);
                }
            }
        });
        listSortDv.setAdapter(adapter);
    }

    public void setBgLayout(View bgLayout) {
        this.bgLayout = bgLayout;
    }

    public void setDefaultSortBtn(DropdownButton defaultSortBtn) {
        this.defaultSortBtn = defaultSortBtn;
    }

    public void setFilterBtn(DropdownButton filterBtn) {
        this.filterBtn = filterBtn;
    }

    public void setSellBtn(DropdownButton sellBtn) {
        this.sellBtn = sellBtn;
    }

    public void setListSortDv(ListView listSortDv) {
        this.listSortDv = listSortDv;
    }

    public void setFilterLayout(RelativeLayout filterLayout) {
        this.filterLayout = filterLayout;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }
}