package com.simoncherry.cookbook.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.simoncherry.cookbook.GridSpacingItemDecoration;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.adapter.CategoryAdapter;
import com.simoncherry.cookbook.adapter.TagAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryView{

    private final static String TAG = CategoryFragment.class.getSimpleName();

    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.rv_tag)
    RecyclerView rvTag;

    private CategoryAdapter categoryAdapter;
    private List<MobCategory> categoryList;
    private TagAdapter tagAdapter;
    private List<List<MobCategory>> allTagList;
    private List<MobCategory> tagList;

    private Context mContext;
    private Unbinder unbinder;
    @Inject
    CategoryPresenterImpl categoryPresenter;

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
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    @Override
    public void onQueryCategorySuccess(MobCategoryResult result) {
        categoryList.clear();
        allTagList.clear();
        if (result != null) {
//            resultList.add(result.getCategoryInfo());
            // 第1层子类
            ArrayList<MobCategoryChild1> child1 = result.getChilds();
            if (child1 != null && child1.size() > 0) {
                for (MobCategoryChild1 categoryChild1 : child1) {
                    categoryList.add(categoryChild1.getCategoryInfo());
                    // 第2层子类
                    ArrayList<MobCategoryChild2> child2 = categoryChild1.getChilds();
                    if (child2 != null && child2.size() > 0) {
                        List<MobCategory> childList = new ArrayList<>();
                        for (MobCategoryChild2 categoryChild2 : child2) {
                            childList.add(categoryChild2.getCategoryInfo());
                        }
                        allTagList.add(childList);
                    }
                }
                categoryList.get(0).setSelected(true);
            }
        }

        if (allTagList != null && allTagList.size() > 0) {
            Logger.t(TAG).e("allTagList: " + allTagList.toString());
            tagList.clear();
            tagList.addAll(allTagList.get(0));
        }

        categoryAdapter.notifyDataSetChanged();
        tagAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        void onClickCategory(String ctgId, String name);
    }

    private void init() {
        initComponent();
        initRecyclerView();
        categoryPresenter.queryCategory();
    }

    private void initComponent() {
        DaggerCategoryComponent.builder()
                .categoryModule(new CategoryModule(getActivity().getApplicationContext(), this))
                .build()
                .inject(this);
    }

    private void initRecyclerView() {
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(mContext, categoryList);
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (allTagList.size() > position) {
                    tagList.clear();
                    tagList.addAll(allTagList.get(position));
                    Logger.t(TAG).e("tagList: " + tagList.toString());
                    tagAdapter.notifyDataSetChanged();
                }
            }
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvCategory.setAdapter(categoryAdapter);

        allTagList = new ArrayList<>();
        tagList = new ArrayList<>();
        tagAdapter = new TagAdapter(mContext, tagList);
        tagAdapter.setOnItemClickListener(new TagAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (tagList.size() > position) {
                    onClickCategory(tagList.get(position).getCtgId(), tagList.get(position).getName());
                }
            }
        });
        rvTag.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvTag.addItemDecoration(new GridSpacingItemDecoration(3, 20, true));
        rvTag.setAdapter(tagAdapter);
    }

    private void onClickCategory(String ctgId, String name) {
        if (mListener != null) {
            mListener.onClickCategory(ctgId, name);
        }
    }
}
