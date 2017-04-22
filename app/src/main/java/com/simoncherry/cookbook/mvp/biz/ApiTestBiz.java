package com.simoncherry.cookbook.mvp.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;
import com.simoncherry.cookbook.rx.MobAPIResultFunc;
import com.simoncherry.cookbook.rx.RxSchedulersHelper;
import com.simoncherry.cookbook.rx.RxSubscriber;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Simon on 2017/4/19.
 */

public class ApiTestBiz extends BaseBiz implements ApiCallback {

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
