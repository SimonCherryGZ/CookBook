package com.simoncherry.cookbook.util;

import com.simoncherry.cookbook.api.APIException;
import com.simoncherry.cookbook.model.MobAPIResult;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Simon on 2017/4/23.
 */

public class RxUtils {

    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<MobAPIResult<T>, T> handleMobResult() {   //compose判断结果
        return new FlowableTransformer<MobAPIResult<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<MobAPIResult<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<MobAPIResult<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(MobAPIResult<T> response) {
                        if(response.getResult() != null) {
                            return createData(response.getResult());
                        } else {
                            return Flowable.error(new APIException(500, "服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
