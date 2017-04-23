package com.simoncherry.cookbook.api;

import com.simoncherry.cookbook.model.MobAPIResult;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Simon on 2017/3/27.
 */

public interface MobAPI {

    @GET("category/query")
    Flowable<MobAPIResult<MobCategoryResult>> queryCategory(@Query("key") String key);

    @GET("menu/search")
    Flowable<MobAPIResult<MobRecipeResult>> queryRecipe(@QueryMap Map<String, String> params);

    @GET("menu/query")
    Flowable<MobAPIResult<MobRecipe>> queryDetail(@QueryMap Map<String, String> params);
}
