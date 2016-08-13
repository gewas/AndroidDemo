package com.icer.myutils.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.icer.myutils.util.common.ScreenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by icer on 2016/7/11.
 */
public class HeartView extends View {

    public static int REFRESH_PERIOD = 13;//刷新间隔毫秒
    public static float MAX_DEGREE = 5;//图标扭摆最大角度
    public static float DEF_PERIOD_LR = REFRESH_PERIOD * 20;
    public static float DEF_PERIOD_SHAKE = REFRESH_PERIOD * 6;

    private int[] ICONS;

    private float mStartX = 0.5f;
    private float mStartY = 1f;
    private float mBorderXL = mStartX - 0.2f;
    private float mBorderXR = mStartX + 0.2f;

    private List<Icon> mIcons = new ArrayList<>();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Map<Integer, Bitmap> mBitmaps = new HashMap<>();

    private AsyncTask mAsyncTask;

    public HeartView(Context context) {
        this(context, null);
    }

    public HeartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setICONS(int[] ICONS) {
        this.ICONS = ICONS;
    }

    public void setStartX(float startX) {
        mStartX = startX;
        mBorderXL = startX - 0.2f;
        mBorderXR = startX + 0.2f;
    }

    public void setStartY(float startY) {
        mStartY = startY;
    }

    private void newTask() {
        mAsyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                while (true) {
                    if (mIcons.size() == 0) {
                        break;
                    }
                    publishProgress();
                    SystemClock.sleep(REFRESH_PERIOD);
                    for (int i = 0; i < mIcons.size(); i++) {
                        Icon icon = mIcons.get(i);
                        icon.calc();
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                invalidate();
            }

            @Override
            protected void onPostExecute(Object o) {
                mAsyncTask = null;
            }
        };
        mAsyncTask.executeOnExecutor(Executors.newCachedThreadPool());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mIcons.size(); i++) {
            Icon icon = mIcons.get(i);
            if (icon.needDraw()) {
                canvas.save();
                float left = icon.x - icon.getBitmap().getWidth() / 2;
                float top = icon.y - icon.getBitmap().getHeight() / 2;
                int al = (int) ((icon.y - icon.getBitmap().getHeight() / 2) / (getHeight() * mStartY) * 384);
                mPaint.setAlpha(icon.y <= icon.getBitmap().getHeight() / 2 ? 0 : al > 255 ? 255 : al);
                Matrix m = new Matrix();
                m.preRotate(icon.shakeDegree, icon.getBitmap().getWidth() / 2, icon.getBitmap().getHeight() / 2);
                m.postTranslate(left, top);
                canvas.drawBitmap(icon.getBitmap(), m, mPaint);
            } else {
                mIcons.remove(icon);
            }
        }
    }

    public void addIcon() {
        mIcons.add(new Icon());
        mIcons.add(new Icon());
        if (mAsyncTask == null)
            newTask();
    }


    private class Icon {

        int drawableId;
        int lr;
        float periodLR;
        float shakeDegree;
        float degreeFactor;
        float periodShake;
        float speedY;
        float speedX;
        float x;
        float y;

        public Icon() {
            drawableId = ICONS[(int) (Math.random() * ICONS.length)];
            lr = Math.random() < 0.5 ? -1 : 1;
            periodLR = DEF_PERIOD_LR;
            shakeDegree = 0;
            degreeFactor = 1;
            periodShake = DEF_PERIOD_SHAKE;
            int sy = (int) (getHeight() * 0.13 + (Math.random() < 0.5 ? -1 : 1) * 0.05 * Math.random() * getHeight());
            int sx = (int) (getWidth() * 0.07 + (Math.random() < 0.5 ? -1 : 1) * 0.05 * Math.random() * getWidth());
            speedY = ScreenUtil.dip2Px(getContext(), sy);
            speedX = ScreenUtil.dip2Px(getContext(), sx);
            x = getWidth() * mStartX;
            y = getHeight() * mStartY;
        }

        private void judgeBorder() {
            float xt = x + speedX / 1000 * REFRESH_PERIOD * lr;
            float cx = xt + getBitmap().getWidth() / 2;
            if (cx - getWidth() * mBorderXL <= 0 || getWidth() * mBorderXR - cx <= 0) {
                lr = -lr;
                periodLR = DEF_PERIOD_LR;
            }
        }

        private void rollLR() {
            lr = Math.random() < 0.5 ? -1 : 1;
        }

        private boolean needDraw() {
            return y > -getBitmap().getHeight() / 2 - 4;
        }

        public void calc() {
            judgeBorder();
            x += speedX / 1000 * REFRESH_PERIOD * lr;
            y -= speedY / 1000 * REFRESH_PERIOD;
            if (shakeDegree >= MAX_DEGREE)
                degreeFactor = -1;
            else if (shakeDegree <= -MAX_DEGREE) {
                degreeFactor = 1;
            }
            shakeDegree += degreeFactor * MAX_DEGREE / DEF_PERIOD_SHAKE * REFRESH_PERIOD;
            periodLR -= REFRESH_PERIOD;
            periodShake -= REFRESH_PERIOD;
            if (periodLR <= 0) {
                rollLR();
                periodLR = DEF_PERIOD_LR;
            }
            if (periodShake <= 0)
                periodShake = DEF_PERIOD_SHAKE;
        }

        private Bitmap getBitmap() {
            Bitmap res = mBitmaps.get(drawableId);
            if (res == null) {
                res = BitmapFactory.decodeResource(getResources(), drawableId);
                mBitmaps.put(drawableId, res);
            }
            return res;
        }
    }
}
