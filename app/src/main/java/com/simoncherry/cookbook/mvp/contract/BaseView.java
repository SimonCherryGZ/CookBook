package com.simoncherry.cookbook.mvp.contract;

/**
 * Created by Simon on 2017/4/23.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
}
