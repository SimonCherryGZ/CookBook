package com.simoncherry.cookbook.api;

import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;

/**
 * Created by Simon on 2017/4/19.
 */

public interface ApiCallback {

    interface QueryCategoryCallback extends BaseCallback{
        void onQueryCategorySuccess(MobCategoryResult value);
        void onQueryCategoryEmpty();
    }

    interface QueryRecipeCallback extends BaseCallback {
        void onQueryRecipeSuccess(MobRecipeResult value);
        void onQueryRecipeEmpty();
    }

    interface QueryDetailCallback extends BaseCallback {
        void onQueryDetailSuccess(MobRecipe value);
        void onQueryDetailEmpty();
    }
}
