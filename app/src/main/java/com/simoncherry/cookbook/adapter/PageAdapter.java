package com.simoncherry.cookbook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Simon on 2017/4/4.
 */

public class PageAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> mData;

    public PageAdapter(FragmentManager fm, ArrayList<Fragment> data) {
        super(fm);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }
}
