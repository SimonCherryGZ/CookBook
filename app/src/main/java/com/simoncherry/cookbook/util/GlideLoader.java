package com.simoncherry.cookbook.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Simon on 2017/4/2.
 */

public class GlideLoader implements BaseLoader {

    @Override
    public void loadImage(String url, ImageView imageView) {
        loadImage(url, 0, 0, true, imageView);
    }

    @Override
    public void loadImage(String url, int placeholder, ImageView imageView) {
        loadImage(url, placeholder, 0, true, imageView);
    }

    @Override
    public void loadImage(String url, int placeholder, int error, ImageView imageView) {
        loadImage(url, placeholder, error, true, imageView);
    }

    @Override
    public void loadImage(String url, int placeholder, int error, boolean isAnimate, ImageView imageView) {
        if (isAnimate) {
            Glide.with(imageView.getContext())
                    .load(url).centerCrop()
                    .placeholder(placeholder)
                    .error(error)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(url).centerCrop()
                    .placeholder(placeholder)
                    .error(error)
                    .dontAnimate()
                    .into(imageView);
        }
    }
}
