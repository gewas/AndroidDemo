package com.icer.myutils.base;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

/**
 * BaseViewHolder for BaseAdapter
 *
 * @see MultiTypeBaseAdapter
 * @see SingleTypeBaseAdapter
 * Created by icer on 2016/7/22.
 */
public abstract class BaseViewHolder<T> {
    protected Context mContext;
    protected ArrayList<T> mData;

    public BaseViewHolder(Context context, ArrayList<T> data) {
        mContext = context;
        mData = data;
    }

    public Context getContext() {
        return mContext;
    }

    public ArrayList<T> getData() {
        return mData;
    }

    protected abstract void initView(View view);

    protected abstract void initState();

    protected abstract void fillData(int position);
}
