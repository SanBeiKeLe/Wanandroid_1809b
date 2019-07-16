package com.wanghongli.myapplication.home.remote;

import com.wanghongli.myapplication.base.ServerException;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.okhttp.WADataService;
import com.wanghongli.myapplication.home.contract.HomeContract;
import com.wanghongli.myapplication.home.model.HomeModel2;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;



public class HomeRemoteSource extends HomeContract.Model2 {


    @Override
    public Observable<List<Banner>> getBanner() {

        return WADataService.getService().getBanners().flatMap(new Function<HttpResult<List<Banner>>, ObservableSource<List<Banner>>>() {
            @Override
            public ObservableSource<List<Banner>> apply(HttpResult<List<Banner>> listHttpResult) throws Exception {
                if (listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0) {


                    return Observable.just(listHttpResult.data);
                }
                return Observable.error(new ServerException(listHttpResult.errorMsg));
            }
        });
    }

    @Override
    public Observable<List<ArticleData.Article>> getTopArticles() {
        return WADataService.getService().getTopArticles().flatMap(new Function<HttpResult<List<ArticleData.Article>>, ObservableSource<List<ArticleData.Article>>>() {
            @Override
            public ObservableSource<List<ArticleData.Article>> apply(HttpResult<List<ArticleData.Article>> listHttpResult) throws Exception {
                if (listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0) {
                    return Observable.just(listHttpResult.data);
                }
                return Observable.error(new ServerException(listHttpResult.errorMsg));
            }
        });
    }

    @Override
    public Observable<ArticleData> getArticles() {
        return loadMoreArticles(0);
    }

    @Override
    public Observable<ArticleData> loadMoreArticles(int page) {
        return WADataService.getService().getArticles(page).flatMap(new Function<HttpResult<ArticleData>, ObservableSource<ArticleData>>() {
            @Override
            public ObservableSource<ArticleData> apply(HttpResult<ArticleData> listHttpResult) throws Exception {
                if (listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.getDatas() != null && listHttpResult.data.getDatas().size() > 0) {
                    return Observable.just(listHttpResult.data);
                }
                return Observable.error(new ServerException(listHttpResult.errorMsg));
            }
        });
    }


}
