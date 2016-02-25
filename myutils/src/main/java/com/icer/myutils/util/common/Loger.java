package com.icer.myutils.util.common;

import android.util.Log;

/**
 * Created by icer on 2016/2/25.
 */
public class Loger {
    private static final int LEVEL_D = 0;
    private static final int LEVEL_I = 1;
    private static final int LEVEL_W = 2;
    private static final int LEVEL_E = 3;

    public static void d(String tag, String msg) {
        log(LEVEL_D, tag, msg);
    }

    public static void i(String tag, String msg) {
        log(LEVEL_I, tag, msg);
    }

    public static void w(String tag, String msg) {
        log(LEVEL_W, tag, msg);
    }

    public static void e(String tag, String msg) {
        log(LEVEL_E, tag, msg);
    }

    private static void log(int level, String tag, String msg) {
//        if(IS_IN_DEV)
        switch (level) {
            case LEVEL_D: {
                Log.d(tag, msg);
            }
            break;

            case LEVEL_I: {
                Log.i(tag, msg);
            }
            break;

            case LEVEL_W: {
                Log.w(tag, msg);
            }
            break;

            case LEVEL_E: {
                Log.e(tag, msg);
            }
            break;
        }
    }
}
