package com.simoncherry.cookbook.module;

import android.content.Context;

import com.simoncherry.cookbook.view.RecipeView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 2017/3/29.
 */
@Module
public class RecipeModule {

    private Context mContext;
    private RecipeView mView;

    public RecipeModule(Context mContext, RecipeView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    RecipeView provideRecipeView() {
        return mView;
    }
}
