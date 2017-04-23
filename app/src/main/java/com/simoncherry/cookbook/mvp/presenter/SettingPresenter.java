package com.simoncherry.cookbook.mvp.presenter;

import android.content.Context;
import android.provider.SearchRecentSuggestions;

import com.simoncherry.cookbook.custom.MySuggestionProvider;
import com.simoncherry.cookbook.model.Constant;
import com.simoncherry.cookbook.mvp.contract.SettingContract;
import com.simoncherry.cookbook.realm.RealmHelper;
import com.simoncherry.cookbook.util.DataCleanManager;
import com.simoncherry.cookbook.util.SPUtils;

import javax.inject.Inject;

/**
 * Created by Simon on 2017/4/23.
 */

public class SettingPresenter extends RxPresenter<SettingContract.View> implements SettingContract.Presenter {

    private Context mContext;
    private SPUtils mSpUtils;

    @Inject
    public SettingPresenter(Context mContext, SPUtils spUtils) {
        this.mContext = mContext;
        this.mSpUtils = spUtils;
    }

    @Override
    public void changeSaveMode(boolean isSaveMode) {
        mSpUtils.put(Constant.SP_SAVE_MODE, isSaveMode);
        mView.onChangeSaveMode(isSaveMode);
    }

    @Override
    public void changeHistoryLimit(int limit) {
        mSpUtils.put(Constant.SP_HISTORY_LIMIT, limit);
        RealmHelper.deleteMultiHistoryAsync(limit);
    }

    @Override
    public void clearSearchHistory() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(mContext,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.clearHistory();
        mView.onClearSearchHistorySuccess();
    }

    @Override
    public void clearCache() {
        DataCleanManager.cleanInternalCache(mContext);
        mView.onClearCacheSuccess();
        mView.onShowCacheSize();
    }
}
