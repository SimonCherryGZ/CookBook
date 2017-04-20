package com.simoncherry.cookbook.module;

import com.simoncherry.cookbook.biz.DetailBiz;
import com.simoncherry.cookbook.contract.DetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/30.
 */
@Module
public class DetailModule {

    private DetailBiz mBiz;
    private DetailContract.View mView;

    public DetailModule(DetailBiz mBiz, DetailContract.View mView) {
        this.mBiz = mBiz;
        this.mView = mView;
    }

    @Provides
    DetailBiz provideBiz() {
        return mBiz;
    }

    @Provides
    DetailContract.View provideView() {
        return mView;
    }
}
