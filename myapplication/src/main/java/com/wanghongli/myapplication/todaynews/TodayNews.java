package com.wanghongli.myapplication.todaynews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.base.BaseFragment;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.todaynews.contract.IContract;
import com.wanghongli.myapplication.todaynews.presenter.IPresenter;
import com.wanghongli.myapplication.wxtwo.TabBean;
import com.wanghongli.myapplication.wxtwo.presenter.WxTwoPresenter;

import java.util.ArrayList;
import java.util.List;

public class TodayNews extends BaseFragment implements IContract.View {
    private static final String TAG = "TodayNews";
//    private FrameLayout mParent_container;
    private TabLayout mTab;
    private ViewPager mVp;
    private TodayVpAdapter mTodayVpAdapter;
    private IContract.Presenter mPresenter;
    private ImageView mIv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.todaynesw_fragment, container, false);
        setPresenter(new IPresenter());
        initView(inflate);
        initmvp();
        return inflate;
    }

    private void initmvp() {
        mPresenter.getTabPresenter(HomeModel.HomeLoadType.LOAD_TYPE_LOAD);
    }

    private void initView(View inflate) {
        mIv = inflate.findViewById(R.id.iv);
//        mParent_container = inflate.findViewById(R.id.parent_container);
        mTab = inflate.findViewById(R.id.tab);
        mVp = inflate.findViewById(R.id.vp);
        //多层嵌套为了达到公用一个model层
        //mtab   tab  数据接口解析出来才判断   fragment

        mTab.setupWithViewPager(mVp);
        //miv


    }

    @Override
    public void setTabData(List<TabBean> list, String msg) {
//        Log.d(TAG, "setTabData: tab  "+list.size());
        Log.d(TAG, "setTabData: tab  "+msg);
        ArrayList<TFragment> tFragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        if (list!=null){
            for (int i = 0; i < list.size(); i++) {
                TabBean tabBean = list.get(i);
                mTab.addTab(mTab.newTab().setText(tabBean.getName()));
                TFragment tFragment = new TFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("id",tabBean.getId());
                Log.d(TAG, "setTabData: "+tabBean.getId());
                tFragment.setArguments(bundle);
                tFragments.add(tFragment);
                titles.add(tabBean.getName());
            }
            //mvp
            mTodayVpAdapter = new TodayVpAdapter(getFragmentManager(),tFragments,titles);
            mVp.setAdapter(mTodayVpAdapter);
            mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),OrderActivity.class);
                    intent.putStringArrayListExtra("data",titles);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void setArticleData(List<ArticlesData.DataBean> list, String msg) {

    }

    @Override
    public void setLoadMoreArticleData(ArticlesData data, String msg) {

    }

    @Override
    public void setPresenter(IContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext();
    }
}
