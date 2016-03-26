package com.icer.myutils.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.icer.myutils.util.image.cache.ImageCache;
import com.icer.myutils.util.image.cache.ImageMemoryCache;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by icer-SP4 on 2016/3/26.
 */
public class ImageLoader {

    public static final String TAG = ImageLoader.class.getCanonicalName();

    //图片缓存
    private ImageCache mImageCache = new ImageMemoryCache();
    //线程池
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //主线程Handler
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void setImageCache(ImageCache imageCache) {
        mImageCache = imageCache;
    }

    public void displayImage(String imageUrl, final ImageView imageView) {
        final Bitmap bitmap = mImageCache.get(imageUrl);
        if (bitmap != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        } else {
            submitLoadRequest(imageUrl, imageView);
        }
    }

    private void submitLoadRequest(final String imageUrl, final ImageView imageView) {
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap != null) {
                    mImageCache.put(imageUrl, bitmap);
                    // 抛到主线程处理UI相关操作
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (imageUrl.equals(imageView.getTag())) {
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    });
                }
            }
        });
    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
