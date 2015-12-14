package com.icer.wifihotspot;

import android.app.Application;

/**
 * Created by icer on 2015/12/14.
 */
public class AppApplication extends Application {
    private static AppApplication mAppApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication = this;
    }

    public static AppApplication getInstance() {
        return mAppApplication;
    }
}
