package com.icer.multiitemtypelistviewdemo;

import android.view.View;

/**
 * Created by icer on 2015/10/22.
 */
public abstract class BaseViewHolder {
    public abstract void initView(View view);

    public abstract void fillData(int position);
}
