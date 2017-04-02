package com.simoncherry.cookbook.module;

import android.content.Context;

import com.simoncherry.cookbook.view.DetailView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/30.
 */
@Module
public class DetailModule {

    private Context mContext;
    private DetailView mView;

    public DetailModule(Context mContext, DetailView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    DetailView provideDetailView() {
        return mView;
    }
}
