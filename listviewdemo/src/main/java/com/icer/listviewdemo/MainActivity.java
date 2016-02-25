package com.icer.listviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icer.myutils.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        regListener();
    }

    private void initView() {
        mButton1 = (Button) findViewById(R.id.main_btn_1);
    }

    private void regListener() {
        mButton1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_1: {
                startActivity(new Intent(this, ListView1UI.class));
            }
        }
    }
}
