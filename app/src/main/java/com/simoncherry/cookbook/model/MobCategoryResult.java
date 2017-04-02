package com.simoncherry.cookbook.model;

import java.util.ArrayList;

/**
 * Created by Simon on 2017/3/27.
 */

public class MobCategoryResult {

    private MobCategory categoryInfo;
    private ArrayList<MobCategoryChild1> childs;

    public MobCategory getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(MobCategory categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public ArrayList<MobCategoryChild1> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<MobCategoryChild1> childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return "MobCategoryResult{" +
                "categoryInfo=" + categoryInfo +
                ", childs=" + childs +
                '}';
    }
}
