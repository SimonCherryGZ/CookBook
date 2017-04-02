package com.simoncherry.cookbook.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.simoncherry.cookbook.adapter.MethodAdapter;
import com.simoncherry.cookbook.component.DaggerDetailComponent;
import com.simoncherry.cookbook.databinding.ActivityDetailBinding;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeDetail;
import com.simoncherry.cookbook.model.MobRecipeMethod;
import com.simoncherry.cookbook.module.DetailModule;
import com.simoncherry.cookbook.presenter.impl.DetailPresenterImpl;
import com.simoncherry.cookbook.util.ImageLoaderUtils;
import com.simoncherry.cookbook.util.StatusBarUtils;
import com.simoncherry.cookbook.view.DetailView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @BindView(R.id.layout_coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.layout_app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.layout_tool_bar)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
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
    DetailPresenterImpl detailPresenter;
    private ExitActivityTransition exitTransition;

    private MethodAdapter mAdapter;
    private List<MobRecipeMethod> mData;


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
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        startExitAnimation();
    }

    private void hideViewBeforeAnimation() {
        coordinatorLayout.setClipChildren(false);
        appBarLayout.setClipChildren(false);
        toolbarLayout.setClipChildren(false);
        ivShadow.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
    }

    private void showViewAfterAnimation() {
        coordinatorLayout.setClipChildren(true);
        appBarLayout.setClipChildren(true);
        toolbarLayout.setClipChildren(true);
        ivShadow.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
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
    }

    private void initComponent() {
        DaggerDetailComponent.builder()
                .detailModule(new DetailModule(getApplicationContext(), this))
                .build()
                .inject(this);
    }

    private void initView() {
        StatusBarUtils.transparencyBar(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
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

        String recipeId = intent.getStringExtra(KEY_RECIPE_ID);
        if (recipeId != null && !TextUtils.isEmpty(recipeId)) {
            detailPresenter.queryDetail(recipeId);
        } else {
            Toast.makeText(mContext, "没有收到recipeId", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleRecipeResult(MobRecipe value) {
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
    }


    @Override
    public void onQueryDetailSuccess(MobRecipe value) {
        handleRecipeResult(value);
    }
}
