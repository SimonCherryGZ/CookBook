package com.simoncherry.cookbook.mvp.contract;

import com.simoncherry.cookbook.ui.BaseView;
import com.simoncherry.cookbook.mvp.presenter.BasePresenter;

/**
 * Created by Simon on 2017/4/23.
 */

public interface SettingContract {

    interface View extends BaseView {
        void onChangeSaveMode(boolean isSaveMode);
        void onShowCacheSize();
        void onClearSearchHistorySuccess();
        void onClearCacheSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void changeSaveMode(boolean isSaveMode);
        void changeHistoryLimit(int limit);
        void clearSearchHistory();
        void clearCache();
    }
}
