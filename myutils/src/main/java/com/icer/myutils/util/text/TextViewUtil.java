package com.icer.myutils.util.text;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class TextViewUtil {

    /**
     * 在TextView文字内容为"xxxxxxx(marker)yyyyyyy"时,向目标位置(marker)后填充文字内容,并去掉"yyyyyyy".
     */
    public static void setTextAfterMarker(TextView tv, String marker, String text) {
        if (text == null || text.length() == 0)
            text = "";
        tv.setText(tv.getText().toString().substring(0, tv.getText().toString().indexOf(marker) + 1) + "%s");
        tv.setText(String.format(tv.getText().toString(), text));
    }

    /**
     * 在TextView文字内容为"xxxxxxx(marker)yyyyyyy"时,向目标位置(marker)后填充指定颜色的文字内容,并去掉"yyyyyyy".
     *
     * @param text           填充的文字内容
     * @param defaultColorId R.color.xxx 如果不想改变默认颜色(标志前的文字颜色),可以填null
     * @param contentColorId R.color.xxx
     */
    public static void setSubColorTextAfterMarker(Context context, TextView tv, String marker, String text, Integer defaultColorId, Integer contentColorId) {
        if (text == null || text.length() == 0)
            text = "";
        if (defaultColorId != null)
            tv.setTextColor(context.getResources().getColor(defaultColorId));
        tv.setText(tv.getText().toString().substring(0, tv.getText().toString().indexOf(marker) + 1) + "%s");
        String foo = String.format(tv.getText().toString(), text);
        SpannableStringBuilder style = new SpannableStringBuilder(foo);
        if (contentColorId != null)
            style.setSpan(new ForegroundColorSpan(context.getResources().getColor(contentColorId)), foo.indexOf(marker) + 1, foo.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(style);
    }

    /**
     * 在TextView文字内容为"xxxxx%sxxxx"(一个格式化占位符)或"xxxx%1$sxxxx%2$x......xxxx%n$sxxxx"时(多个格式化占位符),完成格式化同时,设置新加入的文字颜色,同时也能够设置原来文字的颜色;
     * <p/>
     * 注:请务必保证单个格式化时,使用%s占位符;多格式化时,使用%n$s占位符;
     * 占位符数必须和想填入的字符串数目一致;
     *
     * @param texts             如果可变参数长度为0,不做处理;如果文字长度为0,默认为""
     * @param defaultColorId    R.color.xxx 如果不想改变默认颜色(标志前的文字颜色),可以填null
     * @param newContentColorId R.color.xxx
     */
    public static void setSubColorText(Context context, TextView tv, Integer defaultColorId, Integer[] newContentColorIds, String... texts) {

        if (texts != null) {
            if (texts.length == 1) {//单格式化参数情况
                if (defaultColorId != null)//1.如果有设置改编默认文字颜色,给予改变
                    tv.setTextColor(context.getResources().getColor(defaultColorId));

                String text = texts[0];
                if (TextUtils.isEmpty(text))//2.如果文字内容为null或者长度0,默认其为""
                    text = "";

                String originText = tv.getText().toString();//3.格式化,记录添加的字符串的起止index
                int indexStart = originText.indexOf("%s");
                int indexEnd = indexStart + text.length();
                String foo = String.format(originText, text);
                tv.setText(foo);

                if (indexEnd > indexStart) {//4.如果有必要换色,执行
                    SpannableStringBuilder style = new SpannableStringBuilder(foo);
                    if (newContentColorIds != null && newContentColorIds.length > 0)
                        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(newContentColorIds[0])), indexStart, indexEnd, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tv.setText(style);
                }

            } else if (texts.length > 1) {//多格式化
                if (defaultColorId != null)//1.如果有设置改编默认文字颜色,给予改变
                    tv.setTextColor(context.getResources().getColor(defaultColorId));

                int[] indexesStart = new int[texts.length];
                int[] indexesEnd = new int[texts.length];
                String originText = tv.getText().toString();

                for (int i = 0; i < texts.length; i++) {
                    String text = texts[i];
                    if (TextUtils.isEmpty(text)) {//2.如果文字内容为null或者长度0,默认其为""
                        text = "";
                    }

                    String regular = "%" + (i + 1) + "$s";//3.格式化,记录添加的字符串的起止index
                    indexesStart[i] = originText.indexOf(regular);
                    if (i > 0) {
                        int indexFix = 0;
                        for (int j = 0; j <= i - 1; j++) {
                            String formerRegular = "%" + (j + 1) + "$s";
                            indexFix += (indexesEnd[j] - indexesStart[j]) - formerRegular.length();
                        }
                        indexesStart[i] += indexFix;
                    }
                    indexesEnd[i] = indexesStart[i] + text.length();
                }
                String foo = String.format(originText, (Object[]) texts);
                tv.setText(foo);
                SpannableStringBuilder style = new SpannableStringBuilder(foo);
                int j = 0;
                for (int i = 0; i < texts.length; i++) {
                    if (indexesEnd[i] > indexesStart[i])//4.如果有必要换色,执行
                        if (newContentColorIds != null && newContentColorIds.length > j)
                            style.setSpan(new ForegroundColorSpan(context.getResources().getColor(newContentColorIds[j++])), indexesStart[i], indexesEnd[i], Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                tv.setText(style);
            }
        }
    }
}
