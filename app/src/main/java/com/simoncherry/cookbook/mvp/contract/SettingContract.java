package com.simoncherry.cookbook.mvp.contract;

/**
 * Created by Simon on 2017/4/23.
 */

public interface SettingContract {

    interface View extends BaseView<Presenter> {
        void onChangeSaveMode(boolean isSaveMode);
        void onShowCacheSize();
        void onClearSearchHistorySuccess();
        void onClearCacheSuccess();
    }

    interface Presenter extends BasePresenter {
        void changeSaveMode(boolean isSaveMode);
        void changeHistoryLimit(int limit);
        void clearSearchHistory();
        void clearCache();
    }
}
