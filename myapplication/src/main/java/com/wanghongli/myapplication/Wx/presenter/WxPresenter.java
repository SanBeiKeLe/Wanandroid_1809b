package com.wanghongli.myapplication.Wx.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.Wx.contract.WxContract;
import com.wanghongli.myapplication.Wx.model.WxModel2;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;
import com.wanghongli.myapplication.home.remote.WxRemoteSource;

import java.util.ArrayList;
import java.util.List;

public class WxPresenter implements WxContract.Presenter {

    private WxModel2 mWxModel;
    private WxContract.View mView;

    public WxPresenter() {
        mWxModel = new WxModel2(new WxRemoteSource(), null);
    }

    @Override
    public void getTabPresenter(int type) {
        mWxModel.getTabData((LifecycleProvider) mView, type, new IBaseCallBack<List<WxTabBean>>() {
            @Override
            public void onSuccess(List<WxTabBean> data) {
                mView.setTabData(data, null);
            }

            @Override
            public void onFail(String msg) {
                mView.setTabData(null, msg);
            }
        });
    }

    @Override
    public void getWxArticlesPresenter(int page, int id, int type) {
        mWxModel.getWxArticleData((LifecycleProvider) mView,id,type,page, new IBaseCallBack<WxArticleBean>() {
            @Override
            public void onSuccess(WxArticleBean data) {
                List<WxArticleBean.DataBean> wxArticleBeans = new ArrayList<>();
                mView.setWxArticleData(wxArticleBeans, null);
            }

            @Override
            public void onFail(String msg) {
                mView.setWxArticleData(null, msg);
            }
        });
    }



    @Override
    public void loadMoreArticles(int page, int type) {
        mWxModel.loadMoreArticles((LifecycleProvider) mView, type, page, new IBaseCallBack<WxArticleBean>() {
            @Override
            public void onSuccess(WxArticleBean data) {
                mView.setLoadMoreArticleReceiveData(data, null);
            }

            @Override
            public void onFail(String msg) {
                mView.setLoadMoreArticleReceiveData(null, msg);

            }
        });
    }

    @Override
    public void attachView(WxContract.View view) {

        mView = view;
    }

    @Override
    public void detachView(WxContract.View view) {
        mView = null;
    }
}
