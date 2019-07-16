package com.wanghongli.myapplication.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanghongli.myapplication.LoadingPage;
import com.wanghongli.myapplication.MainActivity;
import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.base.BaseFragment;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.home.contract.HomeContract;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.home.presenter.HomePresenter;
import com.wanghongli.myapplication.utils.Logger;

import java.nio.channels.FileChannel;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private TextView mHome_banner_title;
    private ViewPager mViewPager;
    private float mFactory;
    private boolean isManualScroll;
    private BannerIndicator mHome_banner_indicator;
    private RecyclerView mRlv;
    private SmartRefreshLayout mSmart_refresh;
    private int mPage = 0;
    private HomePresenter mHomePresenter;
    private int mResponseCount;
    private Home_Rlv_ArtilceAdapter mAdapter;
    private List<ArticleData.Article> mTopArticles;
    private List<ArticleData.Article> mArticles;
    private List<Banner> mBanners;


    @Override
    protected boolean isNeedToAddBackStack() {
        return false;
    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        setPresenter(new HomePresenter());
        initView(inflate);
        return inflate;
    }


    private void initView(View inflate) {
        mRlv = inflate.findViewById(R.id.rlv_Article_list);
        mSmart_refresh = inflate.findViewById(R.id.home_smart_refresh_layout);
        mSmart_refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Logger.d("%s上拉加载更多，当前已经展示到%s页", getTag(), mPage);
                mHomePresenter.loadMoreArticlesPresenter(mPage, HomeModel.HomeLoadType.LOAD_TYPE_MORE);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Logger.d("%s下拉加载更多", getTag());
                getOrRefresh(HomeModel.HomeLoadType.LOAD_TYPE_REFRESH);
            }
        });


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d("%s 第一次进来，显示loading 页面，开始获取banner,获取置顶文章，获取普通文章", getTag());
        showLoadingPage(LoadingPage.MODE_2);
        getOrRefresh(HomeModel.HomeLoadType.LOAD_TYPE_LOAD);
    }

    private void getOrRefresh(int type) {
        mHomePresenter.getBannerPresenter(type);
        mHomePresenter.getArticlesPresenter(mPage, type);
        mHomePresenter.getTopArticlesPresenter(type);
    }

    @Override
    public void setBannerData(List<Banner> banner, String msg) {
        //bannner 回来先+1
        mResponseCount++;

        mBanners.addAll(banner);
        mAdapter.notifyDataSetChanged();
//        mHomePresenter.getBannerPresenter(HomeModel.HomeLoadType.LOAD_TYPE_REFRESH);
        handResponseData();
    }

    private void handResponseData() {
        if (mResponseCount == 3) {
            //最后一个回来的显示页面
            mResponseCount = 0;
            Home_Rlv_ArtilceAdapter rlvAdapter = getRlvAdapter();
            if (rlvAdapter.getItemCount() != 0) {
                //刷新请求
                Logger.d("%s刷新请求", getTag());
                mSmart_refresh.finishRefresh();
            }
            //置顶文章
            ArrayList<ArticleData.Article> toSetTopList = new ArrayList<>();
            if (mArticles != null && mTopArticles.size() > 0) {
                mTopArticles.addAll(toSetTopList);
                mTopArticles.clear();
                mTopArticles = null;

            } else {
                Logger.d("%s请求回来的数据是空的" + getTag());
            }
            if (mBanners == null || mBanners.size() < 0 && toSetTopList.size() <= 0) {
                //没有内容,请求出错
                if (rlvAdapter.getItemCount() == 0) {
                    onError("加载失败", new LoadingPage.OnReloadListener() {
                        @Override
                        public void reload() {
                            getOrRefresh(HomeModel.HomeLoadType.LOAD_TYPE_LOAD);
                        }
                    });
                }else {
                    Logger.d("%s 刷新回来的 的banner ，指定文章，普通文章都是空的，由于当前页面已经展示了之前的数据，只是刷新失败了，因此展示不做任何修改",getTag());

                }

            }else {
                if (rlvAdapter.getItemCount()==0){
                    //关闭loadyemian
                    dismissLoadingPage();
                }
                mAdapter.setBanners(mBanners);
                mAdapter.setArticlesTop(mTopArticles);
                mAdapter.notifyDataSetChanged();
            }
        }else {
            Logger.d("%s 由于本次请求不是最后一个回来的，后面还有请求没有回来，因此不做任何处理，等待后面的请求回来，让后面的请求处理数据显示", getTag());
        }
    }

    private Home_Rlv_ArtilceAdapter getRlvAdapter() {
        if (mAdapter == null) {
            Logger.d("%s HomeAdapter 还没有创建，因此需要new 一个新的，并设置给 recycler view", getTag());
            mAdapter = new Home_Rlv_ArtilceAdapter();
            mRlv.setAdapter(mAdapter);
            mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
            mRlv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        } else {
            Logger.d("%s HomeAdapter 已经存在，因此直接返回已有的 adpater", getTag());
        }

        return mAdapter;
    }

    @Override
    public void setArticleData(ArticleData articleData, String msg) {
        // 回来了先登记，累加器加一
        mResponseCount++;
        if(articleData!= null){
            mArticles = articleData.getDatas();
            mPage = articleData.getCurPage();
        }

        handResponseData();

    }

    @Override
    public void setTopArticleData(List<ArticleData.Article> articles, String msg) {

    }

    @Override
    public void onLoadMoreArticleData(ArticleData articleData, String msg) {
        Logger.d("%s 在更多数据回来了。不管是否成功与失败都要调用 SmartRefreshLayout.finishLoadMore() 关闭加载更多动画", getTag());
        if (articleData!=null&&articleData.getDatas()!=null&& articleData.getDatas().size()>0){
            mPage=articleData.getCurPage();
            Home_Rlv_ArtilceAdapter rlvAdapter = getRlvAdapter();
            rlvAdapter.addArticle(articleData.getDatas());
            //todo   over
        }else {
            showToast("没哟数据展示");
            Logger.d("%s没哟数据展示"+getTag());
        }

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mHomePresenter = (HomePresenter) presenter;
        mHomePresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext();
    }
}







