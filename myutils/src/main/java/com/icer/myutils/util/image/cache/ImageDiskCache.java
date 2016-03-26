package com.icer.myutils.util.image.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.icer.myutils.util.common.MD5Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by icer-SP4 on 2016/3/26.
 */
public class ImageDiskCache implements ImageCache {

    private String mCacheDir = "/sdcard/cache/";

    public ImageDiskCache setCacheDir(String cacheDir) {
        mCacheDir = cacheDir;
        return this;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            File file = new File(getFilePath(url));
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(getFilePath(url));
    }

    private String getFilePath(String url) {
        return mCacheDir + File.separator + MD5Util.MD5(url);
    }
}
