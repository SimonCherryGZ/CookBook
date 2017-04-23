package com.simoncherry.cookbook.di.module;

import com.simoncherry.cookbook.mvp.biz.CategoryBiz;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/29.
 */
@Module
public class CategoryModule {

    private CategoryBiz mBiz;

    public CategoryModule(CategoryBiz mBiz) {
        this.mBiz = mBiz;
    }

    @Provides
    CategoryBiz provideBiz() {
        return mBiz;
    }
}
