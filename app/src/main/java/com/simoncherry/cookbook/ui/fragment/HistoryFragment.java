package com.simoncherry.cookbook.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.model.RealmHistory;
import com.simoncherry.cookbook.ui.activity.DetailActivity;
import com.simoncherry.cookbook.ui.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends SimpleFragment {

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;

    private HistoryAdapter mAdapter;
    private List<RealmHistory> mData;

    private Realm realm;
    private RealmResults<RealmHistory> realmResults;


    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realmResults.removeAllChangeListeners();
        realm.close();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initRecyclerView();
        initRealm();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new HistoryAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
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

    private void initRealm() {
        realm = Realm.getDefaultInstance();
        realmResults = realm.where(RealmHistory.class).findAllSortedAsync("createTime", Sort.DESCENDING);
        realmResults.addChangeListener(new RealmChangeListener<RealmResults<RealmHistory>>() {
            @Override
            public void onChange(RealmResults<RealmHistory> element) {
                loadHistoryRecipeFromRealm(element);
            }
        });
    }

    private void loadHistoryRecipeFromRealm(RealmResults<RealmHistory> element) {
        mData.clear();
        if (element.size() > 0) {
            List<RealmHistory> recipeList = element.subList(0, element.size());
            mData.addAll(recipeList);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void startDetailActivity(View view, String recipeId, String thumbnail) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_RECIPE_ID, recipeId);
        intent.putExtra(DetailActivity.KEY_THUMBNAIL, thumbnail);
        ActivityTransitionLauncher.with(getActivity()).from(view).launch(intent);
    }
}
