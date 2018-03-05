package com.sky.app.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * SharedPreferences 工具类
 */
public class SPUtil {
	public static final String XML_USERINFO = "p_userinfo";
    /**
     * spMap 用于存放SharedPreferences的集合
     * 
     */
    private static final Map<String, SoftReference<SharedPreferences>> SP_MAP = new HashMap<String, SoftReference<SharedPreferences>>();
    
    private static Context context;
    
    /** 
     *  在程序初始化时调用，主要是为了为context赋值，否则调用其他方法时会抛出空指针
     *
     * @param context
     * void
     */
    public static void init(Context context) {
        if (SPUtil.context == null)
            SPUtil.context = context;
    }
    
    /**
     * 获取SharedPreferences，如果在spMap中存在，就取出，否则就新建
     * 
     * @param name SharedPreferences 名字
     * @return SharedPreferences
     */
    public static SharedPreferences getSp(String name){
        if (!SP_MAP.containsKey(name)) {
            // 软引用
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SP_MAP.put(name, new SoftReference<SharedPreferences>(sp));
        }
        return SP_MAP.get(name).get();
    }
    
    public static void put(String spName, String key, String value){
        SharedPreferences sp = getSp(spName);
        sp.edit().putString(key, value).commit();
    }
    
    public static void put(String spName, String key, int value){
        SharedPreferences sp = getSp(spName);
        sp.edit().putInt(key, value).commit();
    }
    
    public static void put(String spName, String key, long value){
        SharedPreferences sp = getSp(spName);
        sp.edit().putLong(key, value).commit();
    }
    
    public static void put(String spName, String key, boolean value){
        SharedPreferences sp = getSp(spName);
        sp.edit().putBoolean(key, value).commit();
    }
    
    public static String get(String spName, String key, String defValue){
        SharedPreferences sp = getSp(spName);
        return sp.getString(key, defValue);
    }
    
    public static int get(String spName, String key, int defValue){
        SharedPreferences sp = getSp(spName);
        return sp.getInt(key, defValue);
    }
    
    public static long get(String spName, String key, long defValue){
        SharedPreferences sp = getSp(spName);
        return sp.getLong(key, defValue);
    }
    
    public static boolean get(String spName, String key, boolean defValue){
        SharedPreferences sp = getSp(spName);
        return sp.getBoolean(key, defValue);
    }
    
    public static void clear(String spName, String key){
        getSp(spName).edit().remove(key).commit();
    }
    
    public static void clear(String spName){
        getSp(spName).edit().clear().commit();
    }
}