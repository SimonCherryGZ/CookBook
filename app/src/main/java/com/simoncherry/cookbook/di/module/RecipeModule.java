package com.simoncherry.cookbook.di.module;

import com.simoncherry.cookbook.mvp.biz.RecipeBiz;
import com.simoncherry.cookbook.mvp.contract.RecipeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/29.
 */
@Module
public class RecipeModule {

    private RecipeBiz mBiz;
    private RecipeContract.View mView;

    public RecipeModule(RecipeBiz mBiz, RecipeContract.View mView) {
        this.mBiz = mBiz;
        this.mView = mView;
    }

    @Provides
    RecipeBiz provideBiz() {
        return mBiz;
    }

    @Provides
    RecipeContract.View provideView() {
        return mView;
    }
}
