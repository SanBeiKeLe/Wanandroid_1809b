package com.wanghongli.myapplication.todaynews;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TodayVpAdapter extends FragmentStatePagerAdapter {
    private ArrayList<TFragment> mTFragments;
    private ArrayList<String> mTitles;

    public TodayVpAdapter(FragmentManager fm, ArrayList<TFragment> tFragments, ArrayList<String> titles) {
        super(fm);
        mTFragments = tFragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mTFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
