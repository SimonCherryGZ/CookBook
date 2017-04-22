package com.simoncherry.cookbook.mvp.presenter;

import com.simoncherry.cookbook.mvp.biz.RecipeBiz;
import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.mvp.contract.RecipeContract;
import com.simoncherry.cookbook.model.MobRecipeResult;

import javax.inject.Inject;

/**
 * Created by Simon on 2017/3/29.
 */

public class RecipePresenter extends BasePresenter implements RecipeContract.Presenter{

    private RecipeBiz mBiz;
    private RecipeContract.View mView;

    @Inject
    public RecipePresenter(RecipeBiz mBiz, RecipeContract.View mView) {
        this.mBiz = mBiz;
        this.mView = mView;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void queryRecipe(String cid, int page, int size) {
        mBiz.queryRecipe(cid, page, size, new ApiCallback.QueryRecipeCallback() {
            @Override
            public void onStart() {
                mView.onShowProgressBar();
            }

            @Override
            public void onEnd() {
                mView.onHideProgressBar();
            }

            @Override
            public void onQueryRecipeSuccess(MobRecipeResult value) {
                mView.onQueryRecipeSuccess(value);
            }

            @Override
            public void onQueryRecipeEmpty() {
                mView.onQueryEmpty();
            }

            @Override
            public void onQueryError(String msg) {
                mView.onQueryError(msg);
            }
        });
    }

    @Override
    public void queryRecipeByField(String field, String value, int page, int size) {
        mBiz.queryRecipeByField(field, value, page, size, new ApiCallback.QueryRecipeCallback() {
            @Override
            public void onStart() {
                mView.onShowProgressBar();
            }

            @Override
            public void onEnd() {
                mView.onHideProgressBar();
            }

            @Override
            public void onQueryRecipeSuccess(MobRecipeResult value) {
                mView.onQueryRecipeSuccess(value);
            }

            @Override
            public void onQueryRecipeEmpty() {
                mView.onQueryEmpty();
            }

            @Override
            public void onQueryError(String msg) {
                mView.onQueryError(msg);
            }
        });
    }
}
