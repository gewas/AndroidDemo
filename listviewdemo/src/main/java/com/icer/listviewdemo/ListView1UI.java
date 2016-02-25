package com.icer.listviewdemo;

import android.os.Bundle;
import android.widget.ListView;

import com.icer.myutils.base.BaseActivity;

/**
 * 弹性ListView
 * Created by icer on 2016/2/25.
 */
public class ListView1UI extends BaseActivity {
    private ListView mLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsSwipeFinish(true);
    }
}
