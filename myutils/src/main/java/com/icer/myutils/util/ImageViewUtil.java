package com.icer.myutils.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.ImageView;

@SuppressWarnings("ALL")
public class ImageViewUtil {
    /**
     * 调整图片大小,保证图片比例不变
     *
     * @param activity    当前Activity
     * @param iv          目标ImageView
     * @param drawableId  R.drawable.xxx
     * @param imageWidth  图片宽 px,如果null,则去尝试读取图片信息,但有可能失败
     * @param imageHeight 图片高 px,如果null,则去尝试读取图片信息,但有可能失败
     */
    public static void fixImageRatio(Activity activity, ImageView iv, int drawableId, Float imageWidth, Float imageHeight) {
        fixImageRatio(activity, iv, drawableId, imageWidth, imageHeight, 0, 0);
    }

    public static void fixImageRatio(Activity activity, ImageView iv, int drawableId, Float imageWidth, Float imageHeight, float leftMargin, float rightMargin) {
        ViewGroup.LayoutParams lp = iv.getLayoutParams();
        float ratio;
        if (imageHeight == null || imageWidth == null) {
            Bitmap bm = BitmapFactory.decodeResource(activity.getResources(), drawableId);
            ratio = bm.getHeight() / bm.getWidth();
        } else {
            ratio = imageHeight / imageWidth;
        }
        lp.width = activity.getWindowManager().getDefaultDisplay().getWidth() - ScreenUtil.dipTopx(activity, leftMargin) - ScreenUtil.dipTopx(activity, rightMargin);
        lp.height = (int) (ratio * activity.getWindowManager().getDefaultDisplay().getWidth());
        iv.setLayoutParams(lp);
    }
}
