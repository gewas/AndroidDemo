package com.icer.listviewdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.icer.listviewdemo.R;
import com.icer.myutils.base.UIActivity;

public class MainActivity extends UIActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        regListener();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void regListener() {
        findViewById(R.id.main_btn_1).setOnClickListener(this);
        findViewById(R.id.main_btn_2).setOnClickListener(this);
    }

    @Override
    protected void fillData() {

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
