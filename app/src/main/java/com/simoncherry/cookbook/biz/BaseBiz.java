package com.simoncherry.cookbook.biz;

/**
 * Created by Simon on 2017/4/19.
 */

abstract class BaseBiz {

    public <T> T checkNotNull(T reference) {
        if(reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }
}
