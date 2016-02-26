package com.icer.listviewdemo.ui;

import android.os.Bundle;

import com.icer.listviewdemo.R;
import com.icer.listviewdemo.adapter.MAdapter;
import com.icer.listviewdemo.customView.FlexibleListView;
import com.icer.myutils.base.UIActivity;
import com.icer.myutils.util.random.RandomDataUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 弹性ListView
 * 效果并不理想
 * Created by icer on 2016/2/25.
 */
public class ListView1UI extends UIActivity {
    private FlexibleListView mLV;

    private MAdapter mAdapter;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        initData();
        initView();
        fillData();
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        Collections.addAll(mData, RandomDataUtil.getStrings(50, 1, 13, true, true, false, " "));
        mAdapter = new MAdapter(this, mData);
    }

    @Override
    protected void initView() {
        mLV = (FlexibleListView) findViewById(R.id.lv1);
    }

    @Override
    protected void regListener() {

    }

    @Override
    protected void fillData() {
        mLV.setAdapter(mAdapter);
    }
}
