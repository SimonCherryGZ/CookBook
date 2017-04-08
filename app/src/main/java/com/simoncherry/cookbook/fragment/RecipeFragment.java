package com.simoncherry.cookbook.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.orhanobut.logger.Logger;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.activity.DetailActivity;
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


public class RecipeFragment extends Fragment implements RecipeView{

    @BindView(R.id.layout_recipe)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;


    private static final String TAG = RecipeFragment.class.getSimpleName();
    private static final String ARG_CTG_ID = "ctgId";
    private final static int PAGE_SIZE = 15;
    private int currentPage = 1;
    private String ctgId = "";
    private String field = "cid";
    private String value = "";

    private RecipeAdapter mAdapter;
    private List<MobRecipe> mData;

    private Context mContext;
    private Unbinder unbinder;
    @Inject
    RecipePresenterImpl recipePresenter;


    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance(String ctgId) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CTG_ID, ctgId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ctgId = getArguments().getString(ARG_CTG_ID);
            field = "cid";
            value = getArguments().getString(ARG_CTG_ID);
        }
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onQueryRecipeSuccess(MobRecipeResult result) {
        if (result != null) {
            if (currentPage == 1) {
                mData.clear();
            }
            List<MobRecipe> resultList = result.getList();
            if (resultList != null && resultList.size() > 0) {
                mData.addAll(resultList);
            }
            currentPage++;
        }
        mAdapter.notifyDataSetChanged();

        if (refreshLayout != null) {
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onQueryRecipeFailed() {
        if (refreshLayout != null) {
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }
    }

    private void init() {
        initComponent();
        initRecyclerView();
        initRefreshLayout();
        currentPage = 1;
        //queryRecipe(ctgId, currentPage);
        queryRecipeByField(field, value, currentPage);
    }

    private void initComponent() {
        DaggerRecipeComponent.builder()
                .recipeModule(new RecipeModule(getActivity().getApplicationContext(), this))
                .build()
                .inject(this);
    }

    private void initRefreshLayout() {
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                currentPage = 1;
                //queryRecipe(ctgId, currentPage);
                queryRecipeByField(field, value, currentPage);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                //queryRecipe(ctgId, currentPage);
                queryRecipeByField(field, value, currentPage);
            }
        });
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

    private void queryRecipe(String ctgId, int currentPage) {
        Logger.t(TAG).e("RecipeFragment: " + RecipeFragment.this.hashCode() + "  |  currentPage: " + currentPage);
        if (ctgId != null) {
            recipePresenter.queryRecipe(ctgId, currentPage, PAGE_SIZE);
        } else {
            Toast.makeText(mContext, "获取菜谱分类失败，请退出后重新进入", Toast.LENGTH_SHORT).show();
        }
    }

    private void startDetailActivity(View view, String recipeId, String thumbnail) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_RECIPE_ID, recipeId);
        intent.putExtra(DetailActivity.KEY_THUMBNAIL, thumbnail);
        ActivityTransitionLauncher.with(getActivity()).from(view).launch(intent);
    }

    public void changeCategory(String ctgId) {
        this.ctgId = ctgId;
        this.field = "cid";
        this.value = ctgId;
        currentPage = 1;
        rvRecipe.scrollToPosition(0);
        //queryRecipe(ctgId, currentPage);
        queryRecipeByField(field, value, currentPage);
    }

    private void queryRecipeByField(String field, String value, int currentPage) {
        Logger.t(TAG).e("RecipeFragment: " + RecipeFragment.this.hashCode() + "  |  currentPage: " + currentPage);
        if (ctgId != null) {
            recipePresenter.queryRecipeByField(field, value, currentPage, PAGE_SIZE);
        } else {
            Toast.makeText(mContext, "获取菜谱分类失败，请退出后重新进入", Toast.LENGTH_SHORT).show();
        }
    }

    public void queryRecipeByName(String name) {
        this.field = "name";
        this.value = name;
        currentPage = 1;
        rvRecipe.scrollToPosition(0);
        queryRecipeByField(field, value, currentPage);
    }
}
