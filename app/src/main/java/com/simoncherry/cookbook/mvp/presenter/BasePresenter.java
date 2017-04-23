package com.simoncherry.cookbook.mvp.presenter;

import com.simoncherry.cookbook.ui.BaseView;

/**
 * Created by Simon on 2017/4/21.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}
