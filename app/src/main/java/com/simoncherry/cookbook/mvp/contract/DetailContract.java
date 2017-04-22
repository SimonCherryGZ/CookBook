package com.simoncherry.cookbook.mvp.contract;

import com.simoncherry.cookbook.model.MobRecipe;

/**
 * Created by Simon on 2017/4/20.
 */

public interface DetailContract {

    interface View extends BaseView<Presenter> {
        void onQueryDetailSuccess(MobRecipe value);
    }

    interface Presenter extends BasePresenter {
        void queryDetail(String id);
    }
}
