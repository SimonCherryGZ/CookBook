package com.simoncherry.cookbook.fragment;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.adapter.PageAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Unbinder unbinder;

    private PageAdapter pageAdapter;
    private ArrayList<Fragment> fragmentArrayList;
    private CommonNavigatorAdapter navigatorAdapter;
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
        pageAdapter = new PageAdapter(getChildFragmentManager(), fragmentArrayList);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pageAdapter);
    }

    private void initIndicator() {
        navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.colorPrimary));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.colorAccent));
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 10));
                linePagerIndicator.setColors(getResources().getColor(R.color.colorAccent));
                return linePagerIndicator;
            }
        };

        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(navigatorAdapter);

        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getContext(), 15);
            }
        });

        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void initFragment() {
        RecipeFragment recipeFragment1 = RecipeFragment.newInstance("0010001007");
        RecipeFragment recipeFragment2 = RecipeFragment.newInstance("0010001008");
        RecipeFragment recipeFragment3 = RecipeFragment.newInstance("0010001010");

        titleList.add("荤菜");
        titleList.add("素菜");
        titleList.add("西点");
        navigatorAdapter.notifyDataSetChanged();

        fragmentArrayList.add(recipeFragment1);
        fragmentArrayList.add(recipeFragment2);
        fragmentArrayList.add(recipeFragment3);
        pageAdapter.notifyDataSetChanged();
    }
}
