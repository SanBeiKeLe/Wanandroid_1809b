package com.wanghongli.myapplication.base;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IBaseCacheCallBack<T> {
    int TYPE_CACHE_PERSISTENT = 0X100;// 持久化的缓存
    int TYPE_CACHE_MEMORY = 0X200; // 内存的缓存
    @IntDef({TYPE_CACHE_PERSISTENT,TYPE_CACHE_MEMORY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CallBackCacheType{}

    void onCacheBack(T t,@CallBackCacheType int type);//缓存返回的数据通过该接口回调给Presenter
}
