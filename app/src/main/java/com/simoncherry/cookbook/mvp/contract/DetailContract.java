package com.simoncherry.cookbook.mvp.contract;

import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.ui.BaseView;
import com.simoncherry.cookbook.mvp.presenter.BasePresenter;

/**
 * Created by Simon on 2017/4/20.
 */

public interface DetailContract {

    interface View extends BaseView {
        void onQueryDetailSuccess(MobRecipe value);
    }

    interface Presenter extends BasePresenter<View> {
        void queryDetail(String id);
    }
}
