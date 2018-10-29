package com.gank.chen.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gank.chen.common.MyApplication;

/**
 * @author Rick
 * @date 2018/6/5
 */

public class SharePreferenceUtil {
    private static final String NAME = "Gank";
    private static  Context context = MyApplication.getmContext();
    private static SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);


    public static String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(String key) {
        return sp.contains(key);
    }

    public static void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static void setInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public static void setFloat(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public static void setLong(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public static void clear() {
        if (sp != null) {
            sp.edit().clear().apply();
        }
    }
}
