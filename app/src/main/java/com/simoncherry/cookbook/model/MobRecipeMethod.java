package com.simoncherry.cookbook.model;

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

    @Override
    public String toString() {
        return "MobRecipeMethod{" +
                "img='" + img + '\'' +
                ", step='" + step + '\'' +
                '}';
    }
}
