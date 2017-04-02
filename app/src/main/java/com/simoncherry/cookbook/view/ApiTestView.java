package com.simoncherry.cookbook.view;

import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;

/**
 * Created by Simon on 2017/3/28.
 */

public interface ApiTestView {

    void onQueryCategorySuccess(MobCategoryResult value);

    void onQueryRecipe(MobRecipeResult value);

    void onQueryDetail(MobRecipe value);
}
