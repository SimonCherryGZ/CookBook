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

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.activity.DetailActivity;
import com.simoncherry.cookbook.adapter.CollectionAdapter;
import com.simoncherry.cookbook.model.RealmMobRecipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionFragment extends Fragment {

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;

    private CollectionAdapter mAdapter;
    private List<RealmMobRecipe> mData;

    private Context mContext;
    private Unbinder unbinder;

    private Realm realm;
    private RealmResults<RealmMobRecipe> realmResults;


    public CollectionFragment() {
        // Required empty public constructor
    }

    public static CollectionFragment newInstance() {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_collection, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realmResults.removeAllChangeListeners();
        realm.close();
        unbinder.unbind();
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
        mAdapter = new CollectionAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
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
        realmResults = realm.where(RealmMobRecipe.class).findAllAsync();
        realmResults.addChangeListener(new RealmChangeListener<RealmResults<RealmMobRecipe>>() {
            @Override
            public void onChange(RealmResults<RealmMobRecipe> element) {
                loadCollectionRecipeFromRealm(element);
            }
        });
    }

    private void loadCollectionRecipeFromRealm(RealmResults<RealmMobRecipe> element) {
        mData.clear();
        if (element.size() > 0) {
            List<RealmMobRecipe> recipeList = element.subList(0, element.size());
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
