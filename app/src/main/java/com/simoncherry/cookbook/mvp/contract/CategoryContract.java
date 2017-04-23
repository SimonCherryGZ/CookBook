package com.simoncherry.cookbook.mvp.contract;

import com.simoncherry.cookbook.model.MobCategoryResult;

/**
 * Created by Simon on 2017/4/20.
 */

public interface CategoryContract {

    interface View extends BaseApiView<Presenter> {
        void onQueryCategorySuccess(MobCategoryResult result);
    }

    interface Presenter extends BasePresenter {
        void queryCategory();
    }
}
