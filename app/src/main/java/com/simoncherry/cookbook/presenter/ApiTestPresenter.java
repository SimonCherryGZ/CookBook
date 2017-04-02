package com.simoncherry.cookbook.presenter;

/**
 * Created by Simon on 2017/3/28.
 */

public interface ApiTestPresenter {

    void queryCategory();

    void queryRecipe(String cid, int page, int size);

    void queryDetail(String id);
}
