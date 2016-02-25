package com.icer.listviewdemo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.icer.myutils.base.UIActivity;
import com.icer.myutils.util.random.RandomDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 弹性ListView
 * Created by icer on 2016/2/25.
 */
public class ListView1UI extends UIActivity {
    private ListView mLV;

    private BaseAdapter mAdapter;
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
        for (String str : RandomDataUtil.getStrings(50, 1, 13, true, true, true)) {
            mData.add(str);
        }
    }

    @Override
    protected void initView() {
        mLV = (ListView) findViewById(R.id.lv1);
        mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mData.size();
            }

            @Override
            public Object getItem(int position) {
                return mData.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item, parent, false);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.item_tv);
                tv.setText(getItem(position).toString());
                return convertView;
            }
        };
    }

    @Override
    protected void regListener() {

    }

    @Override
    protected void fillData() {
        mLV.setAdapter(mAdapter);
    }
}
