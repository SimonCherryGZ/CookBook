package com.simoncherry.cookbook.mvp.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobAPIResult;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.rx.CommonSubscriber;
import com.simoncherry.cookbook.util.RxUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by Simon on 2017/4/20.
 */

public class CategoryBiz extends BaseBiz implements ApiCallback {

    public Disposable queryCategory(final QueryCategoryCallback callback) {
        checkNotNull(callback);

        return MobAPIService.getMobAPI()
                .queryCategory(MobAPIService.MOB_API_KEY)
                .compose(RxUtils.<MobAPIResult<MobCategoryResult>>rxSchedulerHelper())
                .compose(RxUtils.<MobCategoryResult>handleMobResult())
                .subscribeWith(new CommonSubscriber<MobCategoryResult>(callback) {
                    @Override
                    public void onNext(MobCategoryResult value) {
                        if (value != null) {
                            callback.onQueryCategorySuccess(value);
                        } else {
                            callback.onQueryCategoryEmpty();
                        }
                    }
                });
    }
}
