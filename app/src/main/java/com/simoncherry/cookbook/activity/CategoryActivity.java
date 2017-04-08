package com.simoncherry.cookbook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.adapter.parentCategoryAdapter;
import com.simoncherry.cookbook.component.DaggerCategoryComponent;
import com.simoncherry.cookbook.model.MobCategory;
import com.simoncherry.cookbook.model.MobCategoryChild1;
import com.simoncherry.cookbook.model.MobCategoryChild2;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.module.CategoryModule;
import com.simoncherry.cookbook.presenter.impl.CategoryPresenterImpl;
import com.simoncherry.cookbook.view.CategoryView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryActivity extends AppCompatActivity implements CategoryView{

    @BindView(R.id.rv_category)
    RecyclerView rvCategory;

    private parentCategoryAdapter mAdapter;
    private List<MobCategory> mData;

    private Context mContext;
    private Unbinder unbinder;
    @Inject
    CategoryPresenterImpl categoryPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        unbinder = ButterKnife.bind(this);
        mContext = CategoryActivity.this;

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
        categoryPresenter.queryCategory();
    }

    private void initComponent() {
        DaggerCategoryComponent.builder()
                .categoryModule(new CategoryModule(getApplicationContext(), this))
                .build()
                .inject(this);
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new parentCategoryAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(new parentCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(mContext, "click No." + position, Toast.LENGTH_SHORT).show();
                if (mData.size() > position) {
                    startRecipeActivity(mData.get(position).getCtgId());
                }
            }
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvCategory.setAdapter(mAdapter);
    }

    private void startRecipeActivity(String cid) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.KEY_CTG_ID, cid);
        startActivity(intent);
    }

    @Override
    public void onQueryCategorySuccess(MobCategoryResult result) {
        mData.clear();
        if (result != null) {
            mData.add(result.getCategoryInfo());
            // 第1层子类
            ArrayList<MobCategoryChild1> child1 = result.getChilds();
            if (child1 != null && child1.size() > 0) {
                for (MobCategoryChild1 categoryChild1 : child1) {
                    mData.add(categoryChild1.getCategoryInfo());
                    // 第2层子类
                    ArrayList<MobCategoryChild2> child2 = categoryChild1.getChilds();
                    if (child2 != null && child2.size() > 0) {
                        for (MobCategoryChild2 categoryChild2 : child2) {
                            mData.add(categoryChild2.getCategoryInfo());
                        }
                    }
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
