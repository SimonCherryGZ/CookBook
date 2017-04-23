package com.simoncherry.cookbook.mvp.contract;

import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.ui.BaseView;
import com.simoncherry.cookbook.mvp.presenter.BasePresenter;

/**
 * Created by Simon on 2017/4/20.
 */

public interface CategoryContract {

    interface View extends BaseView {
        void onQueryCategorySuccess(MobCategoryResult result);
    }

    interface Presenter extends BasePresenter<View> {
        void queryCategory();
    }
}
