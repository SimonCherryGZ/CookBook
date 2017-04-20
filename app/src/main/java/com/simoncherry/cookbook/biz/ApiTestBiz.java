package com.simoncherry.cookbook.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobAPIResultFunc;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Simon on 2017/4/19.
 */

public class ApiTestBiz extends BaseBiz implements ApiCallback {

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobRecipeResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(MobRecipeResult value) {
                        if (value != null) {
                            callback.onQueryRecipeSuccess(value);
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

    public void queryDetail(String id, final QueryDetailCallback callback) {
        checkNotNull(callback);

        Map<String, String> params = new HashMap<>();
        params.put("key", MobAPIService.MOB_API_KEY);
        params.put("id", id);

        MobAPIService.getMobAPI()
                .queryDetail(params)
                .map(new MobAPIResultFunc<MobRecipe>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobRecipe>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(MobRecipe value) {
                        if (value != null) {
                            callback.onQueryDetailSuccess(value);
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
