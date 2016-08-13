package com.icer.myutils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.icer.myutils.R;
import com.icer.myutils.util.common.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

/**
 * Created by icer on 2016/8/12.
 */
public class DanmakuView extends View {

    public static final int REFRESH_PERIOD = 25;
    public static final float DANMAKU_SPEED = 256f;
    public static float TEXT_SIZE;
    public static float MARGIN_DANMAKU;
    public static float MARGIN_NICK_MSG;

    private int MAX_DANMAKU_NUM = 2;
    private float WIDTH;

    private Paint mNickPaint;
    private Paint mContentPaint;

    private List<Danmaku> mDanmakus = new ArrayList<>();
    private List<Danmaku> mDanmakusQueue = new ArrayList<>();

    private AsyncTask<String, String, String> mRefreshTask;

    private Timer mEnqueueTimer;

    public DanmakuView(Context context) {
        this(context, null);
    }

    public DanmakuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DanmakuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TEXT_SIZE = ScreenUtil.dip2Px(getContext(), 14);
        MARGIN_DANMAKU = ScreenUtil.dip2Px(getContext(), 8);
        MARGIN_NICK_MSG = ScreenUtil.dip2Px(getContext(), 4);

        mNickPaint = new Paint();
        mNickPaint.setAntiAlias(true);
        mNickPaint.setColor(getResources().getColor(R.color.yellow));
        mNickPaint.setTextSize(TEXT_SIZE);

