package com.simoncherry.cookbook.di.module;

import com.simoncherry.cookbook.mvp.biz.RecipeBiz;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/29.
 */
@Module
public class RecipeModule {

    private RecipeBiz mBiz;

    public RecipeModule(RecipeBiz mBiz) {
        this.mBiz = mBiz;
    }

    @Provides
    RecipeBiz provideBiz() {
        return mBiz;
    }
}
