package com.simoncherry.cookbook.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.adapter.PageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    @BindView(R.id.layout_tab)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Unbinder unbinder;

    private PageAdapter pageAdapter;
    private ArrayList<Fragment> fragmentArrayList;
    private ArrayList<String> titleList;


    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance() {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_page, container, false);
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

    private void init() {
        initViewPager();
        initIndicator();
        initFragment();
    }

    private void initViewPager() {
        titleList = new ArrayList<>();
        fragmentArrayList = new ArrayList<>();
        pageAdapter = new PageAdapter(getChildFragmentManager(), fragmentArrayList, titleList);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pageAdapter);
    }

    private void initIndicator() {
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFragment() {
        RecipeFragment recipeFragment1 = RecipeFragment.newInstance("0010001007");
        RecipeFragment recipeFragment2 = RecipeFragment.newInstance("0010001008");
        RecipeFragment recipeFragment3 = RecipeFragment.newInstance("0010001010");

        titleList.add("荤菜");
        titleList.add("素菜");
        titleList.add("西点");

        fragmentArrayList.add(recipeFragment1);
        fragmentArrayList.add(recipeFragment2);
        fragmentArrayList.add(recipeFragment3);

        pageAdapter.notifyDataSetChanged();
    }
}
