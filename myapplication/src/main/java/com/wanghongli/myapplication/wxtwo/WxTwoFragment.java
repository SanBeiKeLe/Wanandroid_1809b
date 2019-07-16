package com.wanghongli.myapplication.wxtwo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.Wx.AFragment;
import com.wanghongli.myapplication.base.BaseFragment;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.wxtwo.contract.WxTwoContract;
import com.wanghongli.myapplication.wxtwo.presenter.WxTwoPresenter;

import java.util.ArrayList;
import java.util.List;

public class WxTwoFragment extends BaseFragment implements WxTwoContract.View {
    private static final String TAG = "WxFragment";
    private TabLayout mTab;
    private ViewPager mVp;
    private WxTwoContract.Presenter mWxPresenter;
    private WxTwoVpAdapter mWxTwoVpAdapter;
    private ImageView mImg_child;
    private ArrayList<TabBean> mTabChild;


    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.wx_fragment, container, false);
        setPresenter(new WxTwoPresenter());
//        showLoadingPage(LoadingPage.MODE_2);
        getOrRefresh(HomeModel.HomeLoadType.LOAD_TYPE_LOAD);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mTab = inflate.findViewById(R.id.tab);
        mImg_child = inflate.findViewById(R.id.img_child);
        mVp = inflate.findViewById(R.id.vp);
        mTabChild = new ArrayList<>();

        mTab.setupWithViewPager(mVp);
        if (mTabChild!=null){
            mImg_child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("点击了");
                    Intent intent = new Intent(getContext(), TabActivity.class);
                    intent.putParcelableArrayListExtra("data", mTabChild);
                    startActivity(intent);

                }
            });
        }



    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

    private void getOrRefresh(int loadTypeLoad) {
        mWxPresenter.getTabPresenter(loadTypeLoad);
//        mWxPresenter.getWxArticlesPresenter(0,408,loadTypeLoad);

    }

    @Override
    public void setTabData(List<TabBean> banner, String msg) {
        Log.d(TAG, "setTabData: " + banner);

        mTabChild.addAll(banner);

        ArrayList<TabBean> tabList = new ArrayList<>();
        tabList.addAll(banner);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < banner.size(); i++) {
//            mTab.addTab(mTab.newTab().setText(banner.get(i).getName()));
            AFragment aFragment = new AFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", banner.get(i).getId());
            aFragment.setArguments(bundle);
            fragments.add(aFragment);
        }
        mWxTwoVpAdapter = new WxTwoVpAdapter(getChildFragmentManager(), banner, fragments);

        mVp.setAdapter(mWxTwoVpAdapter);

    }

    @Override
    public void setWxArticleData(List<WxArticleBean.DataBean> articleData, String msg) {
//
//        Log.d(TAG, "setWxArticleData: " + articleData + "msg" + msg);
//
//        mWxVpAdapter = new WxVpAdapter(getChildFragmentManager());
//
//
//        mWxVpAdapter.addArticleData(articleData,mAFragments,mDataBeanArrayList);
//
//        mVp.setAdapter(mWxVpAdapter);
//
//        Logger.d("%s setWxArticleData articleData  %" + articleData + "%s setWxArticleData msg  %" + msg);
    }


    @Override
    public void setLoadMoreArticleReceiveData(WxArticleBean articleData, String msg) {
        Log.d(TAG, "setLoadMoreArticleReceiveData: " + articleData + "msg:" + msg);

    }


    @Override
    public void setPresenter(WxTwoContract.Presenter presenter) {
        mWxPresenter = presenter;
        mWxPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext();
    }
}
