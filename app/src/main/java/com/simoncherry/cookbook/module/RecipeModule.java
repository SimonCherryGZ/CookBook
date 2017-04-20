package com.simoncherry.cookbook.module;

import com.simoncherry.cookbook.biz.RecipeBiz;
import com.simoncherry.cookbook.contract.RecipeContract;

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
