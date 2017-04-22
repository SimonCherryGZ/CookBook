package com.simoncherry.cookbook.mvp.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobRecipeResult;
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

public class RecipeBiz extends BaseBiz implements ApiCallback {

    public void queryRecipe(String cid, int page, int size, final QueryRecipeCallback callback) {
        checkNotNull(callback);

        Map<String, String> params = new HashMap<>();
        params.put("key", MobAPIService.MOB_API_KEY);
        params.put("cid", cid);
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));

        MobAPIService.getMobAPI()
                .queryRecipe(params)
                .map(new MobAPIResultFunc<MobRecipeResult>())
                .compose(RxSchedulersHelper.<MobRecipeResult>io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        callback.onStart();
                    }
                })
                .subscribe(new RxSubscriber<MobRecipeResult>(callback) {
                    @Override
                    public void onNext(MobRecipeResult value) {
                        if (value != null) {
                            callback.onQueryRecipeSuccess(value);
                        } else {
                            callback.onQueryRecipeEmpty();
                        }
                    }
                });
    }

    public void queryRecipeByField(String field, String value, int page, int size, final QueryRecipeCallback callback) {
        checkNotNull(callback);

        Map<String, String> params = new HashMap<>();
        params.put("key", MobAPIService.MOB_API_KEY);
        params.put(field, value);
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));

        MobAPIService.getMobAPI()
                .queryRecipe(params)
                .map(new MobAPIResultFunc<MobRecipeResult>())
                .compose(RxSchedulersHelper.<MobRecipeResult>io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        callback.onStart();
                    }
                })
                .subscribe(new RxSubscriber<MobRecipeResult>(callback) {
                    @Override
                    public void onNext(MobRecipeResult value) {
                        if (value != null) {
                            callback.onQueryRecipeSuccess(value);
                        } else {
                            callback.onQueryRecipeEmpty();
                        }
                    }
                });
    }
}
