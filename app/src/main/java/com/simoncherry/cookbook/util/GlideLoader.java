package com.simoncherry.cookbook.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Simon on 2017/4/2.
 */

public class GlideLoader implements BaseLoader {

    private int placeholderResId = 0;
    private int errorResId = 0;

    public GlideLoader() {
    }

    public GlideLoader(int placeholderResId, int errorResId) {
        this.placeholderResId = placeholderResId;
        this.errorResId = errorResId;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        loadImage(url, placeholderResId, errorResId, true, imageView);
    }

    @Override
    public void loadImage(String url, int placeholder, ImageView imageView) {
        loadImage(url, placeholder, errorResId, true, imageView);
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
