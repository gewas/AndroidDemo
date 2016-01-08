package com.icer.textviewlinkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final String TEXT = "This is a text: http://stackoverflow.com , and this is another: https://github.com/icer-CHN/AndroidDemo . END";
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        setLinkText(tv, TEXT);
    }

    private void setLinkText(TextView textView, String text) {
        textView.setText(text);
        CharSequence cs = textView.getText();
        if (cs instanceof Spannable) {
            Spannable spannable = (Spannable) cs;
            URLSpan[] urls = spannable.getSpans(0, cs.length(), URLSpan.class);
            SpannableStringBuilder ssb = new SpannableStringBuilder(cs);
            for (URLSpan url : urls) {
                LinkSpan linkSpan = new LinkSpan(this, url.getURL(), false);
                ssb.setSpan(linkSpan, spannable.getSpanStart(url), spannable.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            textView.setText(ssb);
        }
    }
}
