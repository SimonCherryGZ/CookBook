package com.simoncherry.cookbook.presenter;

/**
 * Created by Simon on 2017/3/29.
 */

public interface RecipePresenter {

    void queryRecipe(String cid, int page, int size);

    void queryRecipeByField(String field, String value, int page, int size);
}
