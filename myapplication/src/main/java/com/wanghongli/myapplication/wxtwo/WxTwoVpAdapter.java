package com.wanghongli.myapplication.wxtwo;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.wanghongli.myapplication.Wx.AFragment;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;

import java.util.ArrayList;
import java.util.List;

public class WxTwoVpAdapter extends FragmentStatePagerAdapter {


    private final List<TabBean> mList;
    private ArrayList<Fragment> mFragments;

    public WxTwoVpAdapter(FragmentManager fm, List<TabBean> banner, ArrayList<Fragment> fragments) {
        super(fm);
        mList = banner;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();
    }
/*
    public void addArticleData(List<WxArticleBean.DataBean> articleData, ArrayList<AFragment> AFragments, ArrayList<WxTabBean.DataBean> dataBeanArrayList) {
        mFragments.addAll(AFragments);
        mList.addAll(dataBeanArrayList);
        mList.addAll(articleData);
        notifyDataSetChanged();
    }*/
}
