package com.sky.app.ui.activity.baidumap;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBRouteGuidanceListener;
import com.baidu.mapapi.bikenavi.model.BikeRouteDetailInfo;
import com.baidu.mapapi.bikenavi.model.RouteGuideKind;

public class BNaviGuideActivity extends AppCompatActivity {

    private BikeNavigateHelper mNaviHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取诱导页面地图展示View

        mNaviHelper = BikeNavigateHelper.getInstance();
        View view = mNaviHelper.onCreate(BNaviGuideActivity.this);

        if (view != null) {
            setContentView(view);
        }

       // 开始导航
        mNaviHelper.startBikeNavi(BNaviGuideActivity.this);

        // 设置诱导监听, 具体诱导信息请参考javadoc
        mNaviHelper.setRouteGuidanceListener(this, new IBRouteGuidanceListener() {
            @Override
            public void onRouteGuideIconUpdate(Drawable icon) {

            }

            @Override
            public void onRouteGuideKind(RouteGuideKind routeGuideKind) {

            }

            @Override
            public void onRoadGuideTextUpdate(CharSequence charSequence, CharSequence charSequence1) {

            }

            @Override
            public void onRemainDistanceUpdate(CharSequence charSequence) {

            }

            @Override
            public void onRemainTimeUpdate(CharSequence charSequence) {

            }

            @Override
            public void onGpsStatusChange(CharSequence charSequence, Drawable drawable) {

            }

            @Override
            public void onRouteFarAway(CharSequence charSequence, Drawable drawable) {

            }

            @Override
            public void onRoutePlanYawing(CharSequence charSequence, Drawable drawable) {

            }

            @Override
            public void onReRouteComplete() {

            }

            @Override
            public void onArriveDest() {

            }

            @Override
            public void onVibrate() {

            }

            @Override
            public void onGetRouteDetailInfo(BikeRouteDetailInfo bikeRouteDetailInfo) {

            }
        });
    }


    protected void onDestroy() {
        super.onDestroy();
        mNaviHelper.quit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mNaviHelper.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNaviHelper.pause();
    }
}
