package com.simoncherry.cookbook.mvp.contract;

/**
 * Created by Simon on 2017/4/19.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void onQueryEmpty();
    void onQueryError(String msg);
    void onShowProgressBar();
    void onHideProgressBar();
}
