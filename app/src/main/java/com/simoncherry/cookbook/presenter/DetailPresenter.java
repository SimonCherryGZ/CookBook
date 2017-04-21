package com.simoncherry.cookbook.presenter;

import com.simoncherry.cookbook.biz.DetailBiz;
import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.contract.DetailContract;
import com.simoncherry.cookbook.model.MobRecipe;

import javax.inject.Inject;

/**
 * Created by Simon on 2017/3/30.
 */

public class DetailPresenter extends BasePresenter implements DetailContract.Presenter{

    private DetailBiz mBiz;
    private DetailContract.View mView;

    @Inject
    public DetailPresenter(DetailBiz mBiz, DetailContract.View mView) {
        this.mBiz = mBiz;
        this.mView = mView;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void queryDetail(String id) {
        mBiz.queryDetail(id, new ApiCallback.QueryDetailCallback() {
            @Override
            public void onQueryDetailSuccess(MobRecipe value) {
                mView.onQueryDetailSuccess(value);
            }

            @Override
            public void onQueryFailed() {
                mView.onQueryFailed();
            }

            @Override
            public void onQueryError(String msg) {
                mView.onQueryError(msg);
            }
        });
    }
}
