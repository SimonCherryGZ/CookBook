package com.simoncherry.cookbook.contract;

import com.simoncherry.cookbook.model.MobCategoryResult;

/**
 * Created by Simon on 2017/4/20.
 */

public interface CategoryContract {

    interface View extends BaseView<Presenter> {
        void onQueryCategorySuccess(MobCategoryResult result);
    }

    interface Presenter extends BasePresenter {
        void queryCategory();
    }
}
