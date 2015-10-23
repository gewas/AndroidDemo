package com.icer.multiitemtypelistviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private LinkedList<Map<String, String>> mData;

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        setAdapter();
    }

    private void initData() {
        mData = new LinkedList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            Map<String, String> map = new HashMap<>();
            map.put(MyAdapter.KEY_TYPE, MyAdapter.TYPE_HEADER + "");
            map.put(MyAdapter.KEY_VALUE, c + "");
            mData.add(map);
        }

        Random random = new Random();
        for (int i = 0; i < 230; i++) {
            String name = "";
            for (int j = 0; j < 3 + random.nextInt(5); j++) {
                char c = (char) ('a' + random.nextInt(26));
                if (j == 0) {
                    name += (c + "").toUpperCase();
                } else
                    name += c;
            }
            Map<String, String> map = new HashMap<>();
            map.put(MyAdapter.KEY_TYPE, MyAdapter.TYPE_CONTENT + "");
            map.put(MyAdapter.KEY_VALUE, name);
            mData.add(map);
        }
        Collections.sort(mData, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> lhs, Map<String, String> rhs) {
                String l = lhs.get(MyAdapter.KEY_VALUE);
                String r = rhs.get(MyAdapter.KEY_VALUE);
                return l.compareTo(r);
            }
        });
//        for (int i = 0; i < 118; i++) {
//            mData.removeLast();
//        }
        Log.i("Data", mData.toString());
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listView);
    }

    private void setAdapter() {
        mAdapter = new MyAdapter(this, mData);
        mListView.setAdapter(mAdapter);
    }


}
