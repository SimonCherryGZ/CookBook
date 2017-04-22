package com.simoncherry.cookbook.mvp.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.rx.MobAPIResultFunc;
import com.simoncherry.cookbook.rx.RxSchedulersHelper;
import com.simoncherry.cookbook.rx.RxSubscriber;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Simon on 2017/4/20.
 */

public class DetailBiz extends BaseBiz implements ApiCallback {

    public void queryDetail(String id, final QueryDetailCallback callback) {
        checkNotNull(callback);

        Map<String, String> params = new HashMap<>();
        params.put("key", MobAPIService.MOB_API_KEY);
        params.put("id", id);

        MobAPIService.getMobAPI()
                .queryDetail(params)
                .map(new MobAPIResultFunc<MobRecipe>())
                .compose(RxSchedulersHelper.<MobRecipe>io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        callback.onStart();
                    }
                })
                .subscribe(new RxSubscriber<MobRecipe>(callback) {
                    @Override
                    public void onNext(MobRecipe value) {
                        if (value != null) {
                            callback.onQueryDetailSuccess(value);
                        } else {
                            callback.onQueryDetailEmpty();
                        }
                    }
                });
    }
}
