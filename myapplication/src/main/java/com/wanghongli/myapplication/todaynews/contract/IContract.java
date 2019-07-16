package com.wanghongli.myapplication.todaynews.contract;

import com.google.android.material.tabs.TabLayout;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.base.IBasePresenter;
import com.wanghongli.myapplication.base.IBaseView;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.todaynews.ArticlesData;
import com.wanghongli.myapplication.todaynews.DataBean;
import com.wanghongli.myapplication.todaynews.DatasBean;
import com.wanghongli.myapplication.wxtwo.TabBean;

import java.util.List;

public interface IContract {
    interface Model {
        void getTabData(LifecycleProvider provider, @HomeModel.HomeLoadType  int type, IBaseCallBack<List<TabBean>> callBack);
        void getArticleData(LifecycleProvider provider, @HomeModel.HomeLoadType int type,int id,int page, IBaseCallBack<ArticlesData> callBack);

        void getLoadMoreArticleData(LifecycleProvider provider, @HomeModel.HomeLoadType int type,int page,IBaseCallBack<ArticlesData> callBack);
    }

    interface View extends IBaseView<Presenter> {
        void setTabData(List<TabBean> list,String msg);
        void setArticleData(List<ArticlesData.DataBean> list,String msg);

        void setLoadMoreArticleData(ArticlesData data,String msg);
    }


    interface Presenter extends IBasePresenter<View> {
        void getTabPresenter(@HomeModel.HomeLoadType int type);

        void getArticlePresenter(@HomeModel.HomeLoadType int type,int page,int id);

        void getLoadArticlePresenter(@HomeModel.HomeLoadType int type,int page);
    }
}
