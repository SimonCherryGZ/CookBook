package com.simoncherry.cookbook.mvp.contract;

import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;
import com.simoncherry.cookbook.ui.BaseView;
import com.simoncherry.cookbook.mvp.presenter.BasePresenter;

/**
 * Created by Simon on 2017/4/19.
 */

public interface ApiTestContract {

    interface View extends BaseView {
        void onQueryCategorySuccess(MobCategoryResult value);
        void onQueryRecipe(MobRecipeResult value);
        void onQueryDetail(MobRecipe value);
    }

    interface Presenter extends BasePresenter<View> {
        void queryCategory();
        void queryRecipe(String cid, int page, int size);
        void queryDetail(String id);
    }
}
