package com.simoncherry.cookbook.di.module;

import android.content.Context;

import com.simoncherry.cookbook.mvp.contract.SettingContract;
import com.simoncherry.cookbook.util.SPUtils;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/4/23.
 */
@Module
public class SettingModule {
    private Context mContext;
    private SPUtils mSPUtils;
    private SettingContract.View mView;

    public SettingModule(Context mContext, SPUtils mSPUtils, SettingContract.View mView) {
        this.mContext = mContext;
        this.mSPUtils = mSPUtils;
        this.mView = mView;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    SPUtils provideSPUtils() {
        return mSPUtils;
    }

    @Provides
    SettingContract.View provideView() {
        return mView;
    }
}
