package com.icer.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;

/**
 * Created by Administrator on 2015-07-29.
 */
public class ZoomButton extends Button {

    private Scroller mScroller;

    private boolean mIsZoomedIn;

    public ZoomButton(Context context) {
        super(context);
        init();
    }

    public ZoomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            ViewGroup.LayoutParams params = getLayoutParams();
            params.width = mScroller.getCurrX();
            params.height = mScroller.getCurrY();
            setLayoutParams(params);
            invalidate();
        }
    }

    public void zoomIn() {
        mScroller.startScroll(getWidth(), getHeight(), getWidth() * 2, getHeight() * 2);
        invalidate();
        mIsZoomedIn = true;
    }

    public void zoomOut() {
        mScroller.startScroll(getWidth(), getHeight(), -getWidth() * 2 / 3, -getHeight() * 2 / 3);
        invalidate();
        mIsZoomedIn = false;
    }

    public boolean isZoomedIn() {
        return mIsZoomedIn;
    }

}
