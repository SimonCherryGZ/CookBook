package com.simoncherry.cookbook.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.simoncherry.cookbook.custom.MySuggestionProvider;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.ui.fragment.CategoryFragment;
import com.simoncherry.cookbook.ui.fragment.CollectionFragment;
import com.simoncherry.cookbook.ui.fragment.HistoryFragment;
import com.simoncherry.cookbook.ui.fragment.MainFragment;
import com.simoncherry.cookbook.ui.fragment.RecipeFragment;
import com.simoncherry.cookbook.ui.fragment.SettingFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CategoryFragment.OnFragmentInteractionListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private Fragment previousFragment = null;
    private MainFragment mainFragment;
    private CategoryFragment categoryFragment;
    private CollectionFragment collectionFragment;
    private HistoryFragment historyFragment;
    private SettingFragment settingFragment;
    private RecipeFragment recipeFragment;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initComponent() {
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (previousFragment instanceof CategoryFragment && currentFragment instanceof RecipeFragment){
                backToFragment(currentFragment, previousFragment);
                toolbar.setTitle(R.string.main_title_category);
            }else {
                if ((System.currentTimeMillis() - exitTime) > 2000){
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);//在菜单中找到对应控件的item
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            final SearchView finalSearchView = searchView;
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //Toast.makeText(getApplicationContext(), "查询:" + query, Toast.LENGTH_SHORT).show();
                    onQueryRecipeByName(query);
                    finalSearchView.setIconified(true);
                    finalSearchView.setIconified(true);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionSelect(int position) {
                    return false;
                }

                @Override
                public boolean onSuggestionClick(int position) {
                    Cursor cursor = (Cursor) finalSearchView.getSuggestionsAdapter().getItem(position);
                    String query = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
                    //Toast.makeText(getApplicationContext(), "点击:" + query, Toast.LENGTH_SHORT).show();
                    onQueryRecipeByName(query);
                    finalSearchView.setIconified(true);
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                toolbar.setTitle(R.string.main_title_home);
                switchFragment(currentFragment, mainFragment);
                break;
            case R.id.nav_category:
                toolbar.setTitle(R.string.main_title_category);
                switchFragment(currentFragment, categoryFragment);
                break;
            case R.id.nav_collection:
                toolbar.setTitle(R.string.main_title_collection);
                switchFragment(currentFragment, collectionFragment);
                break;
            case R.id.nav_history:
                toolbar.setTitle(R.string.main_title_history);
                switchFragment(currentFragment, historyFragment);
                break;
            case R.id.nav_manage:
                toolbar.setTitle(R.string.main_title_setting);
                switchFragment(currentFragment, settingFragment);
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_about:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClickCategory(String ctgId, String name) {
        recipeFragment.changeCategory(ctgId);
        switchFragment(currentFragment, recipeFragment);
        name = "分类 - " + name;
        toolbar.setTitle(name);
    }

    private void onQueryRecipeByName(String name) {
        recipeFragment.queryRecipeByName(name);
        switchFragment(currentFragment, recipeFragment);
        name = "查询 - " + name;
        toolbar.setTitle(name);
    }

    private void init() {
        initView();
        initFragment();
    }

    private void initView() {
        toolbar.setTitle(R.string.main_title_home);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = fragmentManager.findFragmentById(R.id.layout_content);
                if(fragment != null){
                    Log.e("fragment=", fragment.getClass().getSimpleName());
                }
            }
        });

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        mainFragment = MainFragment.newInstance();
        recipeFragment = RecipeFragment.newInstance("0010001010");
        categoryFragment = CategoryFragment.newInstance();
        collectionFragment = CollectionFragment.newInstance();
        historyFragment = HistoryFragment.newInstance();
        settingFragment = SettingFragment.newInstance();

        previousFragment = mainFragment;
        currentFragment = mainFragment;
        transaction
                .add(R.id.layout_content, mainFragment)
                .add(R.id.layout_content, categoryFragment)
                .add(R.id.layout_content, collectionFragment)
                .add(R.id.layout_content, historyFragment)
                .add(R.id.layout_content, settingFragment)
                .add(R.id.layout_content, recipeFragment)
                .hide(categoryFragment)
                .hide(collectionFragment)
                .hide(historyFragment)
                .hide(settingFragment)
                .hide(recipeFragment)
                .show(mainFragment)
                .commit();
    }

    private void switchFragment(Fragment from, Fragment to) {
        if (!from.equals(to)) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction
                    .hide(from)
                    .show(to)
                    .commit();
            previousFragment = from;
            currentFragment = to;
        }
    }

    private void backToFragment(Fragment from, Fragment to) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .hide(from)
                .show(to)
                .commit();
        previousFragment = null;
        currentFragment = to;
    }
}
