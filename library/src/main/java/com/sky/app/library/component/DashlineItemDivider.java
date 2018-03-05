package com.sky.app.library.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.app.library.R;
import com.sky.app.library.utils.AppUtils;

/**
 * 虚线
 */
public class DashlineItemDivider extends RecyclerView.ItemDecoration {
    private Context context;
    private int paddingLeft;
    private int paddingRight;

    public DashlineItemDivider(Context context, int left, int right){
        this.context = context;
        this.paddingLeft = left;
        this.paddingRight = right;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft() + paddingLeft;
        final int right = parent.getWidth() - parent.getPaddingRight() - paddingRight;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            //以下计算主要用来确定绘制的位置
            final int top = child.getBottom() + params.bottomMargin;

            //绘制虚线
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(AppUtils.getSystemColor(context, R.color.sky_color_999999));
            Path path = new Path();
            path.moveTo(left, top);
            path.lineTo(right,top);
            PathEffect effects = new DashPathEffect(
                    new float[]{AppUtils.px2dip(context, 30),
                            AppUtils.px2dip(context, 10),
                            AppUtils.px2dip(context, 30),
                            AppUtils.px2dip(context, 10)}, 0);//此处单位是像素不是dp  注意 请自行转化为dp
            paint.setPathEffect(effects);
            c.drawPath(path, paint);
        }
    }
}