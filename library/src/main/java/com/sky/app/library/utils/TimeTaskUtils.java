package com.sky.app.library.utils;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sky on 2017/3/30.
 */

public class TimeTaskUtils {
    private static TimeTaskUtils instance = null;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    public static final long TIME = 60000;

    public static TimeTaskUtils getInstance(){
        if (null == instance){
            synchronized (TimeTaskUtils.class){
                if (null == instance){
                    instance = new TimeTaskUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 开始定时
     */
    public void startTimer(final Handler handler){
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            };
        }

        if(mTimer != null && mTimerTask != null )
            mTimer.schedule(mTimerTask, 10000, TIME);
    }

    /**
     * 结束定时
     */
    public void stopTimer(){

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }
}
