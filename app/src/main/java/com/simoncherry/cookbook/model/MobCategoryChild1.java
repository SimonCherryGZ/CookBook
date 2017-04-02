package com.simoncherry.cookbook.model;

import java.util.ArrayList;

/**
 * Created by Simon on 2017/3/27.
 */

public class MobCategoryChild1 {

    private MobCategory categoryInfo;
    private ArrayList<MobCategoryChild2> childs;

    public MobCategory getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(MobCategory categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public ArrayList<MobCategoryChild2> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<MobCategoryChild2> childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return "MobCategoryChild1{" +
                "categoryInfo=" + categoryInfo +
                ", childs=" + childs +
                '}';
    }
}
