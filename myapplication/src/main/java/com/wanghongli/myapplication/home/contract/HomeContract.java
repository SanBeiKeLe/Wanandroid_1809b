package com.wanghongli.myapplication.home.contract;

import androidx.annotation.IntDef;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.base.IBasePresenter;
import com.wanghongli.myapplication.base.IBaseView;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.home.model.HomeModel2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;

public interface HomeContract {


    interface Model {
         void getBannerData(LifecycleProvider provider, @HomeModel.HomeLoadType  int type, IBaseCallBack<List<Banner>> callBack);
         void getArticleData(LifecycleProvider provider, @HomeModel.HomeLoadType  int type,int page ,IBaseCallBack<ArticleData> callBack);
         void getTopArticleData(LifecycleProvider provider,@HomeModel.HomeLoadType  int type ,IBaseCallBack<List<ArticleData.Article>> callBack);
         void loadMoreArticles(LifecycleProvider provider,@HomeModel.HomeLoadType  int type ,int page,IBaseCallBack<ArticleData> callBack );
     }
    public abstract class Model2 {
        public abstract Observable<List<Banner>> getBanner();

        public abstract Observable<List<ArticleData.Article>> getTopArticles();

        public abstract Observable<ArticleData> getArticles();

        public abstract Observable<ArticleData> loadMoreArticles(int page);

        public void saveBanner(List<Banner> banners){

        }

        public void saveTopArticles(List<ArticleData.Article> articles) {

        }

        public void saveNormalArticles(ArticleData articleData) {

        }

    }

    interface View extends IBaseView<Presenter> {
        void setBannerData(List<Banner> banner, String msg);

        void setArticleData(ArticleData articleData, String msg);

        void setTopArticleData(List<ArticleData.Article> articles, String msg);

        void onLoadMoreArticleData(ArticleData articleData, String msg);
    }

    interface Presenter extends IBasePresenter<View> {
        void getBannerPresenter(@HomeModel.HomeLoadType int type);

        void getArticlesPresenter(int page, @HomeModel.HomeLoadType int type);

        void getTopArticlesPresenter(@HomeModel.HomeLoadType int type);

        void loadMoreArticlesPresenter(int page, @HomeModel.HomeLoadType int type);
    }

}
