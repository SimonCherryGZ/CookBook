package com.simoncherry.cookbook.module;

import android.content.Context;

import com.simoncherry.cookbook.view.CategoryView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/29.
 */
@Module
public class CategoryModule {

    private Context mContext;
    private CategoryView mView;

    public CategoryModule(Context mContext, CategoryView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    CategoryView proviceCategoryView() {
        return mView;
    }
}
