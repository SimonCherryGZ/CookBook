package com.simoncherry.cookbook.rx;

import com.simoncherry.cookbook.model.MobAPIResult;

import io.reactivex.functions.Function;

/**
 * Created by Simon on 2017/3/27.
 */

public class MobAPIResultFunc<T> implements Function<MobAPIResult<T>, T> {

//    @Override
//    public T call(MobAPIResult<T> tMobAPIResult) {
//        if (tMobAPIResult.getRetCode() != 0) {
//            throw new APIException(tMobAPIResult.getRetCode(), tMobAPIResult.getMsg());
//        }
//        return tMobAPIResult.getResult();
//    }

    @Override
    public T apply(MobAPIResult<T> tMobAPIResult) throws Exception {
        return tMobAPIResult.getResult();
    }
}
