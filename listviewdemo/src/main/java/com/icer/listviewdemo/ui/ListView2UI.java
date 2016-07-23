package com.icer.listviewdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.icer.listviewdemo.R;
import com.icer.listviewdemo.adapter.MAdapter;
import com.icer.myutils.base.UIActivity;
import com.icer.myutils.util.random.RandomDataUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 跳到指定位置ListView
 * Created by icer on 2016/2/26.
 */
public class ListView2UI extends UIActivity implements View.OnClickListener {

    private ListView mListView;
    private MAdapter mAdapter;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        initData();
        initView();
        regListener();
        fillData();
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        Collections.addAll(mData, RandomDataUtil.getStrings(100, 1, 26, true, true, false, " "));
        mAdapter = new MAdapter(this, mData);
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.lv2);
    }

    @Override
    protected void regListener() {
        findViewById(R.id.ui2_btn1).setOnClickListener(this);
        findViewById(R.id.ui2_btn2).setOnClickListener(this);
        findViewById(R.id.ui2_btn3).setOnClickListener(this);
    }

    @Override
    protected void fillData() {
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ui2_btn1: {
                int position = RandomDataUtil.nextInt(mData.size());
                mListView.setSelection(position);
                showToastShort("Item: " + position);
            }
            break;

            case R.id.ui2_btn2: {
                int position = RandomDataUtil.nextInt(mData.size());
                mListView.smoothScrollToPosition(position);
                showToastShort("Item: " + position);
            }
            break;
            case R.id.ui2_btn3: {
                int position = RandomDataUtil.nextInt(mData.size());
                int pn = RandomDataUtil.aBoolean() ? -1 : 1;
                mListView.smoothScrollBy(pn * position * 50, 1000);
                showToastShort("Distance: " + pn * position * 50);
            }
            break;
        }
    }
}
