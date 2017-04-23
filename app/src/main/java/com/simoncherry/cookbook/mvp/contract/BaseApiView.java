package com.simoncherry.cookbook.mvp.contract;

/**
 * Created by Simon on 2017/4/19.
 */

public interface BaseApiView<T> extends BaseView<T>{
    void onQueryEmpty();
    void onQueryError(String msg);
    void onShowProgressBar();
    void onHideProgressBar();
}
