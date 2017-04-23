package com.simoncherry.cookbook.mvp.presenter;

import android.support.annotation.NonNull;

import com.simoncherry.cookbook.mvp.biz.ApiTestBiz;
import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.mvp.contract.ApiTestContract;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;

import javax.inject.Inject;

/**
 * Created by Simon on 2017/3/28.
 */

public class ApiTestPresenter extends RxPresenter<ApiTestContract.View> implements ApiTestContract.Presenter {

    private ApiTestBiz apiTestBiz;

    @Inject
    public ApiTestPresenter(@NonNull ApiTestBiz apiTestBiz) {
        this.apiTestBiz = apiTestBiz;
    }

    @Override
    public void queryCategory() {
        addSubscribe(apiTestBiz.queryCategory(new ApiCallback.QueryCategoryCallback() {
            @Override
            public void onStart() {
                mView.onShowProgressBar();
            }

            @Override
            public void onEnd() {
                mView.onHideProgressBar();
            }

            @Override
            public void onQueryCategorySuccess(MobCategoryResult value) {
                mView.onQueryCategorySuccess(value);
            }

            @Override
            public void onQueryCategoryEmpty() {
                mView.onQueryEmpty();
            }

            @Override
            public void onQueryError(String msg) {
                mView.onQueryError(msg);
            }
        }));
    }

    @Override
    public void queryRecipe(String cid, int page, int size) {
        addSubscribe(apiTestBiz.queryRecipe(cid, page, size, new ApiCallback.QueryRecipeCallback() {
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
                mView.onQueryRecipe(value);
            }

            @Override
            public void onQueryRecipeEmpty() {
                mView.onQueryEmpty();
            }

            @Override
            public void onQueryError(String msg) {
                mView.onQueryError(msg);
            }
        }));
    }

    @Override
    public void queryDetail(String id) {
        addSubscribe(apiTestBiz.queryDetail(id, new ApiCallback.QueryDetailCallback() {
            @Override
            public void onStart() {
                mView.onShowProgressBar();
            }

            @Override
            public void onEnd() {
                mView.onHideProgressBar();
            }

            @Override
            public void onQueryDetailSuccess(MobRecipe value) {
                mView.onQueryDetail(value);
            }

            @Override
            public void onQueryDetailEmpty() {
                mView.onQueryEmpty();
            }

            @Override
            public void onQueryError(String msg) {
                mView.onQueryError(msg);
            }
        }));
    }
}
