package com.wanghongli.myapplication.home.local;

import android.os.Looper;
import android.text.TextUtils;

import com.wanghongli.myapplication.Wx.contract.WxContract;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;
import com.wanghongli.myapplication.home.contract.HomeContract;
import com.wanghongli.myapplication.utils.DataCacheUtils;
import com.wanghongli.myapplication.utils.SPUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class WxSpLocalSource2 extends WxContract.DataSource {
    private static final String TAB_CACHE_KEY  = "TAB";
    private static final String NORMAL_ARTICLE_CACHE_KEY  = "NORMAL_ARTICLE";

    private <T> Observable<T> getDataObservable(final Class<T> tClass,final String key){
            return Observable.create(new ObservableOnSubscribe<T>() {
                @Override
                public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                    String json = SPUtils.getValue(key);
                    if(!TextUtils.isEmpty(json)){
                        T data  = DataCacheUtils.convertDataFromJson(tClass,json);
                        if(data != null){
                            emitter.onNext(data);
                        }

                    }

                    emitter.onComplete();

                }
            });
    }

    private <T> Observable<List<T>> getDataListObservable(final Class<T> tClass,final String key){
        return Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(ObservableEmitter<List<T>> emitter) throws Exception {
                String json = SPUtils.getValue(key);
                if(!TextUtils.isEmpty(json)){
                    List<T> data  = DataCacheUtils.convertDataListFromJson(tClass,json);
                    if(data != null){
                        emitter.onNext(data);
                    }

                }
                emitter.onComplete();

            }
        });
    }

    @Override
    public Observable<List<WxTabBean>> getTab() {

        return  getDataListObservable(WxTabBean.class,TAB_CACHE_KEY);
    }

    @Override
    public Observable<WxArticleBean> getWxArticles(int id, int page) {
        return  getDataObservable(WxArticleBean.class, NORMAL_ARTICLE_CACHE_KEY).doOnNext(new Consumer<WxArticleBean>() {
            @Override
            public void accept(WxArticleBean articleData) throws Exception {
                articleData.getData().setCurPage(-1);
            }
        });
    }

    @Override
    public Observable<WxArticleBean> loadMoreArticles(int page) {

        return null;
    }

    @Override
    public void saveTab(List<WxTabBean> banners) {
        super.saveTab(banners);
        saveTo(banners,TAB_CACHE_KEY);
    }

    @Override
    public void saveNormalArticles(WxArticleBean articleData) {
        super.saveNormalArticles(articleData);
        saveTo(articleData,NORMAL_ARTICLE_CACHE_KEY);
    }

    private void saveTo(final Object o, final String key){
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){ // 运行在主线程

            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    String json = DataCacheUtils.convertToJsonFromObject(o);
                    SPUtils.saveValueToDefaultSpByCommit(key, json);
                    emitter.onComplete();
                }
            }).subscribeOn(Schedulers.io()).subscribe();
        }else{
            String json = DataCacheUtils.convertToJsonFromObject(o);
            SPUtils.saveValueToDefaultSpByCommit(key, json);
        }
    }

}
