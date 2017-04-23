package com.simoncherry.cookbook.mvp.contract;

import com.simoncherry.cookbook.model.MobRecipeResult;
import com.simoncherry.cookbook.ui.BaseView;
import com.simoncherry.cookbook.mvp.presenter.BasePresenter;

/**
 * Created by Simon on 2017/4/20.
 */

public interface RecipeContract {

    interface View extends BaseView{
        void onQueryRecipeSuccess(MobRecipeResult result);
    }

    interface Presenter extends BasePresenter<View> {
        void queryRecipe(String cid, int page, int size);
        void queryRecipeByField(String field, String value, int page, int size);
    }
}
