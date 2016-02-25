package com.icer.myutils.base;

import android.os.Bundle;

/**
 * Created by icer on 2016/2/25.
 */
public abstract class UIActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void regListener();

    protected abstract void fillData();

}
