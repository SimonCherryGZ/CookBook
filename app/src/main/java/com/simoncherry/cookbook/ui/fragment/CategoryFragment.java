package com.simoncherry.cookbook.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.custom.GridSpacingItemDecoration;
import com.simoncherry.cookbook.model.MobCategory;
import com.simoncherry.cookbook.model.RealmCategory;
import com.simoncherry.cookbook.realm.RealmHelper;
import com.simoncherry.cookbook.ui.adapter.childCategoryAdapter;
import com.simoncherry.cookbook.ui.adapter.parentCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment{

    private final static String TAG = CategoryFragment.class.getSimpleName();

    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.rv_tag)
    RecyclerView rvTag;

    private parentCategoryAdapter parentCategoryAdapter;
    private List<MobCategory> parentList;
    private childCategoryAdapter childCategoryAdapter;
    private List<List<MobCategory>> allChildList;
    private List<MobCategory> childList;

    private Realm realm;
    private RealmResults<RealmCategory> realmResults;

    private OnFragmentInteractionListener mListener;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initComponent() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realmResults.removeAllChangeListeners();
        realm.close();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public interface OnFragmentInteractionListener {
        void onClickCategory(String ctgId, String name);
    }

    private void init() {
        initRecyclerView();
        initRealm();
    }

    private void initRecyclerView() {
        parentList = new ArrayList<>();
        parentCategoryAdapter = new parentCategoryAdapter(mContext, parentList);
        parentCategoryAdapter.setOnItemClickListener(new parentCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (allChildList.size() > position) {
                    childList.clear();
                    childList.addAll(allChildList.get(position));
                    Logger.t(TAG).i("childList: " + childList.toString());
                    childCategoryAdapter.notifyDataSetChanged();
                }
            }
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvCategory.setAdapter(parentCategoryAdapter);

        allChildList = new ArrayList<>();
        childList = new ArrayList<>();
        childCategoryAdapter = new childCategoryAdapter(mContext, childList);
        childCategoryAdapter.setOnItemClickListener(new childCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (childList.size() > position) {
                    onClickCategory(childList.get(position).getCtgId(), childList.get(position).getName());
                }
            }
        });
        rvTag.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvTag.addItemDecoration(new GridSpacingItemDecoration(3, 20, true));
        rvTag.setAdapter(childCategoryAdapter);
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();
        realmResults = realm.where(RealmCategory.class).findAllAsync();
        realmResults.addChangeListener(new RealmChangeListener<RealmResults<RealmCategory>>() {
            @Override
            public void onChange(RealmResults<RealmCategory> element) {
                if (element.size() > 0) {
                    handleRealmResult(element);
                }
            }
        });
    }

    private void handleRealmResult(RealmResults<RealmCategory> element) {
        RealmResults<RealmCategory> parentCategory = element.where().equalTo("isChild", false).findAll();
        if (parentCategory != null) {
            parentList.clear();
            allChildList.clear();

            for (RealmCategory category : parentCategory) {
                parentList.add(RealmHelper.convertRealmCategoryToMobCategory(category));
                RealmResults<RealmCategory> childCategory = element.where().equalTo("parentId", category.getCtgId()).findAll();
                if (childCategory != null) {
                    List<MobCategory> childList = new ArrayList<>();
                    for (RealmCategory category1 : childCategory) {
                        childList.add(RealmHelper.convertRealmCategoryToMobCategory(category1));
                    }
                    allChildList.add(childList);
                }
            }
            parentList.get(0).setSelected(true);
        }

        if (allChildList != null && allChildList.size() > 0) {
            Logger.t(TAG).i("allChildList: " + allChildList.toString());
            childList.clear();
            childList.addAll(allChildList.get(0));
        }

        parentCategoryAdapter.notifyDataSetChanged();
        childCategoryAdapter.notifyDataSetChanged();
    }

    private void onClickCategory(String ctgId, String name) {
        if (mListener != null) {
            mListener.onClickCategory(ctgId, name);
        }
    }
}
