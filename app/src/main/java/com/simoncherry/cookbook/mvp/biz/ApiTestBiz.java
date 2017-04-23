package com.simoncherry.cookbook.mvp.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobAPIResult;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;
import com.simoncherry.cookbook.rx.CommonSubscriber;
import com.simoncherry.cookbook.util.RxUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by Simon on 2017/4/19.
 */

public class ApiTestBiz extends BaseBiz implements ApiCallback {

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

    public Disposable queryRecipe(String cid, int page, int size, final QueryRecipeCallback callback) {
        checkNotNull(callback);

        Map<String, String> params = new HashMap<>();
        params.put("key", MobAPIService.MOB_API_KEY);
        params.put("cid", cid);
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));

        return MobAPIService.getMobAPI()
                .queryRecipe(params)
                .compose(RxUtils.<MobAPIResult<MobRecipeResult>>rxSchedulerHelper())
                .compose(RxUtils.<MobRecipeResult>handleMobResult())
                .subscribeWith(new CommonSubscriber<MobRecipeResult>(callback) {
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

    public Disposable queryDetail(String id, final QueryDetailCallback callback) {
        checkNotNull(callback);

        Map<String, String> params = new HashMap<>();
        params.put("key", MobAPIService.MOB_API_KEY);
        params.put("id", id);

        return MobAPIService.getMobAPI()
                .queryDetail(params)
                .compose(RxUtils.<MobAPIResult<MobRecipe>>rxSchedulerHelper())
                .compose(RxUtils.<MobRecipe>handleMobResult())
                .subscribeWith(new CommonSubscriber<MobRecipe>(callback) {
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
