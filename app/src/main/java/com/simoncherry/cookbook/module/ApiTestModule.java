package com.simoncherry.cookbook.module;

import android.content.Context;

import com.simoncherry.cookbook.view.ApiTestView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/28.
 */
@Module
public class ApiTestModule {

    private Context mContext;
    private ApiTestView mView;

    public ApiTestModule(Context mContext, ApiTestView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    ApiTestView provideApiTestView() {
        return mView;
    }
}
