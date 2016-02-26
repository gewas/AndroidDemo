package com.icer.listviewdemo.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.icer.myutils.util.common.ScreenUtil;

/**
 * Created by icer on 2016/2/25.
 */
public class FlexibleListView extends ListView {
    private Context mContext;

    public FlexibleListView(Context context) {
        super(context);
        mContext = context;
    }

    public FlexibleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public FlexibleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, ScreenUtil.dip2Px(mContext, 128), isTouchEvent);
    }
}
