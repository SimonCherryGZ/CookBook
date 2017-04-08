package com.simoncherry.cookbook.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Simon on 2017/4/8.
 */

public class RealmCategory extends RealmObject {

    @PrimaryKey
    private String ctgId;
    private String name;
    private String parentId;
    private boolean isChild;
    private boolean isSelected;

    public String getCtgId() {
        return ctgId;
    }

    public void setCtgId(String ctgId) {
        this.ctgId = ctgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isChild() {
        return isChild;
    }

    public void setChild(boolean child) {
        isChild = child;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "RealmCategory{" +
                "ctgId='" + ctgId + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", isChild=" + isChild +
                ", isSelected=" + isSelected +
                '}';
    }
}
