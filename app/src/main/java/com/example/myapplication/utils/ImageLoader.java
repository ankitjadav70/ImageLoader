package com.example.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    private LruCache<String, Bitmap> memoryCache;
    private Context context;
   private static ImageLoader imageLoader;

    public static ImageLoader getInstance(Context context) {
        if(imageLoader ==null) {
           return imageLoader = new ImageLoader(context);
        }else {
            return imageLoader;
        }
    private ImageLoader(Context context) {
        this.context = context;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public Bitmap loadImage(String imageUrl,String imageName) {
        Bitmap bitmap = null;

        // Check if image is in memory cache
        bitmap = memoryCache.get(imageName);

        if (bitmap == null) {
            // Check if image is in disk cache
            bitmap = readFromDiskCache(imageName);

            if (bitmap == null) {
                // Load image from network
                bitmap = loadFromNetwork(imageUrl);

                if (bitmap != null) {
                    // Save image to memory cache
                    memoryCache.put(imageName, bitmap);
                    // Save image to disk cache
                    saveToDiskCache(imageName, bitmap);
                }
            } else {
                // Save image to memory cache
                memoryCache.put(imageName, bitmap);
            }
        }

        return bitmap;
    }

    private Bitmap loadFromNetwork(String imageUrl) {
        try
        {
            URL url = new URL(imageUrl);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap readFromDiskCache(String imageUrl) {
        Bitmap bitmap = null;
        try {
            File file = new File(context.getCacheDir(), imageUrl);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void saveToDiskCache(String imageUrl, Bitmap bitmap) {
        try {
            File file = new File(context.getCacheDir(), imageUrl);
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
