package com.simoncherry.cookbook.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.simoncherry.cookbook.api.MobAPIService;
import com.simoncherry.cookbook.model.MobAPIResultFunc;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.presenter.CategoryPresenter;
import com.simoncherry.cookbook.view.CategoryView;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Simon on 2017/3/29.
 */

public class CategoryPresenterImpl implements CategoryPresenter {

    private Context mContext;
    private CategoryView mView;

    @Inject
    public CategoryPresenterImpl(Context mContext, CategoryView mView) {
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
}
