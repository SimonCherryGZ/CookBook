package com.simoncherry.cookbook.custom;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Simon on 2017/4/6.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.simoncherry.cookbook.custom.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
