package com.simoncherry.cookbook.component;

import com.simoncherry.cookbook.activity.DetailActivity;
import com.simoncherry.cookbook.module.DetailModule;

import dagger.Component;

/**
 * Created by Simon on 2017/3/30.
 */
@Component(modules = DetailModule.class)
public interface DetailComponent {
    void inject(DetailActivity detailActivity);
}
