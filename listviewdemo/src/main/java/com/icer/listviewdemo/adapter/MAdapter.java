package com.icer.listviewdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icer.listviewdemo.R;

import java.util.List;

/**
 * Created by icer on 2016/2/26.
 */
public class MAdapter extends BaseAdapter {

    private Context mContext;
    private List mData;

    public MAdapter(Context context, List data) {
        mContext = context;
        mData = data;
    }

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

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mContext);
    }
}
