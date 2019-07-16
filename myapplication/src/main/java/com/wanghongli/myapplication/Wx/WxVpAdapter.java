package com.wanghongli.myapplication.Wx;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;

import java.util.ArrayList;
import java.util.List;

public class WxVpAdapter extends FragmentStatePagerAdapter {
    private ArrayList<WxArticleBean.DataBean> mList = new ArrayList<>();
    private ArrayList<WxTabBean.DataBean> mTitle = new ArrayList<>();
    private ArrayList<AFragment> fragments=new ArrayList<>();


    public WxVpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position).getName();
    }

    public void addArticleData(List<WxArticleBean.DataBean> articleData, ArrayList<AFragment> AFragments, ArrayList<WxTabBean.DataBean> dataBeanArrayList) {
        fragments.addAll(AFragments);
        mTitle.addAll(dataBeanArrayList);
        mList.addAll(articleData);
        notifyDataSetChanged();
    }
}
