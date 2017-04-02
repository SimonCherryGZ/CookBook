package com.simoncherry.cookbook.model;

/**
 * Created by Simon on 2017/3/28.
 */

public class MobRecipeDetail {

    private String title;
    private String img;
    private String ingredients;
    private String method;
    private String sumary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipeMethods() {
        return method;
    }

    public void setRecipeMethods(String recipeMethods) {
        this.method = recipeMethods;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    @Override
    public String toString() {
        return "MobRecipeDetail{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", recipeMethods=" + method +
                ", sumary='" + sumary + '\'' +
                '}';
    }
}
