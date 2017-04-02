package com.simoncherry.cookbook.model;

import android.text.TextUtils;

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
        return title != null ? title : "";
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
        if (ingredients != null) {
            ingredients = ingredients.replace("[", "");
            ingredients = ingredients.replace("]", "");
            ingredients = ingredients.replace("\"", "");
        }
        return ingredients != null && !TextUtils.isEmpty(ingredients) ? ingredients : "不详";
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipeMethods() {
        return method != null ? method : "";
    }

    public void setRecipeMethods(String recipeMethods) {
        this.method = recipeMethods;
    }

    public String getSumary() {
        return sumary != null ? sumary : "";
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
