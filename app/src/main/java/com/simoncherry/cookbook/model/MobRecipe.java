package com.simoncherry.cookbook.model;

import java.util.List;

/**
 * Created by Simon on 2017/3/28.
 */

public class MobRecipe {

    private List<String> ctgIds;
    private String ctgTitles;
    private String menuId;
    private String name;
    private MobRecipeDetail recipe;
    private String thumbnail;

    public List<String> getCtgIds() {
        return ctgIds;
    }

    public void setCtgIds(List<String> ctgIds) {
        this.ctgIds = ctgIds;
    }

    public String getCtgTitles() {
        return ctgTitles;
    }

    public void setCtgTitles(String ctgTitles) {
        this.ctgTitles = ctgTitles;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MobRecipeDetail getRecipeDetail() {
        return recipe;
    }

    public void setRecipeDetail(MobRecipeDetail recipeDetail) {
        this.recipe = recipeDetail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "MobRecipe{" +
                "ctgIds=" + ctgIds +
                ", ctgTitles='" + ctgTitles + '\'' +
                ", menuId='" + menuId + '\'' +
                ", name='" + name + '\'' +
                ", recipeDetail=" + recipe +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
