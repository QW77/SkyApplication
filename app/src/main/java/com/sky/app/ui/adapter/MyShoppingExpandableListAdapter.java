package com.sky.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sky.app.R;
import com.sky.app.bean.ShopCarDetail;
import com.sky.app.bean.ShopCarList;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 购物车适配器
 */
public class MyShoppingExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    List<ShopCarList> carList;//购物车数据
    public static final String EDITING = "编辑";//编辑状态文字
    public static final String FINISH_EDITING = "完成";//完成状态文字

    private OnAllCheckedBoxNeedChangeListener onAllCheckedBoxNeedChangeListener;
    private OnGoodsCheckedChangeListener onGoodsCheckedChangeListener;
    private OnGoodsNumChangeListener onGoodsNumChangeListener;
    private OnDeleteGoodsListener onDeleteGoodsListener;
    private OnItemClickListener onItemClickListener;

    public MyShoppingExpandableListAdapter(Context context, List<ShopCarList> carList) {
        this.context = context;
        this.carList = carList;
    }

    /**
     * 获取当前父item的数据数量
     * @return
     */
    @Override
    public int getGroupCount() {
        return carList.size();
    }

    /**
     * 获取当前父item下的子item的个数
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return carList.get(groupPosition).getProducts().size();
    }

    /**
     * 获取当前父item的数据
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return carList.get(groupPosition);
    }

    /**
     * 得到子item需要关联的数据
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return carList.get(groupPosition).getProducts().get(childPosition);
    }

    /**
     * 得到父item的ID
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 得到子item的ID
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 表示孩子是否和组ID是跨基础数据的更改稳定
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 设置父item组件
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.app_mine_my_shopping_item, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.app_edit = (TextView) convertView.findViewById(R.id.app_edit);
            groupViewHolder.app_sell_name = (CheckBox) convertView.findViewById(R.id.app_sell_name);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.app_sell_name.setText(carList.get(groupPosition).getSeller_name());

        if (carList.get(groupPosition).isType()) {
            groupViewHolder.app_edit.setText(FINISH_EDITING);
        } else {
            groupViewHolder.app_edit.setText(EDITING);
        }

        /**
         * 编辑按钮切换
         */
        groupViewHolder.app_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupEditing(groupPosition);
            }
        });

        final boolean nowBeanChecked = carList.get(groupPosition).isSelected();
        groupViewHolder.app_sell_name.setChecked(nowBeanChecked);
        groupViewHolder.app_sell_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupOneParentAllChildChecked(!nowBeanChecked, groupPosition);

                //控制总checkedbox 接口
                onAllCheckedBoxNeedChangeListener.onCheckedBoxNeedChange(dealAllParentIsChecked());
            }
        });
        return convertView;
    }

    /**
     * 设置子item的组件
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.app_my_shopping_item_child, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.app_select_product = (CheckBox) convertView.findViewById(R.id.app_select_product);
            childViewHolder.app_img = (ImageView) convertView.findViewById(R.id.app_img);
            childViewHolder.app_finish = (RelativeLayout) convertView.findViewById(R.id.app_finish);
            childViewHolder.app_edit = (RelativeLayout) convertView.findViewById(R.id.app_edit);

            //完成
            childViewHolder.app_title = (TextView) convertView.findViewById(R.id.app_title);
            childViewHolder.app_attr_name = (TextView) convertView.findViewById(R.id.app_attr_name);
            childViewHolder.app_price = (TextView) convertView.findViewById(R.id.app_price);
            childViewHolder.app_num = (TextView) convertView.findViewById(R.id.app_num);

            //编辑
            childViewHolder.app_del = (TextView) convertView.findViewById(R.id.app_del);
            childViewHolder.app_num_edit = (TextView) convertView.findViewById(R.id.app_num_edit);
            childViewHolder.app_add = (TextView) convertView.findViewById(R.id.app_add);
            childViewHolder.app_attr_name_edit = (TextView) convertView.findViewById(R.id.app_attr_name_edit);
            childViewHolder.app_delete = (TextView) convertView.findViewById(R.id.app_delete);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        final ShopCarDetail shopCarDetail = carList.get(groupPosition).getProducts().get(childPosition);

        /**
         * 商品的点击事件
         */
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener){
                    onItemClickListener.item(groupPosition, childPosition);
                }
            }
        });

        /**
         * 渲染数据
         */
        ImageHelper.getInstance().displayDefinedImage(shopCarDetail.getProduct_image_url(), childViewHolder.app_img,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);

        /**渲染完成界面**/
        childViewHolder.app_title.setText(shopCarDetail.getProduct_name());
        childViewHolder.app_attr_name.setText("选择分类：" + shopCarDetail.getAttr_name());
        childViewHolder.app_price.setText("¥" + AppUtils.parseDouble(shopCarDetail.getAttr_price_now()/100));
        childViewHolder.app_num.setText("x" + shopCarDetail.getProduct_num());

        /**渲染编辑界面**/
        childViewHolder.app_num_edit.setText(shopCarDetail.getProduct_num() + "");
        childViewHolder.app_attr_name_edit.setText("选择分类：" + shopCarDetail.getAttr_name());
        childViewHolder.app_select_product.setChecked(shopCarDetail.isSelected());

        //复选框事件
        childViewHolder.app_select_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final boolean nowBeanChecked = shopCarDetail.isSelected();
                shopCarDetail.setSelected(!nowBeanChecked);

                boolean isOneParentAllChildIsChecked = dealOneParentAllChildIsChecked(groupPosition);
                ShopCarList shopCarList = carList.get(groupPosition);
                shopCarList.setSelected(isOneParentAllChildIsChecked);
                notifyDataSetChanged();

                onAllCheckedBoxNeedChangeListener.onCheckedBoxNeedChange(dealAllParentIsChecked());
                dealPrice();
            }
        });

        //显示／隐藏 完成或编辑界面
        if (!shopCarDetail.isType()) {//完成
            childViewHolder.app_finish.setVisibility(View.VISIBLE);
            childViewHolder.app_edit.setVisibility(View.GONE);
        } else {//编辑
            childViewHolder.app_finish.setVisibility(View.GONE);
            childViewHolder.app_edit.setVisibility(View.VISIBLE);
        }

        /**增加商品**/
        childViewHolder.app_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealAdd(shopCarDetail);
            }
        });

        /**减少商品**/
        childViewHolder.app_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealReduce(shopCarDetail);
            }
        });

        /**删除商品**/
        childViewHolder.app_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeOneGood(groupPosition, childPosition);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 供全选按钮调用
     * @param isChecked
     */
    public void setupAllChecked(boolean isChecked) {
        for (int i = 0; i < carList.size(); i++) {
            ShopCarList shopCarList = carList.get(i);
            shopCarList.setSelected(isChecked);

            for (int j = 0; j < shopCarList.getProducts().size(); j++) {
                ShopCarDetail shopCarDetail = shopCarList.getProducts().get(j);
                shopCarDetail.setSelected(isChecked);
            }
        }
        notifyDataSetChanged();
        dealPrice();
    }

    /**
     * 设置一个父全选控制子组件
     * @param isChecked
     * @param groupPosition
     */
    private void setupOneParentAllChildChecked(boolean isChecked, int groupPosition) {
        ShopCarList shopCarList = carList.get(groupPosition);
        shopCarList.setSelected(isChecked);

        for (int j = 0; j < shopCarList.getProducts().size(); j++) {
            ShopCarDetail shopCarDetail = shopCarList.getProducts().get(j);
            shopCarDetail.setSelected(isChecked);
        }
        notifyDataSetChanged();
        dealPrice();
    }

    /**
     * 是否全选
     * @param groupPosition
     * @return
     */
    public boolean dealOneParentAllChildIsChecked(int groupPosition) {
        ShopCarList shopCarList = carList.get(groupPosition);
        for (int j = 0; j < shopCarList.getProducts().size(); j++) {
            ShopCarDetail shopCarDetail = shopCarList.getProducts().get(j);
            if (!shopCarDetail.isSelected()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 父类是否选中
     * @return
     */
    public boolean dealAllParentIsChecked() {
        for (int i = 0; i < carList.size(); i++) {
            ShopCarList shopCarList = carList.get(i);
            if (!shopCarList.isSelected()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算金额
     */
    private void dealPrice(){
        DecimalFormat df = new DecimalFormat("0.00");
        double i = 0;
        int j = 0;
        for (ShopCarList shopCarList : carList){
            for (ShopCarDetail shopCarDetail : shopCarList.getProducts()){
                if (shopCarDetail.isSelected()){
                    i += (shopCarDetail.getAttr_price_now()/100 * shopCarDetail.getProduct_num());
                    j += shopCarDetail.getProduct_num();
                }
            }
        }
        //计算回调
        onGoodsCheckedChangeListener.onGoodsCheckedChange(j, df.format(i));
    }

    /**
     * 设置编辑状态
     * @param groupPosition
     */
    private void setupEditing(int groupPosition) {
        ShopCarList shopCarList = carList.get(groupPosition);
        boolean isEditing = !shopCarList.isType();
        shopCarList.setType(isEditing);
        for (int j = 0; j < shopCarList.getProducts().size(); j++) {
            ShopCarDetail shopCarDetail = shopCarList.getProducts().get(j);
            shopCarDetail.setType(isEditing);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加商品
     */
    public void dealAdd(ShopCarDetail shopCarDetail) {
        int count = shopCarDetail.getProduct_num();
        count++;
        shopCarDetail.setProduct_num(count);
        notifyDataSetChanged();
        dealPrice();
        if (null != onGoodsNumChangeListener){
            onGoodsNumChangeListener.onChange(shopCarDetail.getCart_id(), shopCarDetail.getProduct_num());
        }
    }

    /**
     * 减少商品
     * @param shopCarDetail
     */
    public void dealReduce(ShopCarDetail shopCarDetail) {
        int count = shopCarDetail.getProduct_num();
        if (count == 1) return;
        count--;
        shopCarDetail.setProduct_num(count);
        notifyDataSetChanged();
        dealPrice();
        if (null != onGoodsNumChangeListener){
            onGoodsNumChangeListener.onChange(shopCarDetail.getCart_id(), shopCarDetail.getProduct_num());
        }
    }

    /**
     * 删除某一个商品
     * @param groupPosition
     * @param childPosition
     */
    public void removeOneGood(int groupPosition, int childPosition) {
        List<ShopCarDetail> shopCarDetails = carList.get(groupPosition).getProducts();
        if (null != onDeleteGoodsListener){
            onDeleteGoodsListener.delete(shopCarDetails.get(childPosition).getCart_id());
        }
        shopCarDetails.remove(childPosition);

        if (null == shopCarDetails || shopCarDetails.isEmpty()){
           carList.remove(groupPosition);
        }
        notifyDataSetChanged();
        dealPrice();
    }

    /**
     * 父组件视图元素
     */
    class GroupViewHolder {
        TextView app_edit;//编辑还是完成
        CheckBox app_sell_name;//复选框
    }

    /**
     * 子组件视图元素
     */
    class ChildViewHolder {
        CheckBox app_select_product;//复选框
        ImageView app_img;//图片
        RelativeLayout app_finish;//完成按钮
        RelativeLayout app_edit;//编辑按钮

        /**编辑**/
        TextView app_del;//删除
        TextView app_num_edit;//数量
        TextView app_add;//添加
        TextView app_attr_name_edit;//属性
        TextView app_delete;//删除

        /**完成**/
        TextView app_title;//商品名称
        TextView app_attr_name;//属性
        TextView app_price;//价格
        TextView app_num;//数量
    }

    /****************设置监听事件******************/
    public void setOnGoodsCheckedChangeListener(OnGoodsCheckedChangeListener onGoodsCheckedChangeListener) {
        this.onGoodsCheckedChangeListener = onGoodsCheckedChangeListener;
    }
    public void setOnAllCheckedBoxNeedChangeListener(OnAllCheckedBoxNeedChangeListener onAllCheckedBoxNeedChangeListener) {
        this.onAllCheckedBoxNeedChangeListener = onAllCheckedBoxNeedChangeListener;
    }
    public void setOnGoodsNumChangeListener(OnGoodsNumChangeListener onGoodsNumChangeListener){
        this.onGoodsNumChangeListener = onGoodsNumChangeListener;
    }
    public void setOnDeleteGoodsListener(OnDeleteGoodsListener onDeleteGoodsListener){
        this.onDeleteGoodsListener = onDeleteGoodsListener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnAllCheckedBoxNeedChangeListener {
        void onCheckedBoxNeedChange(boolean allParentIsChecked);
    }

    public interface OnGoodsCheckedChangeListener {
        void onGoodsCheckedChange(int totalCount, String totalPrice);
    }

    public interface OnGoodsNumChangeListener {
        void onChange(String cardId, int num);
    }

    public interface OnDeleteGoodsListener{
        void delete(String cardId);
    }

    public interface OnItemClickListener{
        void item(int first, int second);
    }
}
