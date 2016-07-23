package com.icer.myutils.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

/**
 * BaseBroadcastReceiver for Dynamic Register
 * Created by icer on 2016/7/23.
 */
public abstract class BaseReceiver extends BroadcastReceiver {

    protected Context mContext;

    protected BaseReceiver(Context context) {
        mContext = context;
    }

    protected void register(String... actions) {
        IntentFilter intentFilter = new IntentFilter();
        if (actions != null)
            for (String action : actions)
                intentFilter.addAction(action);
        mContext.registerReceiver(this, intentFilter);
    }

    protected void unregister() {
        mContext.unregisterReceiver(this);
    }

}
