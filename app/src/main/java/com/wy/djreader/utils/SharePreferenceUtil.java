package com.wy.djreader.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

public class SharePreferenceUtil {
    /**
     * 获取存储文件中的值
     * @auther chenlf3
     * @date 2015年8月31日 下午3:27:37
     * @param context
     * @param key
     * @return
     */
    public static String getSPString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    /**
     * 获取存储文件中的值
     * @auther chenlf3
     * @date 2015年8月31日 下午3:27:37
     * @param context
     * @param key
     * @return
     */
    public static int getSPInt(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    /**
     * 获取存储文件中的值
     * @auther chenlf3
     * @date 2015年8月31日 下午3:27:50
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getSPString(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 获取存储文件中的值
     * @auther chenlf3
     * @date 2015年8月31日 下午3:27:50
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getSPInt(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 添加存储文件中的值
     * @auther chenlf3
     * @date 2015年8月31日 下午3:29:57
     * @param context
     * @param key
     * @param value
     */
    public static void addSP(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 添加存储文件中的值
     * @auther chenlf3
     * @date 2015年8月31日 下午3:29:57
     * @param context
     * @param key
     * @param value
     */
    public static void addSP(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 添加存储文件中的值
     * @auther chenlf3
     * @date 2015年8月31日 下午3:33:58
     * @param context
     * @param map
     */
    public static void addSPSString(Context context, Map<String,String> map) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Set<String> keys = map.keySet();
        for(String key:keys) {
            editor.putString(key, map.get(key));
        }
        editor.commit();
    }

    /**
     * 添加存储文件中的值
     * @auther chenlf3
     * @date 2015年8月31日 下午3:33:58
     * @param context
     * @param map
     */
    public static void addSPSInt(Context context, Map<String,Integer> map) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Set<String> keys = map.keySet();
        for(String key:keys) {
            editor.putInt(key, map.get(key));
        }
        editor.commit();
    }
}
