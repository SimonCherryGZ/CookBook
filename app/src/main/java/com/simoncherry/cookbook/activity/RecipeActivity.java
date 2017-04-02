package com.simoncherry.cookbook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.adapter.RecipeAdapter;
import com.simoncherry.cookbook.component.DaggerRecipeComponent;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;
import com.simoncherry.cookbook.module.RecipeModule;
import com.simoncherry.cookbook.presenter.impl.RecipePresenterImpl;
import com.simoncherry.cookbook.view.RecipeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeActivity extends AppCompatActivity implements RecipeView {

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;


    public final static String KEY_CTG_ID = "ctgId";

    private RecipeAdapter mAdapter;
    private List<MobRecipe> mData;

    private Context mContext;
    private Unbinder unbinder;
    @Inject
    RecipePresenterImpl recipePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        unbinder = ButterKnife.bind(this);
        mContext = RecipeActivity.this;

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void init() {
        initComponent();
        initRecyclerView();
        initData();
    }

    private void initComponent() {
        DaggerRecipeComponent.builder()
                .recipeModule(new RecipeModule(getApplicationContext(), this))
                .build()
                .inject(this);
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new RecipeAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mData.size() > position) {
                    startDetailActivity(view, mData.get(position).getMenuId(), mData.get(position).getThumbnail());
                }
            }
        });
        rvRecipe.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvRecipe.setAdapter(mAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        String cid = intent.getStringExtra(KEY_CTG_ID);
        if (cid != null) {
            recipePresenter.queryRecipe(cid, 1, 20);
        } else {
            Toast.makeText(mContext, "没有收到CID", Toast.LENGTH_SHORT).show();
        }
    }

    private void startDetailActivity(View view, String recipeId, String thumbnail) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_RECIPE_ID, recipeId);
        intent.putExtra(DetailActivity.KEY_THUMBNAIL, thumbnail);
        ActivityTransitionLauncher.with(RecipeActivity.this).from(view).launch(intent);
    }

    @Override
    public void onQueryRecipeSuccess(MobRecipeResult result) {
        mData.clear();
        if (result != null) {
            List<MobRecipe> resultList = result.getList();
            if (resultList != null && resultList.size() > 0) {
                mData.addAll(resultList);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
