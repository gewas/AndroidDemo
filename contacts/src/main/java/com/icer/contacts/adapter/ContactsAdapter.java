package com.icer.contacts.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icer.contacts.R;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by icer on 2015/10/27.
 */
public class ContactsAdapter extends BaseAdapter {
    public static final String TAG = ContactsAdapter.class.getCanonicalName();

    public static final String KEY_TYPE = "type";
    public static final String KEY_HEADER = "header";
    public static final String KEY_NAME = "name";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_PHONE = "phone";

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_CONTENT = 1;


    private Context mContext;
    private LayoutInflater mInflater;
    private List<Map<String, String>> mData;

    public ContactsAdapter(Context context, List<Map<String, String>> data) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        int viewType = getItemViewType(position);
        BaseViewHolder viewHolder = null;
        if (convertView == null) {
            switch (viewType) {
                case VIEW_TYPE_HEADER:
                    convertView = mInflater.inflate(R.layout.item_header, parent, false);
                    viewHolder = new ViewHolderHeader();
                    break;

                case VIEW_TYPE_CONTENT:
                    convertView = mInflater.inflate(R.layout.item_content, parent, false);
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Map<String, String> map = mData.get(position);
        int type = Integer.parseInt(map.get(KEY_TYPE));
        switch (type) {
            case VIEW_TYPE_HEADER:
                return VIEW_TYPE_HEADER;

            case VIEW_TYPE_CONTENT:
                return VIEW_TYPE_CONTENT;
        }
        return super.getItemViewType(position);
    }

    public void addData(Collection<Map<String, String>> data) {
        if (data != null && !data.isEmpty()) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    private class ViewHolderHeader extends BaseViewHolder {

        private TextView header;

        @Override
        public void initView(View view) {
            header = (TextView) view.findViewById(R.id.header_tv);
        }

        @Override
        public void fillData(int position) {
            header.setText(mData.get(position).get(KEY_HEADER));
        }
    }

    private class ViewHolderContent extends BaseViewHolder {

        private ImageView avatar;
        private TextView name;
        private TextView phone;

        @Override
        public void initView(View view) {
            avatar = (ImageView) view.findViewById(R.id.avatar_iv);
            name = (TextView) view.findViewById(R.id.name_tv);
            phone = (TextView) view.findViewById(R.id.phone_tv);
        }

        @Override
        public void fillData(int position) {
            Map<String, String> map = mData.get(position);
            avatar.setImageResource(R.drawable.ic_launcher);
            String avatarStr = map.get(KEY_AVATAR);
            if (avatarStr != null && avatarStr.length() > 0) {
                Uri avatarUri = Uri.parse(avatarStr);
                InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(mContext.getContentResolver(), avatarUri);
                avatar.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            }
            name.setText(map.get(KEY_NAME));
            phone.setText(map.get(KEY_PHONE));
        }
    }
}
