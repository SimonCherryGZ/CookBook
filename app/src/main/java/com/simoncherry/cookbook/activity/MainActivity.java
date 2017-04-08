package com.simoncherry.cookbook.activity;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.simoncherry.cookbook.MySuggestionProvider;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.component.DaggerCategoryComponent;
import com.simoncherry.cookbook.fragment.CategoryFragment;
import com.simoncherry.cookbook.fragment.CollectionFragment;
import com.simoncherry.cookbook.fragment.HistoryFragment;
import com.simoncherry.cookbook.fragment.PageFragment;
import com.simoncherry.cookbook.fragment.RecipeFragment;
import com.simoncherry.cookbook.model.MobCategoryChild1;
import com.simoncherry.cookbook.model.MobCategoryChild2;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.RealmCategory;
import com.simoncherry.cookbook.module.CategoryModule;
import com.simoncherry.cookbook.presenter.impl.CategoryPresenterImpl;
import com.simoncherry.cookbook.realm.RealmHelper;
import com.simoncherry.cookbook.view.CategoryView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity
        implements CategoryView, NavigationView.OnNavigationItemSelectedListener,
        CategoryFragment.OnFragmentInteractionListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_splash)
    ImageView ivSplash;

    private Unbinder unbinder;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private Fragment previousFragment = null;
    private PageFragment pageFragment;
    private CategoryFragment categoryFragment;
    private CollectionFragment collectionFragment;
    private HistoryFragment historyFragment;
    private RecipeFragment recipeFragment;
    @Inject
    CategoryPresenterImpl categoryPresenter;

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
            if (previousFragment instanceof CategoryFragment && currentFragment instanceof RecipeFragment){
                backToFragment(currentFragment, previousFragment);
                toolbar.setTitle(R.string.main_title_category);
            }else {
                super.onBackPressed();
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
    public void onClickCategory(String ctgId, String name) {
        recipeFragment.changeCategory(ctgId);
        switchFragment(currentFragment, recipeFragment);
        name = "分类 - " + name;
        toolbar.setTitle(name);
    }

    @Override
    public void onQueryCategorySuccess(MobCategoryResult result) {
        handleCategoryResult(result);
    }

    private void handleCategoryResult(MobCategoryResult result) {
        if (result != null) {
            List<RealmCategory> categoryList = new ArrayList<>();
            // 第1层子类
            ArrayList<MobCategoryChild1> child1 = result.getChilds();
            if (child1 != null && child1.size() > 0) {
                for (MobCategoryChild1 categoryChild1 : child1) {
                    categoryList.add(RealmHelper.convertMobCategoryToRealmCategory(categoryChild1.getCategoryInfo(), false));
                    // 第2层子类
                    ArrayList<MobCategoryChild2> child2 = categoryChild1.getChilds();
                    if (child2 != null && child2.size() > 0) {
                        for (MobCategoryChild2 categoryChild2 : child2) {
                            categoryList.add(RealmHelper.convertMobCategoryToRealmCategory(categoryChild2.getCategoryInfo(), true));
                        }
                    }
                }
            }
            saveCategoryToRealm(categoryList);
        }
    }

    private void saveCategoryToRealm(final List<RealmCategory> categoryList) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmHelper.saveCategoryToRealm(realm, categoryList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
                splashImageFadeOut();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                realm.close();
                splashImageFadeOut();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void splashImageFadeOut() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.img_fade_out);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                ivSplash.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        ivSplash.startAnimation(animation);
    }

    private void onQueryRecipeByName(String name) {
        recipeFragment.queryRecipeByName(name);
        switchFragment(currentFragment, recipeFragment);
        name = "查询 - " + name;
        toolbar.setTitle(name);
    }

    private void init() {
        initComponent();
        initView();
        initFragment();
        categoryPresenter.queryCategory();
    }

    private void initComponent() {
        DaggerCategoryComponent.builder()
                .categoryModule(new CategoryModule(getApplicationContext(), this))
                .build()
                .inject(this);
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

        previousFragment = pageFragment;
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
