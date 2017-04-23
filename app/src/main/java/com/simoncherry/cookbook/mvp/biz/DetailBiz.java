package com.simoncherry.cookbook.mvp.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobAPIResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.rx.CommonSubscriber;
import com.simoncherry.cookbook.util.RxUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by Simon on 2017/4/20.
 */

public class DetailBiz extends BaseBiz implements ApiCallback {

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
