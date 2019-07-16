package com.wanghongli.myapplication.Wx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wanghongli.myapplication.LoadingPage;
import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.Wx.contract.WxContract;
import com.wanghongli.myapplication.Wx.presenter.WxPresenter;
import com.wanghongli.myapplication.base.BaseFragment;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class WxFragment extends BaseFragment implements WxContract.View {
    private static final String TAG = "WxFragment";
    private TabLayout mTab;
    private ViewPager mVp;
    private WxPresenter mWxPresenter;
    private WxVpAdapter mWxVpAdapter;
    private ArrayList<AFragment> mAFragments;
    private ArrayList<WxTabBean.DataBean> mDataBeanArrayList;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.wx_fragment, container, false);
        setPresenter(new WxPresenter());
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mTab = inflate.findViewById(R.id.tab);
        mVp = inflate.findViewById(R.id.vp);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoadingPage(LoadingPage.MODE_2);
        getOrRefresh(HomeModel.HomeLoadType.LOAD_TYPE_LOAD);
    }

    private void getOrRefresh(int loadTypeLoad) {
        mWxPresenter.getTabPresenter(loadTypeLoad);
        mWxPresenter.getWxArticlesPresenter(0,408,loadTypeLoad);

    }


    @Override
    public void setTabData(List<WxTabBean> tabDataBeans, String msg) {
        if (tabDataBeans != null) {
            mDataBeanArrayList = new ArrayList<>();

            dismissLoadingPage();
            Log.d(TAG, "setTabData.size: " + tabDataBeans.size());
            mAFragments = new ArrayList<>();
            for (int i = 0; i < tabDataBeans.size(); i++) {
                List<WxTabBean.DataBean> data = tabDataBeans.get(i).getData();
                mDataBeanArrayList.addAll(data);
                for (int j = 0; j < data.size(); j++) {
                    mTab.addTab(mTab.getTabAt(i).setText(data.get(j).getName()), i);
                    AFragment aFragment = new AFragment();
                    Bundle bundle=new Bundle();
                    bundle.putInt("id",data.get(j).getId());
                    aFragment.setArguments(bundle);
                    aFragment.setArguments(bundle);
                    mAFragments.add(aFragment);
                }

            }
            mTab.setupWithViewPager(mVp);

            Log.d(TAG, "setTabData: " + tabDataBeans + "msg:" + msg+"mfragments:"+mAFragments.size());
            Logger.d("%s setWxArticleData articleData  %" + tabDataBeans + "%s setWxArticleData msg  %" + msg);

        }
    }

    @Override
    public void setWxArticleData(List<WxArticleBean.DataBean> articleData, String msg) {

        Log.d(TAG, "setWxArticleData: " + articleData + "msg" + msg);

        mWxVpAdapter = new WxVpAdapter(getChildFragmentManager());


        mWxVpAdapter.addArticleData(articleData,mAFragments,mDataBeanArrayList);

        mVp.setAdapter(mWxVpAdapter);

        Logger.d("%s setWxArticleData articleData  %" + articleData + "%s setWxArticleData msg  %" + msg);
    }


    @Override
    public void setLoadMoreArticleReceiveData(WxArticleBean articleData, String msg) {
        Log.d(TAG, "setLoadMoreArticleReceiveData: " + articleData + "msg:" + msg);

    }


    @Override
    public void setPresenter(WxContract.Presenter presenter) {
        mWxPresenter = (WxPresenter) presenter;
        mWxPresenter.attachView(this);

    }

    @Override
    public Context getContextObject() {
        return getContext();
    }
}
