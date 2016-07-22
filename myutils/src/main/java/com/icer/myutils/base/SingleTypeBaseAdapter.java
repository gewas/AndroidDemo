package com.icer.myutils.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * SingleTypeBaseAdapter for ListView, GridView;
 *
 * @see android.widget.ListView
 * @see android.widget.GridView
 * Created by icer on 2016/7/22.
 */
public abstract class SingleTypeBaseAdapter<T> extends android.widget.BaseAdapter {

    protected Context mContext;
    protected ArrayList<T> mData;

    public SingleTypeBaseAdapter(Context context, ArrayList<T> data) {
        mContext = context;
        mData = data;
    }

    public Context getContext() {
        return mContext;
    }

    public ArrayList<T> getData() {
        return mData;
    }

    protected abstract int getItemLayout();

    protected abstract Class<? extends BaseViewHolder> getViewHolderClass();

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
        BaseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(getItemLayout(), parent, false);
            try {
                Constructor constructor = getViewHolderClass().getConstructor(Context.class, mData.getClass());
                constructor.setAccessible(true);
                holder = (BaseViewHolder) constructor.newInstance(mContext, mData);
                holder.initView(convertView);
                convertView.setTag(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        holder = (BaseViewHolder) convertView.getTag();
        holder.initState();
        holder.fillData(position);
        return convertView;
    }
}
