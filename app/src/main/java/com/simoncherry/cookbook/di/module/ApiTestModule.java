package com.simoncherry.cookbook.di.module;

import com.simoncherry.cookbook.mvp.biz.ApiTestBiz;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/28.
 */
@Module
public class ApiTestModule {

    private ApiTestBiz mBiz;

    public ApiTestModule(ApiTestBiz mBiz) {
        this.mBiz = mBiz;
    }

    @Provides
    ApiTestBiz provideBiz() {
        return mBiz;
    }
}
