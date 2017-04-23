package com.simoncherry.cookbook.di.module;

import com.simoncherry.cookbook.mvp.biz.DetailBiz;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/30.
 */
@Module
public class DetailModule {

    private DetailBiz mBiz;

    public DetailModule(DetailBiz mBiz) {
        this.mBiz = mBiz;
    }

    @Provides
    DetailBiz provideBiz() {
        return mBiz;
    }
}
