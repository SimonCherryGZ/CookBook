package com.simoncherry.cookbook.di.component;

import com.simoncherry.cookbook.di.module.SettingModule;
import com.simoncherry.cookbook.ui.fragment.SettingFragment;

import dagger.Component;

/**
 * Created by Simon on 2017/4/23.
 */
@Component(modules = SettingModule.class)
public interface SettingComponent {
    void inject(SettingFragment settingFragment);
}
