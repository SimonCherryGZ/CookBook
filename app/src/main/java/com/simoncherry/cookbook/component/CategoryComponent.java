package com.simoncherry.cookbook.component;

import com.simoncherry.cookbook.ui.activity.SplashActivity;
import com.simoncherry.cookbook.module.CategoryModule;

import dagger.Component;

/**
 * Created by Simon on 2017/3/29.
 */
@Component(modules = CategoryModule.class)
public interface CategoryComponent {
    void inject(SplashActivity splashActivity);
}
