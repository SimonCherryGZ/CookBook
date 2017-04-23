package com.simoncherry.cookbook.di.module;

import android.content.Context;

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

    public SettingModule(Context mContext, SPUtils mSPUtils) {
        this.mContext = mContext;
        this.mSPUtils = mSPUtils;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    SPUtils provideSPUtils() {
        return mSPUtils;
    }
}
