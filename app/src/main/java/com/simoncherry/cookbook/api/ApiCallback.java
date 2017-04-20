package com.simoncherry.cookbook.api;

import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;

/**
 * Created by Simon on 2017/4/19.
 */

public interface ApiCallback {

    interface QueryCategoryCallback {
        void onQueryCategorySuccess(MobCategoryResult value);
        void onQueryFailed();
        void onQueryError(String msg);
    }

    interface QueryRecipeCallback {
        void onQueryRecipeSuccess(MobRecipeResult value);
        void onQueryFailed();
        void onQueryError(String msg);
    }

    interface QueryDetailCallback {
        void onQueryDetailSuccess(MobRecipe value);
        void onQueryFailed();
        void onQueryError(String msg);
    }
}
