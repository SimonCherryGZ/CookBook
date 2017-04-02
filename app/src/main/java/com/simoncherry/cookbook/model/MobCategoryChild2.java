package com.simoncherry.cookbook.model;

/**
 * Created by Simon on 2017/3/27.
 */

public class MobCategoryChild2 {

    private MobCategory categoryInfo;

    public MobCategory getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(MobCategory categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    @Override
    public String toString() {
        return "MobCategoryChild2{" +
                "categoryInfo=" + categoryInfo +
                '}';
    }
}
