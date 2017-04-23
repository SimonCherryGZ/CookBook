package com.simoncherry.cookbook.mvp.contract;

import com.simoncherry.cookbook.model.MobRecipeResult;

/**
 * Created by Simon on 2017/4/20.
 */

public interface RecipeContract {

    interface View extends BaseApiView<Presenter> {
        void onQueryRecipeSuccess(MobRecipeResult result);
    }

    interface Presenter extends BasePresenter {
        void queryRecipe(String cid, int page, int size);
        void queryRecipeByField(String field, String value, int page, int size);
    }
}
