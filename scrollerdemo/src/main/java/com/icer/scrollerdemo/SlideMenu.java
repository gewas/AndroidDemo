package com.icer.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2015-07-30.
 */
public class SlideMenu extends FrameLayout {

    private static final double mSlideRatio = 0.4;
    private Scroller mScroller;
    private RelativeLayout mMenuLayout;
    private RelativeLayout mContentLayout;
    private boolean mIsClosed;

    private int mSlideWidth;

    public SlideMenu(Context context) {
        super(context);
        init();
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
        mIsClosed = true;
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mSlideWidth = (int) (mSlideRatio * wm.getDefaultDisplay().getWidth());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuLayout = (RelativeLayout) findViewById(R.id.menu);
        mContentLayout = (RelativeLayout) findViewById(R.id.content);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mContentLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public boolean isClosed() {
        return mIsClosed;
    }

    public void openMenu() {
        if (mIsClosed) {
            mScroller.startScroll(mContentLayout.getScrollX(), mContentLayout.getScrollY(), -mSlideWidth, 0);
            mIsClosed = false;
            invalidate();
        }
    }

    public void closeMenu() {
        if (!mIsClosed) {
            mScroller.startScroll(mContentLayout.getScrollX(), mContentLayout.getScrollY(), mSlideWidth, 0);
            mIsClosed = true;
            invalidate();
        }
    }

}
