package com.simoncherry.cookbook.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Simon on 2017/4/2.
 */

public abstract class BaseSwipeBackActivity extends SwipeBackActivity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }
}
