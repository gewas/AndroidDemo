package com.icer.listviewdemo.ui;

import android.content.Intent;
import android.view.View;

import com.icer.listviewdemo.R;
import com.icer.myutils.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent() {
        findViewById(R.id.main_btn_1).setOnClickListener(this);
        findViewById(R.id.main_btn_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_1: {
                startActivity(new Intent(this, ListView1UI.class));
            }
            break;

            case R.id.main_btn_2: {
                startActivity(new Intent(this, ListView2UI.class));
            }
            break;
        }
    }
}
