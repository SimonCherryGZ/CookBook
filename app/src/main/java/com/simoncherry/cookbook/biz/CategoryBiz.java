package com.simoncherry.cookbook.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobAPIResultFunc;
import com.simoncherry.cookbook.model.MobCategoryResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Simon on 2017/4/20.
 */

public class CategoryBiz extends BaseBiz implements ApiCallback {

    public void queryCategory(final QueryCategoryCallback callback) {
        checkNotNull(callback);

        MobAPIService.getMobAPI()
                .queryCategory(MobAPIService.MOB_API_KEY)
                .map(new MobAPIResultFunc<MobCategoryResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobCategoryResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(MobCategoryResult value) {
                        if (value != null) {
                            callback.onQueryCategorySuccess(value);
                        } else {
                            callback.onQueryFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onQueryError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
