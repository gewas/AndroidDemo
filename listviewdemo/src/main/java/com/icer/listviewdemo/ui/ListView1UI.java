package com.icer.listviewdemo.ui;

import com.icer.listviewdemo.R;
import com.icer.listviewdemo.adapter.MAdapter;
import com.icer.listviewdemo.customView.FlexibleListView;
import com.icer.myutils.base.BaseActivity;
import com.icer.myutils.util.random.RandomDataUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 弹性ListView
 * 效果并不理想
 * Created by icer on 2016/2/25.
 */
public class ListView1UI extends BaseActivity {
    private FlexibleListView mLV;

    private MAdapter mAdapter;
    private List<String> mData;

    @Override
    protected int bindLayout() {
        return R.layout.activity_1;
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
        mLV.setAdapter(mAdapter);
    }
}
