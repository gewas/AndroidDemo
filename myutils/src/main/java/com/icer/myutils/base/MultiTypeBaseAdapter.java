package com.icer.myutils.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * MultiTypeBaseAdapter for ListView, GridView;
 *
 * @see android.widget.ListView
 * @see android.widget.GridView
 * Created by icer on 2016/7/22.
 */
public abstract class MultiTypeBaseAdapter<T> extends android.widget.BaseAdapter {

    protected Context mContext;
    protected ArrayList<T> mData;

    public MultiTypeBaseAdapter(Context context, ArrayList<T> data) {
        mContext = context;
        mData = data;
    }

    protected abstract int getItemTypeCount();

    protected abstract int getItemType(int position);

    protected abstract int getItemLayout(int position);

    protected abstract Class<? extends BaseViewHolder> getViewHolderClass(int position);

    @Override
    public int getViewTypeCount() {
        return getItemTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
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
        BaseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(getItemLayout(position), parent, false);
            try {
                Constructor constructor = getViewHolderClass(position).getConstructor(Context.class, mData.getClass());
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
