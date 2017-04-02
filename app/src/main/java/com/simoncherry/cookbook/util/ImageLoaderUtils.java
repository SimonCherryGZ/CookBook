package com.simoncherry.cookbook.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.simoncherry.cookbook.R;

/**
 * Created by Simon on 2017/4/2.
 */

public class ImageLoaderUtils {

    private static ImageLoaderUtils mInstance;
    private static BaseLoader mLoader = new GlideLoader(R.drawable.default_img, R.drawable.default_img);

    public ImageLoaderUtils() {
        setLoader(new GlideLoader(R.drawable.default_img, R.drawable.default_img));
    }

    public static void setLoader(BaseLoader mLoader) {
        ImageLoaderUtils.mLoader = mLoader;
    }

    public static ImageLoaderUtils getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtils.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtils();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        mLoader.loadImage(url, imageView);
    }

    public void loadImage(String url, ImageView imageView) {
        mLoader.loadImage(url, imageView);
    }

    public void loadImage(String url, int placeholder, ImageView imageView) {
        mLoader.loadImage(url, placeholder, imageView);
    }

    public void loadImage(String url, int placeholder, int error, ImageView imageView) {
        mLoader.loadImage(url, placeholder, error, imageView);
    }

    public void loadImage(String url, int placeholder, int error, boolean isAnimate, ImageView imageView) {
        mLoader.loadImage(url, placeholder, error, isAnimate, imageView);
    }
}
