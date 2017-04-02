package com.simoncherry.cookbook.util;

import android.widget.ImageView;

/**
 * Created by Simon on 2017/4/2.
 */

public interface BaseLoader {

    void loadImage(String url, ImageView imageView);

    void loadImage(String url, int placeholder, ImageView imageView);

    void loadImage(String url, int placeholder, int error, ImageView imageView);

    void loadImage(String url, int placeholder, int error, boolean isAnimate, ImageView imageView);
}
