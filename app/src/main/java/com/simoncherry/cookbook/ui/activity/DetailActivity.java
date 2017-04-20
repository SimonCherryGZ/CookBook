package com.simoncherry.cookbook.ui.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.biz.DetailBiz;
import com.simoncherry.cookbook.component.DaggerDetailComponent;
import com.simoncherry.cookbook.contract.DetailContract;
import com.simoncherry.cookbook.databinding.ActivityDetailBinding;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeDetail;
import com.simoncherry.cookbook.model.MobRecipeMethod;
import com.simoncherry.cookbook.model.RealmCollection;
import com.simoncherry.cookbook.model.RealmHistory;
import com.simoncherry.cookbook.module.DetailModule;
import com.simoncherry.cookbook.presenter.DetailPresenter;
import com.simoncherry.cookbook.realm.RealmHelper;
import com.simoncherry.cookbook.ui.adapter.MethodAdapter;
import com.simoncherry.cookbook.util.ImageLoaderUtils;
import com.simoncherry.cookbook.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class DetailActivity extends BaseSwipeBackActivity implements DetailContract.View {

    @BindView(R.id.layout_coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.layout_app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.layout_tool_bar)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fabCollect;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.iv_shadow)
    ImageView ivShadow;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    @BindView(R.id.rv_method)
    RecyclerView rvMethod;


    public final static String KEY_RECIPE_ID = "recipeId";
    public final static String KEY_THUMBNAIL = "thumbnail";

    private Context mContext;
    private Unbinder unbinder;
    private ActivityDetailBinding binding;
    @Inject
    DetailPresenter detailPresenter;
    private ExitActivityTransition exitTransition;

    private MethodAdapter mAdapter;
    private List<MobRecipeMethod> mData;

    private Realm realm;
    private RealmResults<RealmCollection> realmResults;

    private MobRecipe mobRecipe;
    private String recipeId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        unbinder = ButterKnife.bind(this);
        mContext = DetailActivity.this;

        startEnterAnimation(savedInstanceState);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmResults.removeAllChangeListeners();
        realm.close();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        detailPresenter = (DetailPresenter) presenter;
    }

    @Override
    public void onBackPressed() {
        startExitAnimation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideViewBeforeAnimation() {
        coordinatorLayout.setClipChildren(false);
        appBarLayout.setClipChildren(false);
        toolbarLayout.setClipChildren(false);
        ivShadow.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        fabCollect.setVisibility(View.INVISIBLE);
    }

    private void showViewAfterAnimation() {
        coordinatorLayout.setClipChildren(true);
        appBarLayout.setClipChildren(true);
        toolbarLayout.setClipChildren(true);
        ivShadow.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        fabCollect.setVisibility(View.VISIBLE);
    }

    private void startEnterAnimation(Bundle savedInstanceState) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                hideViewBeforeAnimation();
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                showViewAfterAnimation();
                scrollView.scrollTo(0, 0);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };
        exitTransition = ActivityTransition.with(getIntent())
                .to(ivImg)
                .duration(300)
                .interpolator(new DecelerateInterpolator())
                .enterListener(animatorListener)
                .start(savedInstanceState);
    }

    private void startExitAnimation() {
        appBarLayout.setExpanded(true, false);
        scrollView.scrollTo(0, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideViewBeforeAnimation();
                exitTransition.interpolator(new AccelerateInterpolator()).exit(DetailActivity.this);
            }
        }, 200);
    }

    private void init() {
        initComponent();
        initView();
        initRecyclerView();
        initData();
        initRealm();
    }

    private void initComponent() {
        DaggerDetailComponent.builder()
                .detailModule(new DetailModule(new DetailBiz(), this))
                .build()
                .inject(this);
    }

    private void initView() {
        StatusBarUtils.transparencyBar(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        fabCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "click fabCollect", Toast.LENGTH_SHORT).show();
                handleCollection();
            }
        });
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new MethodAdapter(mContext, mData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rvMethod.setLayoutManager(linearLayoutManager);
        rvMethod.setAdapter(mAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        String thumbnail = intent.getStringExtra(KEY_THUMBNAIL);
        if (thumbnail != null) {
            ImageLoaderUtils.getInstance()
                    .loadImage(thumbnail, R.drawable.default_img, R.drawable.default_img, false, ivImg);
        }

        recipeId = intent.getStringExtra(KEY_RECIPE_ID);
        if (recipeId != null && !TextUtils.isEmpty(recipeId)) {
            detailPresenter.queryDetail(recipeId);
        } else {
            recipeId = "";
            Toast.makeText(mContext, "没有收到recipeId", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();
        realmResults = realm.where(RealmCollection.class)
                .equalTo("menuId", recipeId)
                .findAllAsync();
        realmResults.addChangeListener(new RealmChangeListener<RealmResults<RealmCollection>>() {
            @Override
            public void onChange(RealmResults<RealmCollection> element) {
                if (element.size() > 0) {
                    fabCollect.setImageResource(R.drawable.ic_fab_like_p);
                } else {
                    fabCollect.setImageResource(R.drawable.ic_fab_like_n);
                }
            }
        });
    }

    private void handleRecipeResult(MobRecipe value) {
        mobRecipe = value;
        if (value != null) {
            binding.setRecipe(value);

            String title = value.getName();
            if (title != null) {
                toolbarLayout.setTitle(title);
            }

            MobRecipeDetail recipeDetail = value.getRecipeDetail();
            if (recipeDetail != null) {
                String methods = recipeDetail.getRecipeMethods();
                if (methods != null && !TextUtils.isEmpty(methods)) {
                    List<MobRecipeMethod> methodList = new Gson().fromJson(methods, new TypeToken<List<MobRecipeMethod>>(){}.getType());
                    if (methodList != null && methodList.size() > 0) {
                        mData.clear();
                        mData.addAll(methodList);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
        saveHistoryToRealm();
    }

    private void saveHistoryToRealm() {
        if (mobRecipe != null && mobRecipe.getRecipeDetail() != null) {
            if (RealmHelper.retrieveHistoryByMenuId(realm, recipeId).size() == 0) {  // 如果没有该条历史
                if (RealmHelper.retrieveHistory(realm).size() > 5) {  // 如果历史数量大于5
                    RealmHelper.deleteFirstHistory(realm);
                }

                RealmHistory realmHistory = new RealmHistory();
                realmHistory.setCtgTitles(mobRecipe.getCtgTitles());
                realmHistory.setMenuId(mobRecipe.getMenuId());
                realmHistory.setName(mobRecipe.getName());
                realmHistory.setSummary(mobRecipe.getRecipeDetail().getSumary());
                realmHistory.setIngredients(mobRecipe.getRecipeDetail().getIngredients());
                realmHistory.setThumbnail(mobRecipe.getThumbnail());
                realmHistory.setCreateTime(new Date());
                RealmHelper.createHistory(realm, realmHistory);
            }
        }
    }

    private void handleCollection() {
        if (realmResults.size() > 0) {
            //RealmHelper.deleteCollectionByMenuId(realm, recipeId);
            RealmHelper.deleteCollectionByResult(realm, realmResults);
        } else {
            if (mobRecipe != null && mobRecipe.getRecipeDetail() != null) {
                RealmCollection realmCollection = new RealmCollection();
                realmCollection.setCtgTitles(mobRecipe.getCtgTitles());
                realmCollection.setMenuId(mobRecipe.getMenuId());
                realmCollection.setName(mobRecipe.getName());
                realmCollection.setSummary(mobRecipe.getRecipeDetail().getSumary());
                realmCollection.setIngredients(mobRecipe.getRecipeDetail().getIngredients());
                realmCollection.setThumbnail(mobRecipe.getThumbnail());
                RealmHelper.createCollection(realm, realmCollection);
            }
        }
    }

    @Override
    public void onQueryDetailSuccess(MobRecipe value) {
        handleRecipeResult(value);
    }

    @Override
    public void onQueryFailed() {
        Toast.makeText(this, "value is null", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQueryError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
