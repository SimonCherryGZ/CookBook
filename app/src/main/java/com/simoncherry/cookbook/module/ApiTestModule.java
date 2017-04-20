package com.simoncherry.cookbook.module;

import com.simoncherry.cookbook.biz.ApiTestBiz;
import com.simoncherry.cookbook.contract.ApiTestContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/28.
 */
@Module
public class ApiTestModule {

    private ApiTestBiz mBiz;
    private ApiTestContract.View mView;

    public ApiTestModule(ApiTestBiz mBiz, ApiTestContract.View mView) {
        this.mBiz = mBiz;
        this.mView = mView;
    }

    @Provides
    ApiTestBiz provideBiz() {
        return mBiz;
    }

    @Provides
    ApiTestContract.View provideView() {
        return mView;
    }
}
