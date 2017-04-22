package com.simoncherry.cookbook.rx;

import com.simoncherry.cookbook.api.BaseCallback;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Simon on 2017/4/22.
 */

public abstract class RxSubscriber<T> implements Observer<T> {

    private BaseCallback callback;

    protected RxSubscriber(BaseCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onError(Throwable t) {
        callback.onEnd();
        callback.onQueryError(t.getMessage());
    }

    @Override
    public void onComplete() {
        callback.onEnd();
    }
}
