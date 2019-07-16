package com.wanghongli.myapplication.home.presenter;

import android.widget.Adapter;

import androidx.appcompat.view.menu.MenuView;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.home.contract.HomeContract;
import com.wanghongli.myapplication.home.local.HomeSpLocalSource;
import com.wanghongli.myapplication.home.local.HomeSpLocalSource2;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.home.model.HomeModel2;

import java.util.Calendar;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeModel2 mHomeModel;
    private HomeContract.View mView;

    public HomePresenter() {
        mHomeModel =  new HomeModel2(new HomeSpLocalSource(),new HomeSpLocalSource2());
//        mHomeModel = new HomeModel();
    }

    @Override
    public void getBannerPresenter(int type) {
        mHomeModel.getBannerData((LifecycleProvider) mView, type, new IBaseCallBack<List<Banner>>() {
            @Override
            public void onSuccess(List<Banner> data) {
                if(mView != null){
                    mView.setBannerData(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                if(mView != null){
                    mView.setBannerData(null, msg);
                }
            }
        });
    }

    @Override
    public void getArticlesPresenter(int page, int type) {
        mHomeModel.getArticleData((LifecycleProvider) mView, type, page, new IBaseCallBack<ArticleData>() {
            @Override
            public void onSuccess(ArticleData data) {
                if(mView != null){
                    mView.setArticleData(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                if(mView != null){
                    mView.setArticleData(null, msg);
                }
            }
        });

    }

    @Override
    public void getTopArticlesPresenter(int type) {
        mHomeModel.getTopArticleData((LifecycleProvider) mView, type, new IBaseCallBack<List<ArticleData.Article>>() {
            @Override
            public void onSuccess(List<ArticleData.Article> data) {
                if(mView != null){
                    mView.setTopArticleData(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                if(mView != null){
                    mView.setTopArticleData(null, msg);
                }
            }
        });
    }

    @Override
    public void loadMoreArticlesPresenter(int page, int type) {
        mHomeModel.loadMoreArticles((LifecycleProvider) mView, type, page, new IBaseCallBack<ArticleData>() {
            @Override
            public void onSuccess(ArticleData data) {
                if(mView != null){
                    mView.onLoadMoreArticleData(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                if(mView != null){
                    mView.onLoadMoreArticleData(null, msg);
                }
            }
        });
    }

    @Override
    public void attachView(HomeContract.View view) {

        mView = view;
    }

    @Override
    public void detachView(HomeContract.View view) {
        mView = null;
    }
}
