package com.simoncherry.cookbook.component;

import com.simoncherry.cookbook.ui.activity.ApiTestActivity;
import com.simoncherry.cookbook.module.ApiTestModule;

import dagger.Component;

/**
 * Created by Simon on 2017/3/28.
 */
@Component(modules = ApiTestModule.class)
public interface ApiTestComponent {
    void inject(ApiTestActivity apiTestActivity);
}
