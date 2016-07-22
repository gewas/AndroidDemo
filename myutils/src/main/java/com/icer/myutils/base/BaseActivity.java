package com.icer.myutils.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.icer.myutils.constant.Functional;
import com.icer.myutils.util.common.Loger;
import com.icer.myutils.util.common.ScreenUtil;

/**
 * Created by icer on 2016/2/25.
 */
public class BaseActivity extends AppCompatActivity {


    private Toast mLastToast;
    private BroadcastReceiver mSuicideReceiver;

    private float xDown;
    private float yDown;
    private boolean isSwipeFinish;

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
                xDown = event.getX();
                yDown = event.getY();
                return true;

            case MotionEvent.ACTION_UP:
                if (Math.abs(event.getY() - yDown) < 0.1f * ScreenUtil.getScreenHeight(this)) {
                    if (Math.abs(event.getX() - xDown) > 0.1f * ScreenUtil.getScreenWidth(this)) {
                        if (event.getX() - xDown > 0 && isSwipeFinish) {
                            finish();
                        }
                    }
                }
                return true;

        }

        return super.onTouchEvent(event);
    }

    public void setIsSwipeFinish(boolean bool) {
        isSwipeFinish = bool;
    }

    public BaseActivity getActivity() {
        return this;
    }

    public void logI(String msg) {
        Loger.i(getTag(), msg);
    }

    public void logW(String msg) {
        Loger.w(getTag(), msg);
    }

    public void logE(String msg) {
        Loger.e(getTag(), msg);
    }

    public void showToast(int stringResID) {
        cancelToast();
        mLastToast = Toast.makeText(this, stringResID, Toast.LENGTH_SHORT);
        mLastToast.show();
    }

    public void showToast(String string) {
        cancelToast();
        mLastToast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        mLastToast.show();
    }

    public void cancelToast() {
        if (mLastToast != null)
            mLastToast.cancel();
    }

    public void enableSuicide() {
        if (mSuicideReceiver == null) {
            mSuicideReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Functional.SUICIDE)) {
                        finish();
                    }
                }
            };
            registerReceiver(mSuicideReceiver, new IntentFilter(Functional.SUICIDE));
        }
    }

    public void executeSuicide() {
        sendBroadcast(new Intent(Functional.SUICIDE));
    }

    public synchronized boolean isQuickClick() {
        long time = System.currentTimeMillis();
        boolean res = time - mLastClickTime < 500;
        mLastClickTime = time;
        return res;
    }

    public String getTag() {
        return getClass().getCanonicalName();
    }
}
