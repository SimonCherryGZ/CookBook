package com.simoncherry.cookbook.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.fragment.CategoryFragment;
import com.simoncherry.cookbook.fragment.CollectionFragment;
import com.simoncherry.cookbook.fragment.HistoryFragment;
import com.simoncherry.cookbook.fragment.PageFragment;
import com.simoncherry.cookbook.fragment.RecipeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RecipeFragment.OnFragmentInteractionListener,
        CategoryFragment.OnFragmentInteractionListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private Fragment previousFragment = null;
    private PageFragment pageFragment;
    private CategoryFragment categoryFragment;
    private CollectionFragment collectionFragment;
    private HistoryFragment historyFragment;
    private RecipeFragment recipeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            if (previousFragment != null){
                backToFragment(currentFragment, previousFragment);
                if (currentFragment instanceof CategoryFragment) {
                    toolbar.setTitle(R.string.main_title_category);
                }
            }else {
                super.onBackPressed();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                toolbar.setTitle(R.string.main_title_home);
                switchFragment(currentFragment, pageFragment);
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
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onClickCategory(String ctgId, String name) {
        recipeFragment.changeCategory(ctgId);
        switchFragment(currentFragment, recipeFragment);
        name = "分类 - " + name;
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

        pageFragment = PageFragment.newInstance();
        recipeFragment = RecipeFragment.newInstance("0010001010");
        categoryFragment = CategoryFragment.newInstance();
        collectionFragment = CollectionFragment.newInstance();
        historyFragment = HistoryFragment.newInstance();

        currentFragment = pageFragment;
        transaction
                .add(R.id.layout_content, pageFragment)
                .add(R.id.layout_content, categoryFragment)
                .add(R.id.layout_content, collectionFragment)
                .add(R.id.layout_content, historyFragment)
                .add(R.id.layout_content, recipeFragment)
                .hide(categoryFragment)
                .hide(collectionFragment)
                .hide(historyFragment)
                .hide(recipeFragment)
                .show(pageFragment)
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
