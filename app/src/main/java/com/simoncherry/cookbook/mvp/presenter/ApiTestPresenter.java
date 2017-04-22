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

public class ApiTestPresenter extends BasePresenter implements ApiTestContract.Presenter {

    private ApiTestBiz apiTestBiz;
    private ApiTestContract.View apiTestView;

    @Inject
    public ApiTestPresenter(@NonNull ApiTestBiz apiTestBiz, @NonNull ApiTestContract.View apiTestView) {
        this.apiTestBiz = apiTestBiz;
        this.apiTestView = apiTestView;

        apiTestView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void queryCategory() {
        apiTestBiz.queryCategory(new ApiCallback.QueryCategoryCallback() {
            @Override
            public void onStart() {
                apiTestView.onShowProgressBar();
            }

            @Override
            public void onEnd() {
                apiTestView.onHideProgressBar();
            }

            @Override
            public void onQueryCategorySuccess(MobCategoryResult value) {
                apiTestView.onQueryCategorySuccess(value);
            }

            @Override
            public void onQueryCategoryEmpty() {
                apiTestView.onQueryEmpty();
            }

            @Override
            public void onQueryError(String msg) {
                apiTestView.onQueryError(msg);
            }
        });
    }

    @Override
    public void queryRecipe(String cid, int page, int size) {
        apiTestBiz.queryRecipe(cid, page, size, new ApiCallback.QueryRecipeCallback() {
            @Override
            public void onStart() {
                apiTestView.onShowProgressBar();
            }

            @Override
            public void onEnd() {
                apiTestView.onHideProgressBar();
            }

            @Override
            public void onQueryRecipeSuccess(MobRecipeResult value) {
                apiTestView.onQueryRecipe(value);
            }

            @Override
            public void onQueryRecipeEmpty() {
                apiTestView.onQueryEmpty();
            }

            @Override
            public void onQueryError(String msg) {
                apiTestView.onQueryError(msg);
            }
        });
    }

    @Override
    public void queryDetail(String id) {
        apiTestBiz.queryDetail(id, new ApiCallback.QueryDetailCallback() {
            @Override
            public void onStart() {
                apiTestView.onShowProgressBar();
            }

            @Override
            public void onEnd() {
                apiTestView.onHideProgressBar();
            }

            @Override
            public void onQueryDetailSuccess(MobRecipe value) {
                apiTestView.onQueryDetail(value);
            }

            @Override
            public void onQueryDetailEmpty() {
                apiTestView.onQueryEmpty();
            }

            @Override
            public void onQueryError(String msg) {
                apiTestView.onQueryError(msg);
            }
        });
    }
}
