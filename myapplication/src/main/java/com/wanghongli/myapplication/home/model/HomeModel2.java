package com.wanghongli.myapplication.home.model;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.BaseRepository;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.base.ServerException;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.okhttp.WADataService;
import com.wanghongli.myapplication.home.contract.HomeContract;
import com.wanghongli.myapplication.home.contract.HomeContract.Model2;
import com.wanghongli.myapplication.utils.DataCacheUtils;
import com.wanghongli.myapplication.utils.ObjectUtils;
import com.wanghongli.myapplication.utils.SPUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomeModel2 extends BaseRepository implements HomeContract.Model {
    private HomeContract.Model2 mRemote;
    private HomeContract.Model2 mLocal;
    @IntDef({HomeLoadType.LOAD_TYPE_LOAD, HomeLoadType.LOAD_TYPE_REFRESH,HomeLoadType.LOAD_TYPE_MORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HomeLoadType{
        int LOAD_TYPE_LOAD = 1;
        int LOAD_TYPE_REFRESH = 2;
        int LOAD_TYPE_MORE = 3;
    }


    public HomeModel2(@NonNull HomeContract.Model2 remote, HomeContract.Model2 local) {
        ObjectUtils.requireNonNull(remote, "remote source is null");
        mRemote = remote;
        mLocal = local;
    }


    @Override
    public void getBannerData(LifecycleProvider provider, int type, IBaseCallBack<List<Banner>> callBack) {
        Observable<List<Banner>> localObservable = null;
        Observable<List<Banner>> remoteObservable = mRemote.getBanner();


        // 如果是第一次加载 则需要线去加载缓存
        if (type == HomeModel.HomeLoadType.LOAD_TYPE_LOAD && mLocal != null) {
            localObservable = mLocal.getBanner();

        }


        process(provider, localObservable, remoteObservable, new Consumer<List<Banner>>() {
            @Override
            public void accept(List<Banner> banners) throws Exception {
                if(mLocal != null && banners != null && banners.size() > 0){
                    mLocal.saveBanner(banners);
                }
            }
        }, callBack);



    }

    @Override
    public void getArticleData(LifecycleProvider provider, int type, int page, IBaseCallBack<ArticleData> callBack) {
        Observable<ArticleData> localObservable = null;

        Observable<ArticleData> remoteObservable = mRemote.getArticles();
        if (type == HomeModel.HomeLoadType.LOAD_TYPE_LOAD && mLocal != null) {
            localObservable = mLocal.getArticles();
        }


        process(provider, localObservable, remoteObservable, new Consumer<ArticleData>() {
            @Override
            public void accept(ArticleData articleData) throws Exception {
                if(mLocal != null){
                    mLocal.saveNormalArticles(articleData);
                }
            }
        }, callBack);
    }

    @Override
    public void getTopArticleData(LifecycleProvider provider, int type, IBaseCallBack<List<ArticleData.Article>> callBack) {

        Observable<List<ArticleData.Article>> localObservable = null;
        Observable<List<ArticleData.Article>> remoteObservable = mRemote.getTopArticles();

        if (type == HomeModel.HomeLoadType.LOAD_TYPE_LOAD && mLocal != null) {
            localObservable = mLocal.getTopArticles();


        }



        process(provider, localObservable, remoteObservable, new Consumer<List<ArticleData.Article>>() {
            @Override
            public void accept(List<ArticleData.Article> articles) throws Exception {
                if(mLocal != null){
                    mLocal.saveTopArticles(articles);
                }
            }
        }, callBack);
    }

    @Override
    public void loadMoreArticles(LifecycleProvider provider, int type, int page, IBaseCallBack<ArticleData> callBack) {
        process(provider, null, mRemote.loadMoreArticles(page), null, callBack);
    }
    private <R> void process(LifecycleProvider provider, Observable<R> localObservable,Observable<R> remoteObservable, Consumer<R> doOnNext, IBaseCallBack<R> callBack){

        if (mLocal != null && doOnNext != null) {
            // 服务器数据如果加载成功，不管是第一次还是刷新，再回调各 presenter 时先保存到本地，

            remoteObservable = remoteObservable.doOnNext(doOnNext);

        }

        //通过 concat 连接操作符，实现 本地有数据先读本地，等本地读取完成后，再读取服务器，包装本地一定发发再服务器之前。
        //Observable<R> resultObservable = localObservable == null ? remoteObservable : Observable.concat(localObservable, remoteObservable);

        Observable<R> resultObservable = null;

        if(localObservable == null){
            resultObservable = remoteObservable;
        }else{
            resultObservable = Observable.concat(localObservable,remoteObservable).firstOrError().toObservable();
        }
        observerNoMap(provider, resultObservable, callBack);

    }
}
