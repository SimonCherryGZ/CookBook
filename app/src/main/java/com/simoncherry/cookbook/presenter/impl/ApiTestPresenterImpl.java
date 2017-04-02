package com.simoncherry.cookbook.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobAPIResultFunc;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;
import com.simoncherry.cookbook.presenter.ApiTestPresenter;
import com.simoncherry.cookbook.view.ApiTestView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Simon on 2017/3/28.
 */

public class ApiTestPresenterImpl implements ApiTestPresenter {

    private Context mContext;
    private ApiTestView mView;

    @Inject
    public ApiTestPresenterImpl(Context mContext, ApiTestView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void queryCategory() {
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
                            mView.onQueryCategorySuccess(value);
                        } else {
                            Toast.makeText(mContext, "value is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void queryRecipe(String cid, int page, int size) {
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
                            mView.onQueryRecipe(value);
                        } else {
                            Toast.makeText(mContext, "value is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void queryDetail(String id) {
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
                            mView.onQueryDetail(value);
                        } else {
                            Toast.makeText(mContext, "value is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
