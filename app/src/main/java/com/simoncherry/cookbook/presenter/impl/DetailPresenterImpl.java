package com.simoncherry.cookbook.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobAPIResultFunc;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.presenter.DetailPresenter;
import com.simoncherry.cookbook.view.DetailView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Simon on 2017/3/30.
 */

public class DetailPresenterImpl implements DetailPresenter {

    private Context mContext;
    private DetailView mView;

    @Inject
    public DetailPresenterImpl(Context mContext, DetailView mView) {
        this.mContext = mContext;
        this.mView = mView;
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
                            mView.onQueryDetailSuccess(value);
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
