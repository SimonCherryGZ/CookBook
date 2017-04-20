package com.simoncherry.cookbook.biz;

import com.simoncherry.cookbook.api.ApiCallback;
import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobAPIResultFunc;
import com.simoncherry.cookbook.model.MobRecipe;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
