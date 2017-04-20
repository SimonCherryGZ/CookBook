package com.simoncherry.cookbook.module;

import com.simoncherry.cookbook.biz.CategoryBiz;
import com.simoncherry.cookbook.contract.CategoryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/29.
 */
@Module
public class CategoryModule {

    private CategoryBiz mBiz;
    private CategoryContract.View mView;

    public CategoryModule(CategoryBiz mBiz, CategoryContract.View mView) {
        this.mBiz = mBiz;
        this.mView = mView;
    }

    @Provides
    CategoryBiz provideBiz() {
        return mBiz;
    }

    @Provides
    CategoryContract.View provideView() {
        return mView;
    }
}
