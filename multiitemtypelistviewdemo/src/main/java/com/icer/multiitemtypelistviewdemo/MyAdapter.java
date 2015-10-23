package com.icer.multiitemtypelistviewdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by icer on 2015/10/22.
 */
public class MyAdapter extends BaseAdapter {
    //ItemViewType必须由0开始......因为其涉及到ListView内部的机制
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CONTENT = 1;
    public static final String KEY_TYPE = "type";
    public static final String KEY_VALUE = "value";

    private int convertViewCount;

    private Context mContext;
    private LinkedList<Map<String, String>> mData;

    public MyAdapter(Context context, LinkedList<Map<String, String>> data) {
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
        int type = getItemViewType(position);
        BaseViewHolder viewHolder = null;
        if (convertView == null) {
            Log.i("convertView", ++convertViewCount + "");
            switch (type) {
                case TYPE_HEADER:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_header, parent, false);
                    viewHolder = new ViewHolderHeader();
                    break;

                case TYPE_CONTENT:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_content, parent, false);
                    viewHolder = new ViewHolderContent();
                    break;
            }
            viewHolder.initView(convertView);
            convertView.setTag(viewHolder);
        }
        if (viewHolder == null)
            viewHolder = (BaseViewHolder) convertView.getTag();
        viewHolder.fillData(position);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(mData.get(position).get(KEY_TYPE));
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private class ViewHolderHeader extends BaseViewHolder {
        private TextView header;

        @Override
        public void initView(View view) {
            header = (TextView) view.findViewById(R.id.item_header_tv);
        }

        @Override
        public void fillData(int position) {
            String str = mData.get(position).get(KEY_VALUE);
            header.setText(str);
        }
    }

    private class ViewHolderContent extends BaseViewHolder {
        private ImageView icon;
        private TextView text;

        @Override
        public void initView(View view) {
            icon = (ImageView) view.findViewById(R.id.icon_iv);
            text = (TextView) view.findViewById(R.id.text_tv);
        }

        @Override
        public void fillData(int position) {
            String str = mData.get(position).get(KEY_VALUE);
            text.setText(str);
        }
    }
}
