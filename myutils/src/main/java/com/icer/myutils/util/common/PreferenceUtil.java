package com.icer.myutils.util.common;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressWarnings("unused")
public class PreferenceUtil {

    private static String SHARED_PREFERENCE_FILE_NAME;

    private static PreferenceUtil appPreferenceUtil;
    private static Application appApplication;

    private PreferenceUtil(Application application) {
        SHARED_PREFERENCE_FILE_NAME = application.getPackageName() + ".SharedPreference";
        appApplication = application;
    }

    public static PreferenceUtil getInstance(Application application) {
        if (appPreferenceUtil == null)
            appPreferenceUtil = new PreferenceUtil(application);
        return appPreferenceUtil;
    }

    private static SharedPreferences getPreferences() {
        return appApplication.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Activity.MODE_PRIVATE);
    }

    public void clearAll() {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.clear();
        editor.apply();
    }

    public void removeKey(String key) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove(key);
        editor.apply();
    }

    public void setIntValue(String key, int value) {
        Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntValue(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    public void setStringValue(String key, String value) {
        Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public void setBooleanValue(String key, boolean value) {
        Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    public void setFloatValue(String key, float value) {
        Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloatValue(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    public void setLongValue(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLongValue(String key, long defaultValue) {
        return getPreferences().getLong(key, defaultValue);
    }

    public void doUpgradeInitialization() {
        //Do your Stuff
    }
}
