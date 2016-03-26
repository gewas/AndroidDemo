package com.icer.myutils.util.image.cache;

import android.graphics.Bitmap;

/**
 * Created by icer-SP4 on 2016/3/26.
 */
public interface ImageCache {
    void put(String url, Bitmap bitmap);

    Bitmap get(String url);
}
