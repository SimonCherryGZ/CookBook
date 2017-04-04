package com.simoncherry.cookbook.component;

import com.simoncherry.cookbook.activity.RecipeActivity;
import com.simoncherry.cookbook.fragment.RecipeFragment;
import com.simoncherry.cookbook.module.RecipeModule;

import dagger.Component;

/**
 * Created by Simon on 2017/3/29.
 */
@Component(modules = RecipeModule.class)
public interface RecipeComponent {
    void inject(RecipeActivity recipeActivity);
    void inject(RecipeFragment recipeFragment);
}
