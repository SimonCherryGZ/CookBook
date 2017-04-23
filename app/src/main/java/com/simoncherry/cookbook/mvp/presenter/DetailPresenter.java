package com.simoncherry.cookbook.mvp.presenter;

import com.simoncherry.cookbook.mvp.biz.DetailBiz;
import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.mvp.contract.DetailContract;
import com.simoncherry.cookbook.model.MobRecipe;

import javax.inject.Inject;

/**
 * Created by Simon on 2017/3/30.
 */

public class DetailPresenter extends RxPresenter<DetailContract.View> implements DetailContract.Presenter{

    private DetailBiz mBiz;

    @Inject
    public DetailPresenter(DetailBiz mBiz) {
        this.mBiz = mBiz;
    }

    @Override
    public void queryDetail(String id) {
        addSubscribe(mBiz.queryDetail(id, new ApiCallback.QueryDetailCallback() {
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
                mView.onQueryDetailSuccess(value);
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
