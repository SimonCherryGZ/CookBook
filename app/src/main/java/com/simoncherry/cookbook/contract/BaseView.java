package com.simoncherry.cookbook.contract;

/**
 * Created by Simon on 2017/4/19.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void onQueryFailed();
    void onQueryError(String msg);
}
