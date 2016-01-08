package com.icer.textviewlinkdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

/**
 * Created by icer on 2016/1/8.
 */
public class LinkSpan extends ClickableSpan {
    public static final String TAG = LinkSpan.class.getSimpleName();

    private Context mContext;
    private String mUrl;
    private boolean mUnderline;

    public LinkSpan(Context context, String url, boolean underline) {
        mContext = context;
        mUrl = url;
        mUnderline = underline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(mUnderline);
    }

    @Override
    public void onClick(View widget) {
        Log.i(TAG, "Link: " + mUrl);
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)));
    }
}
