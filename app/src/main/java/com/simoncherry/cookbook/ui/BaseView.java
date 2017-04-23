package com.simoncherry.cookbook.ui;

/**
 * Created by Simon on 2017/4/23.
 */

public interface BaseView {
    void onQueryEmpty();
    void onQueryError(String msg);
    void onShowProgressBar();
    void onHideProgressBar();
}
