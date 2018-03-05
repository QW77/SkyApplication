package com.sky.app.ui.activity.baidumap;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWNaviStatusListener;
import com.baidu.mapapi.walknavi.adapter.IWRouteGuidanceListener;
import com.baidu.mapapi.walknavi.model.RouteGuideKind;
import com.baidu.platform.comapi.walknavi.WalkNaviModeSwitchListener;

/**
 *
 */
public class WNaviGuideActivity extends AppCompatActivity {

    private WalkNavigateHelper mNaviHelper;
    private String TAG="WNaviGuideActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wnavi_guide);
        // 获取导航控制类
        mNaviHelper = WalkNavigateHelper.getInstance();
        View view = mNaviHelper.onCreate(WNaviGuideActivity.this);
        if (view != null) {
            setContentView(view);
        }

        mNaviHelper.setWalkNaviStatusListener(new IWNaviStatusListener() {
            @Override
            public void onWalkNaviModeChange(int mode, WalkNaviModeSwitchListener listener) {
                Log.d(TAG, "onWalkNaviModeChange : " + mode);
                mNaviHelper.switchWalkNaviMode(WNaviGuideActivity.this, mode, listener);
            }

            @Override
            public void onNaviExit() {
                Log.d(TAG, "onNaviExit");
            }
        });

        // 开始导航
        mNaviHelper.startWalkNavi(WNaviGuideActivity.this);
        mNaviHelper.setRouteGuidanceListener(this, new IWRouteGuidanceListener() {
            /**
             * 诱导图标更新
             *
             * @param icon
             */
            @Override
            public void onRouteGuideIconUpdate(Drawable icon) {

            }

            /**
             * 诱导枚举信息
             *
             * @param routeGuideKind 诱导信息 see {@link RouteGuideKind}
             */
            @Override
            public void onRouteGuideKind(RouteGuideKind routeGuideKind) {
            }

            /**
             * 诱导信息
             *
             * @param charSequence 第一行显示的信息，比如“沿当前道路”
             * @param charSequence1 第二行显示的信息，比如“向东出发”，第二行信息也可能为空
             */
            @Override
            public void onRoadGuideTextUpdate(CharSequence charSequence, CharSequence charSequence1) {
            }
            /**
             * 总的剩余距离
             *
             * @param charSequence 已经带有单位
             */
            @Override
            public void onRemainDistanceUpdate(CharSequence charSequence) {
            }

            /**
             * 总的剩余时间
             *
             * @param charSequence 已经带有单位
             */
            @Override
            public void onRemainTimeUpdate(CharSequence charSequence) {
            }

            /**
             * GPS状态发生变化，来自诱导引擎的消息
             *
             * @param charSequence GPS信息
             * @param drawable
             */
            @Override
            public void onGpsStatusChange(CharSequence charSequence, Drawable drawable) {
            }

            /**
             * 已经开始偏航
             *
             * @param charSequence 偏航信息
             * @param drawable
             */
            @Override
            public void onRouteFarAway(CharSequence charSequence, Drawable drawable) {
            }

            /**
             * 偏航规划中
             *
             * @param charSequence 偏航规划中的信息
             * @param drawable
             */
            @Override
            public void onRoutePlanYawing(CharSequence charSequence, Drawable drawable) {
            }

            /**
             * 重新算路成功
             */
            @Override
            public void onReRouteComplete() {
            }

            /**
             * 到达目的地
             */
            @Override
            public void onArriveDest() {

            }

            /**
             * 震动
             */
            @Override
            public void onVibrate() {

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
