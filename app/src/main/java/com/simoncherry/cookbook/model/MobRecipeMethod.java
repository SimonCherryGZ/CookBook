package com.simoncherry.cookbook.model;

import android.text.TextUtils;

/**
 * Created by Simon on 2017/3/28.
 */

public class MobRecipeMethod {

    private String img;
    private String step;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public boolean isImgAvailable() {
        return img != null && !TextUtils.isEmpty(img);
    }

    @Override
    public String toString() {
        return "MobRecipeMethod{" +
                "img='" + img + '\'' +
                ", step='" + step + '\'' +
                '}';
    }
}
