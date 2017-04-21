package com.simoncherry.cookbook.di.component;

import com.simoncherry.cookbook.ui.activity.DetailActivity;
import com.simoncherry.cookbook.di.module.DetailModule;

import dagger.Component;

/**
 * Created by Simon on 2017/3/30.
 */
@Component(modules = DetailModule.class)
public interface DetailComponent {
    void inject(DetailActivity detailActivity);
}
