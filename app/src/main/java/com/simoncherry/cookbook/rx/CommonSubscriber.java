package com.simoncherry.cookbook.rx;

import com.simoncherry.cookbook.api.BaseCallback;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by Simon on 2017/4/23.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

    private BaseCallback callback;

    public CommonSubscriber(BaseCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onStart() {
        super.onStart();
        callback.onStart();
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
