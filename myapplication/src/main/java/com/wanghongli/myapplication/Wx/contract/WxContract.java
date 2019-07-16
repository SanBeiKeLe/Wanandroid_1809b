package com.wanghongli.myapplication.Wx.contract;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.base.IBasePresenter;
import com.wanghongli.myapplication.base.IBaseView;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;
import com.wanghongli.myapplication.home.model.HomeModel;

import java.util.List;

import io.reactivex.Observable;

public interface WxContract {

    interface Model {
        void getTabData(LifecycleProvider provider, @HomeModel.HomeLoadType  int type, IBaseCallBack<List<WxTabBean>> callBack);
        void getWxArticleData(LifecycleProvider provider, @HomeModel.HomeLoadType  int type,int id,int page ,IBaseCallBack<WxArticleBean> callBack);
        void loadMoreArticles(LifecycleProvider provider, @HomeModel.HomeLoadType int type, int page, IBaseCallBack<WxArticleBean> callBack);

    }
    public abstract class DataSource {
        public abstract Observable<List<WxTabBean>> getTab();

        public abstract Observable<WxArticleBean> getWxArticles(int id ,int page);

        public abstract Observable<WxArticleBean> loadMoreArticles(int page);

        public void saveTab(List<WxTabBean> banners){

        }

        public void saveNormalArticles(WxArticleBean articleData) {

        }

    }

    interface View extends IBaseView<WxContract.Presenter> {
        void setTabData(List<WxTabBean> banner, String msg);

        void setWxArticleData(List<WxArticleBean.DataBean> articleData, String msg);//数据需要page

        void setLoadMoreArticleReceiveData(WxArticleBean articleData, String msg);
    }

    interface Presenter extends IBasePresenter<WxContract.View> {
        void getTabPresenter(@HomeModel.HomeLoadType int type);

//        void getWxArticlesPresenter( @HomeModel.HomeLoadType int type);
        void getWxArticlesPresenter(int page,int id, @HomeModel.HomeLoadType int type);

        void loadMoreArticles(int page, @HomeModel.HomeLoadType int type);
    }

}