        mContentPaint = new Paint();
        mContentPaint.setAntiAlias(true);
        mContentPaint.setColor(getResources().getColor(R.color.white));
        mContentPaint.setTextSize(TEXT_SIZE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        WIDTH = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mDanmakus.size(); i++) {
            Danmaku d = mDanmakus.get(i);
            if (d.isEnd()) {
                continue;
            }
            canvas.drawText(d.mNick, d.mLeftNick, d.mTopText, mNickPaint);
            canvas.drawText(d.mMsg, d.mLeftContent, d.mTopText, mContentPaint);
        }
    }

    public void newDanmaku(String nick, String content) {
        new Danmaku(nick, content, true);
        if (mEnqueueTimer == null) {
            mEnqueueTimer = new Timer();
            mEnqueueTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    while (hasVacant()) {
                        if (mDanmakusQueue.size() > 0) {
                            Danmaku d = mDanmakusQueue.remove(0);
                            if (!d.enqueue()) {
                                mDanmakusQueue.add(0, d);
                                break;
                            }
                        }
                    }
                }
            }, 100, 500);
        }
    }

    private boolean hasVacant() {
        if (mDanmakus.size() < MAX_DANMAKU_NUM)
            return true;
        boolean res = false;
        synchronized (mDanmakus) {
            for (int i = 0; i < mDanmakus.size(); i++) {
                Danmaku d = mDanmakus.get(i);
                if (d.isRailAvailable()) {// 可以排在当前弹幕右边
                    if (i + 1 < mDanmakus.size()) {// 如果不是已发弹幕的最后一个弹幕
                        Danmaku nextD = mDanmakus.get(i + 1);
                        if (nextD.mTop != d.mTop) {// 如果是当前轨道最后一个弹幕
                            res = true;
                            break;
                        }
                    } else {// 如果已经是已发弹幕的最后一个弹幕
                        res = true;
                        break;
                    }
                }
            }
        }
        return res;
    }

    private void startTask() {
        if (mRefreshTask == null) {
            mRefreshTask = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    while (!isTaskEnd()) {
                        publishProgress();
                        for (int i = 0; i < mDanmakus.size(); i++) {
                            Danmaku d = mDanmakus.get(i);
                            d.calc(REFRESH_PERIOD);
                        }
                        SystemClock.sleep(REFRESH_PERIOD);
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    super.onProgressUpdate(values);
                    invalidate();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    synchronized (mDanmakus) {
                        mDanmakus.clear();
                    }
                    mRefreshTask = null;
                }

                private boolean isTaskEnd() {
                    boolean res = true;
                    for (int i = 0; i < mDanmakus.size(); i++) {
                        Danmaku d = mDanmakus.get(i);
                        if (!d.isEnd()) {
                            res = false;
                            break;
                        }
                    }
                    return res;
                }
            };
            mRefreshTask.executeOnExecutor(Executors.newCachedThreadPool());
        }
    }

    public void setMaxDanmakuNum(int maxDanmakuNum) {
        MAX_DANMAKU_NUM = maxDanmakuNum;
    }

    private class Danmaku {

        public String mNick;
        public String mMsg;

        public float mLeft;
        public float mLeftNick;
        public float mLeftContent;
        public float mTop;
        public float mTopText;
        public float mRight;
        public float mBottom;

        public Danmaku(String nick, String msg, boolean justRecoed) {
            mNick = nick;
            mMsg = msg;
            mDanmakusQueue.add(this);
        }

        public Danmaku(String nick, String msg) {
            mNick = nick;
            mMsg = msg;
            enqueue();
        }

        public boolean enqueue() {
            boolean res = false;
            mLeft = WIDTH;
            mLeftNick = mLeft;
            mLeftContent = mLeftNick + mNickPaint.measureText(mNick) + MARGIN_NICK_MSG;
            mRight = mLeftContent + mContentPaint.measureText(mMsg);
            mTop = 0f;

            synchronized (mDanmakus) {
                if (mDanmakus.size() == 0) {
                    res = true;
                    mDanmakus.add(this);
                } else if (mDanmakus.size() > 0 && mDanmakus.get(mDanmakus.size() - 1).mTop + TEXT_SIZE + MARGIN_DANMAKU <= (MAX_DANMAKU_NUM - 1) * (TEXT_SIZE + MARGIN_DANMAKU)) {
                    mTop = mDanmakus.get(mDanmakus.size() - 1).mTop + TEXT_SIZE + MARGIN_DANMAKU;
                    res = true;
                    mDanmakus.add(this);
                } else {
                    for (int i = 0; i < mDanmakus.size(); i++) {
                        Danmaku d = mDanmakus.get(i);
                        if (d.isRailAvailable()) {// 可以排在当前弹幕右边
                            if (i + 1 < mDanmakus.size()) {// 如果不是已发弹幕的最后一个弹幕
                                Danmaku nextD = mDanmakus.get(i + 1);
                                if (nextD.mTop != d.mTop) {// 如果是当前轨道最后一个弹幕
                                    mTop = d.mTop;
                                    res = true;
                                    mDanmakus.add(i + 1, this);
                                    if (d.isEnd())
                                        mDanmakus.remove(d);
                                    break;
                                } else {// 如果还不是当前轨道最后一个弹幕
                                    if (d.isEnd())
                                        mDanmakus.remove(i--);
                                }
                            } else {// 如果已经是已发弹幕的最后一个弹幕
                                mTop = d.mTop;
                                res = true;
                                mDanmakus.add(this);
                                if (d.isEnd())
                                    mDanmakus.remove(d);
                                break;
                            }
                        }
                    }
                }
                mTopText = mTop + TEXT_SIZE;
                mBottom = mTop + TEXT_SIZE;
                if (res)
                    startTask();
            }
            return res;
        }

        public void calc(int dMillis) {
            float dp = dMillis * DANMAKU_SPEED / 1000.0f;
            mLeft -= dp;
            mLeftNick -= dp;
            mLeftContent -= dp;
            mRight -= dp;
        }

        public boolean isRailAvailable() {
            return mRight < WIDTH;
        }

        public boolean isEnd() {
            return mRight < 0;
        }

        @Override
        public String toString() {
            return "Danmaku{" +
                    "mLeft=" + mLeft +
                    ", mTop=" + mTop +
                    '}';
        }
    }
}
