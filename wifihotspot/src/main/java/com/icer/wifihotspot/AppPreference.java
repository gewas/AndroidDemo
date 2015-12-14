package com.icer.wifihotspot;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreference {

    public static final String KEY_SSID = "KEY_SSID";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";

    private static SharedPreferences getPrefrences() {
        return AppApplication.getInstance().getSharedPreferences(AppApplication.getInstance().getPackageName(), Activity.MODE_PRIVATE);
    }

    /**
     * 清除所有share配置
     */
    public static void clearAll() {
        Editor editor = getPrefrences().edit();
        editor.clear();
        editor.commit();
    }

    public static void removeKey(String key) {
        Editor editor = getPrefrences().edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 设置整型值
     *
     * @param key   键
     * @param value 值
     */
    public static void setIntValue(String key, int value) {
        Editor editor = getPrefrences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取整型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 整型值
     */
    public static int getIntValue(String key, int defaultValue) {
        return getPrefrences().getInt(key, defaultValue);
    }

    /**
     * 设置字符型值
     *
     * @param key   键
     * @param value 值
     */
    public static void setStringValue(String key, String value) {
        Editor editor = getPrefrences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取字符型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 字符型值
     */
    public static String getStringValue(String key, String defaultValue) {
        return getPrefrences().getString(key, defaultValue);
    }

    /**
     * 设置布尔型值
     *
     * @param key   键
     * @param value 值
     */
    public static void setBooleanValue(String key, boolean value) {
        Editor editor = getPrefrences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取布尔型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 布尔型值
     */
    public static boolean getBooleanValue(String key, boolean defaultValue) {
        return getPrefrences().getBoolean(key, defaultValue);
    }

    /**
     * 设置布浮点值
     *
     * @param key   键
     * @param value 值
     */
    public static void setFloatValue(String key, float value) {
        Editor editor = getPrefrences().edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 获取浮点型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 浮点型值
     */
    public static float getFloatValue(String key, float defaultValue) {
        return getPrefrences().getFloat(key, defaultValue);
    }

    /**
     * 设置长整型值
     *
     * @param key   键
     * @param value 值
     */
    public static void setLongValue(String key, long value) {
        Editor editor = getPrefrences().edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取长整型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 长整型值
     */
    public static long getLongValue(String key, long defaultValue) {
        return getPrefrences().getLong(key, defaultValue);
    }
}
