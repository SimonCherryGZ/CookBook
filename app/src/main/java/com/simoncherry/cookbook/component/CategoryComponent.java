package com.simoncherry.cookbook.component;

import com.simoncherry.cookbook.activity.CategoryActivity;
import com.simoncherry.cookbook.activity.MainActivity;
import com.simoncherry.cookbook.fragment.CategoryFragment;
import com.simoncherry.cookbook.module.CategoryModule;

import dagger.Component;

/**
 * Created by Simon on 2017/3/29.
 */
@Component(modules = CategoryModule.class)
public interface CategoryComponent {
    void inject(CategoryActivity categoryActivity);
    void inject(CategoryFragment categoryFragment);
    void inject(MainActivity mainActivity);
}
