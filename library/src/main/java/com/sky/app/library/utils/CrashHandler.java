package com.sky.app.library.utils;

import android.content.Context;
import android.os.Looper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 *  UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler crashHandler = null; // CrashHandler 实例
    private Context mContext;  // 程序的 Context 对象
    private Thread.UncaughtExceptionHandler mDefaultHandler;// 系统默认的 UncaughtException 处理类

    /** 保证只有一个 CrashHandler 实例 */
    private CrashHandler() {
    }

    /** 获取 CrashHandler 实例 ,单例模式 */
    public static CrashHandler getInstance() {
        if (null == crashHandler){
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();//获取系统默认的 UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this);// 设置该 CrashHandler 为程序的默认处理器
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 退出程序,注释下面的重启启动程序代码
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) return false;

        // 使用 Toast 来显示异常信息
        ThreadPoolManager.getInstance().addTask(new Runnable(){

            @Override
            public void run() {
                Looper.prepare();
                T.showShort(mContext, "很抱歉，程序异常");
                Looper.loop();
            }

        });
        T.showLong(mContext, "程序异常信息：" + ex.getMessage());
        L.msg("程序异常信息：" + ex.getMessage());
        L.msg("程序异常堆栈信息：" + getStackTrace(ex));
        return true;
    }

    /**
     * 完整的堆栈信息
     * @param e Exception
     * @return
     */
    public static String getStackTrace(Throwable e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}