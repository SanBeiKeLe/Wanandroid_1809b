package com.wanghongli.myapplication.home.model;

import android.os.health.PackageHealthStats;

import androidx.annotation.IntDef;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.WAApplication;
import com.wanghongli.myapplication.base.BaseRepository;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.base.ServerException;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.okhttp.WADataService;
import com.wanghongli.myapplication.home.contract.HomeContract;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.PublicKey;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import retrofit2.http.PATCH;

public class HomeModel extends BaseRepository implements HomeContract.Model {

    @Override
    public void getBannerData(LifecycleProvider provider, int type, IBaseCallBack<List<Banner>> callBack) {
            observer(provider, WADataService.getService().getBanners(), new Function<HttpResult<List<Banner>>, ObservableSource<List<Banner>>>() {
                @Override
                public ObservableSource<List<Banner>> apply(HttpResult<List<Banner>> listHttpResult) throws Exception {
                    if (listHttpResult!=null&&listHttpResult.data.size()>0){
                        return Observable.just(listHttpResult.data);
                    }
                    return Observable.error(new ServerException(listHttpResult.errorMsg));
                }
            }, callBack);
    }

    @Override
    public void getArticleData(LifecycleProvider provider, int type, int page, IBaseCallBack<ArticleData> callBack) {
       request(provider, 0,callBack);
    }

    private void request(LifecycleProvider provider, int page, IBaseCallBack<ArticleData> callBack) {
        observer(provider, WADataService.getService().getArticles(page), new Function<HttpResult<ArticleData>, ObservableSource<ArticleData>>() {
            @Override
            public ObservableSource<ArticleData> apply(HttpResult<ArticleData> listHttpResult) throws Exception {
                if(listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.getDatas() != null && listHttpResult.data.getDatas().size() > 0){
                    return  Observable.just(listHttpResult.data);
                }
                return Observable.error(new ServerException(listHttpResult.errorMsg));
            }
        }, callBack);
    }


    @Override
    public void getTopArticleData(LifecycleProvider provider, int type, IBaseCallBack<List<ArticleData.Article>> callBack) {

        observer(provider, WADataService.getService().getTopArticles(), new Function<HttpResult<List<ArticleData.Article>>, ObservableSource<List<ArticleData.Article>>>() {
            @Override
            public ObservableSource<List<ArticleData.Article>> apply(HttpResult<List<ArticleData.Article>> listHttpResult) throws Exception {
                if (listHttpResult!=null){
                    return Observable.just(listHttpResult.data);
                }
                return Observable.error(new ServerException(listHttpResult.errorMsg));
            }
        }, callBack);
    }

    @Override
    public void loadMoreArticles(LifecycleProvider provider, int type, int page, IBaseCallBack<ArticleData> callBack) {
        request(provider, page,callBack);
    }


    /* @IntDef({HomeLoadType.LOAD_TYPE_LOAD, HomeLoadType.LOAD_TYPE_REFRESH})
        @Retention(RetentionPolicy.SOURCE)
        public @interface HomeLoadType{
            int LOAD_TYPE_LOAD = 1;
            int LOAD_TYPE_REFRESH = 2;
        }*/
   @IntDef({HomeLoadType.LOAD_TYPE_LOAD,HomeLoadType.LOAD_TYPE_REFRESH})
   @Retention(RetentionPolicy.SOURCE)
   public @interface HomeLoadType{
       int LOAD_TYPE_LOAD = 1;
       int LOAD_TYPE_REFRESH = 2;
        int LOAD_TYPE_MORE = 3;
   }



}
