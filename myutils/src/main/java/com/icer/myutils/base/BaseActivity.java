package com.icer.myutils.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.icer.myutils.constant.Broadcast;
import com.icer.myutils.util.common.Logger;
import com.icer.myutils.util.common.ScreenUtil;

/**
 * Created by icer on 2016/2/25.
 */
@SuppressWarnings("unused")
public abstract class BaseActivity extends AppCompatActivity {

    private Toast mLastToast;
    private BaseReceiver mSuicideReceiver;

    private float mXDown;
    private float mYDown;

    private boolean mIsSwipeFinish;

    private boolean mEnableDoublePressExit;
    private String mDoublePressExitHint;
    private long mLastBackMillis;
    private long mDoublePressExitPeriod;

    private long mLastClickTime = System.currentTimeMillis();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSuicideReceiver != null)
            unregisterReceiver(mSuicideReceiver);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = event.getX();
                mYDown = event.getY();
                return true;

            case MotionEvent.ACTION_UP:
                if (Math.abs(event.getY() - mYDown) < 0.1f * ScreenUtil.getScreenHeight(this)) {
                    if (Math.abs(event.getX() - mXDown) > 0.1f * ScreenUtil.getScreenWidth(this)) {
                        if (event.getX() - mXDown > 0 && mIsSwipeFinish) {
                            finish();
                        }
                    }
                }
                return true;

        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (!mEnableDoublePressExit)
            super.onBackPressed();
        else {
            long now = System.currentTimeMillis();
            if (now - mLastBackMillis > mDoublePressExitPeriod) {
                mLastBackMillis = now;
                showToastShort(mDoublePressExitHint);
            } else
                super.onBackPressed();
        }
    }

    public void setIsSwipeFinish(boolean bool) {
        mIsSwipeFinish = bool;
    }

    public BaseActivity getActivity() {
        return this;
    }

    public void log(String msg) {
        Logger.d(getActivityTag(), msg);
    }

    public void logI(String msg) {
        Logger.i(getActivityTag(), msg);
    }

    public void logW(String msg) {
        Logger.w(getActivityTag(), msg);
    }

    public void logE(String msg) {
        Logger.e(getActivityTag(), msg);
    }

    public void showToastShort(int stringResID) {
        showToastShort(getString(stringResID));
    }

    public void showToastShort(String string) {
        cancelToast();
        mLastToast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        mLastToast.show();
    }

    public void showToastLong(int stringResID) {
        showToastLong(getString(stringResID));
    }

    public void showToastLong(String string) {
        cancelToast();
        mLastToast = Toast.makeText(this, string, Toast.LENGTH_LONG);
        mLastToast.show();
    }

    public void cancelToast() {
        if (mLastToast != null)
            mLastToast.cancel();
    }

    public void EnableDoublePressExit(long periodMillis, String hint) {
        mEnableDoublePressExit = true;
        mDoublePressExitPeriod = periodMillis;
        mDoublePressExitHint = hint;
        mLastBackMillis = System.currentTimeMillis();
    }

    public void enableSuicide() {
        if (mSuicideReceiver == null) {
            mSuicideReceiver = new BaseReceiver(this) {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Broadcast.SUICIDE)) {
                        finish();
                    }
                }
            };
            mSuicideReceiver.register(Broadcast.SUICIDE);
        }
    }

    public void executeSuicide() {
        sendBroadcast(new Intent(Broadcast.SUICIDE));
    }

    public synchronized boolean isQuickClick() {
        long time = System.currentTimeMillis();
        boolean res = time - mLastClickTime < 500;
        mLastClickTime = time;
        return res;
    }

    public String getActivityTag() {
        return getClass().getCanonicalName();
    }

    public int getActivityID() {
        return getActivityTag().hashCode();
    }
}
