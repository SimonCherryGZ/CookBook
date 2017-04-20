package com.simoncherry.cookbook.presenter;

import com.simoncherry.cookbook.biz.CategoryBiz;
import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.contract.CategoryContract;
import com.simoncherry.cookbook.model.MobCategoryResult;

import javax.inject.Inject;

/**
 * Created by Simon on 2017/3/29.
 */

public class CategoryPresenter implements CategoryContract.Presenter{

    private CategoryBiz mBiz;
    private CategoryContract.View mView;

    @Inject
    public CategoryPresenter(CategoryBiz mBiz, CategoryContract.View mView) {
        this.mBiz = mBiz;
        this.mView = mView;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void queryCategory() {
        mBiz.queryCategory(new ApiCallback.QueryCategoryCallback() {
            @Override
            public void onQueryCategorySuccess(MobCategoryResult value) {
                mView.onQueryCategorySuccess(value);
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
