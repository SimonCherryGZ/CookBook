package com.simoncherry.cookbook.contract;

import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;

/**
 * Created by Simon on 2017/4/19.
 */

public interface ApiTestContract {

    interface View extends BaseView<Presenter> {
        void onQueryCategorySuccess(MobCategoryResult value);
        void onQueryRecipe(MobRecipeResult value);
        void onQueryDetail(MobRecipe value);
        void onQueryFailed();
        void onQueryError(String msg);
    }

    interface Presenter extends BasePresenter {
        void queryCategory();
        void queryRecipe(String cid, int page, int size);
        void queryDetail(String id);
    }
}
