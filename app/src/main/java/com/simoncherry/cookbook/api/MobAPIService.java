package com.simoncherry.cookbook.api;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Simon on 2017/3/27.
 */

public class MobAPIService {

    public final static String MOB_API_KEY = "1c7ba126d698a";
    public final static String MOB_API_SERCET = "e3c74a50deb7cd4503f3d27743ead9e8";
    public final static String MOB_API_BASE_URL = "http://apicloud.mob.com/v1/cook/";

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static MobAPI mobAPI;

    public static MobAPI getMobAPI() {
        if (mobAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(MOB_API_BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mobAPI = retrofit.create(MobAPI.class);
        }
        return mobAPI;
    }
}
