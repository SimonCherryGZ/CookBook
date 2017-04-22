package com.simoncherry.cookbook.mvp.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.rx.MobAPIResultFunc;
import com.simoncherry.cookbook.rx.RxSchedulersHelper;
import com.simoncherry.cookbook.rx.RxSubscriber;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Simon on 2017/4/20.
 */

public class CategoryBiz extends BaseBiz implements ApiCallback {

    public void queryCategory(final QueryCategoryCallback callback) {
        checkNotNull(callback);

        MobAPIService.getMobAPI()
                .queryCategory(MobAPIService.MOB_API_KEY)
                .map(new MobAPIResultFunc<MobCategoryResult>())
                .compose(RxSchedulersHelper.<MobCategoryResult>io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        callback.onStart();
                    }
                })
                .subscribe(new RxSubscriber<MobCategoryResult>(callback) {
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
