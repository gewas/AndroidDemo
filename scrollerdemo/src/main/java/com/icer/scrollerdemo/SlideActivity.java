package com.icer.scrollerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2015-07-30.
 */
public class SlideActivity extends Activity {
    private SlideMenu mSlideMenu;

    private float xDown;
    private float yDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);
        mSlideMenu = (SlideMenu) findViewById(R.id.slide_menu);
        findViewById(R.id.slide_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSlideMenu.isClosed())
                    mSlideMenu.openMenu();
                else
                    mSlideMenu.closeMenu();
            }
        });
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
                        if (event.getX() - xDown > 0 && mSlideMenu.isClosed())
                            mSlideMenu.openMenu();
                        else if (event.getX() - xDown < 0 && !mSlideMenu.isClosed())
                            mSlideMenu.closeMenu();
                    }
                }
                return true;

        }

        return super.onTouchEvent(event);
    }
}
