package com.simoncherry.cookbook.api;

/**
 * Created by Simon on 2017/4/22.
 */

public interface BaseCallback {
    void onStart();
    void onEnd();
    void onQueryError(String msg);
}
